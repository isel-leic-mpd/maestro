/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2019, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 *
 */

package org.isel.maestro.async;



import org.isel.maestro.async.dto.*;
import org.isel.maestro.async.model.*;
import org.isel.maestro.async.utils.requests.AsyncHttpRequest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;



public class MaestroService {

    final LastfmWebApi api;

    public MaestroService(LastfmWebApi api) {
        this.api = api;
    }

    public MaestroService() {

        this(new LastfmWebApi(new AsyncHttpRequest()));
    }


    public CompletableFuture<Stream<Artist>> searchArtistPar(String name, int max) {
        int npages = (max -1) / LastfmWebApi.PAGE_SIZE + 1;
        return
            IntStream.rangeClosed(1, npages)
            .mapToObj(page -> api.searchArtist(name, page))
            .reduce(CompletableFuture.completedFuture(List.of()),
                // Note that the lambda passed to thenCombine modifies
                // the received list. This should no be done in general, that is
                // the objects processed in the lambda should be seen as immutables
                // ans instead we should build other objects instead of modify the
                // passed ones. We see in next lecture hot to resolve this
                (cf1, cf2) -> cf1.thenCombine(cf2, (l1,l2) -> { l1.addAll(l2); return l1; })
            )
            .thenApply ( l ->  l.stream().map(this::dtoToArtist));
    }



    // This implementation, although correct, can be enhanced.
    // We will do this in next lecture
    public CompletableFuture<Stream<Artist>> searchArtistPar2(String name, int max) {
        int npages = (max -1) / LastfmWebApi.PAGE_SIZE + 1;

        var futures = IntStream.rangeClosed(1, npages)
                 .mapToObj(page -> api.searchArtist(name, page))
                 .toArray(sz -> new CompletableFuture[sz]);

        return
        CompletableFuture.allOf(futures)
        .thenApply(__ ->
            Arrays.stream(futures)
                .map(cf -> (List<ArtistDto>) cf.join())
                .flatMap(l -> l.stream())
                .map(this::dtoToArtist)
        );
    }

    private CompletableFuture<Stream<Artist>>
        searchArtistSerialAux(String name,
                              int max,
                              int page,
                              Stream<Artist> acc) {
        if (page == max) {
            return CompletableFuture.completedFuture(acc);
        }
        else {
            return
            api.searchArtist(name, page)
            .thenCompose(list -> {
                var newAcc =
                    Stream.concat(acc, list.stream().map(this::dtoToArtist));
                return searchArtistSerialAux(name, max, page + 1, newAcc);
            });
        }
    }

    public CompletableFuture<Stream<Artist>>
    searchArtistSerial(String name, int max) {
        int npages = (max -1) / LastfmWebApi.PAGE_SIZE + 1;

        return searchArtistSerialAux(name, npages, 1, Stream.empty());
    }

    public CompletableFuture<ArtistDetail>  getArtistDetail(String artistMbid) {
       return
            api.getArtistInfo(artistMbid)
                .thenApply( this::dtoToArtistDetail);
    }




    public CompletableFuture<Stream<Album>> getAlbums(String artistMbid, int max) {
        // TO IMPLEMENT
        return null;
    }


    private CompletableFuture<Stream<Track>> getAlbumTracks(String albumMbid) {
        // TO IMPLEMENT
        return null;
    }

    private CompletableFuture<Stream<Track>> getTracks(String artistMbid) {
        // TO IMPLEMENT
        return null;
    }

    public CompletableFuture<Stream<TrackRank>> getTop100(String country) {
        // TO IMPLEMENT
        return null;
    }

    private ArtistDetail dtoToArtistDetail(ArtistDetailDto dto) {


        List<String> similar =
                dto.getSimilarArtists()
                .stream()
                .map(detailDto -> detailDto.getName())
                .collect(toList());

        return new ArtistDetail(
            similar, dto.getGenres(), dto.getBio() ) ;
    }

    private Artist dtoToArtist(ArtistDto dto) {
        // TO IMPLEMENT
        return null;
    }

    private Album dtoToAlbum(AlbumDto dto) {
        // TO IMPLEMENT
        return null;
    }

    private Track dtoToTrack(TrackDto dto) {

        return new Track(dto.getName(), dto.getUrl(), dto.getDuration());
    }

    private TrackRank dtoToTrackRank(TrackRankDto dto) {
        return new TrackRank(dto.getName(),
                dto.getUrl(),
                dto.getDuration(),
                dto.getRank(),
                dto.getArtistMbid());
    }
}
