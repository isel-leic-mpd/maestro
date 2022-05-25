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

package org.isel.maestro.streams;

import org.isel.leirt.maestro.requests.CountRequest;
import org.isel.leirt.maestro.requests.HttpRequest;
import org.isel.maestro.streams.model.CommonTopTrackRanks;
import org.junit.jupiter.api.Test;

import java.util.*;

import java.util.stream.Stream;


import static java.util.stream.Collectors.toList;
import static org.isel.maestro.streams.utils.StreamUtils.intersection;
import static org.junit.jupiter.api.Assertions.*;


public class MaestroServiceTest {

    private <T> Optional<T> lastOf(Stream<T> stream) {
        return stream.reduce((t1, t2) -> t2);
    }

    /*
      Uncomment for Phase 2

@Test
    public void search_David_Bowie_and_count_albums() {

        CountRequest countRequest = new CountRequest(new HttpRequest());

        MaestroService service = new MaestroService(
            new LastfmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtist("david", 50);
        assertEquals(0, countRequest.getCount());

        Artist davidBowie =
            artists
                .dropWhile(a -> !a.getName().equalsIgnoreCase("David Bowie"))
                .findFirst().get();

        assertEquals(1, countRequest.getCount());
        assertEquals("David Bowie", davidBowie.getName());
        assertEquals(168, davidBowie.getAlbums().count());
        assertEquals(30, countRequest.getCount());
    }

    @Test
    public void search_ColdPlay_and_count_all_results_with_cache() {
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtist("cold", 30);
        assertEquals(0, countRequest.getCount());

        Artist second = artists.skip(1).findFirst().get();
        assertEquals("Cold War Kids", second.getName());
        assertEquals(1, countRequest.getCount());
    }

    @Test
    public void searchHiperAndCountAllResults() {
        CountRequest countReq = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(
            new LastfmWebApi(countReq));
        Stream<Artist> artists = service.searchArtist("hiper", 100);
        assertEquals(0, countReq.getCount());
        assertEquals(6, artists.count());
        assertEquals(2, countReq.getCount());
        artists = service.searchArtist("hiper", 100);
        Artist last = lastOf(artists).get();
        assertEquals("Hi-Per", last.getName());
        assertEquals(4, countReq.getCount());
    }


    @Test
    public void searchHiperAndCountAllResultsWithCache() {
        CountRequest countReq = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(
            new LastfmWebApi(countReq));
        Supplier<Stream<Artist>> artists =
                cache(service.searchArtist("hiper", 100));
        assertEquals(0, countReq.getCount());
        assertEquals(6, artists.get().count()); //JM expected was 700
        assertEquals(2, countReq.getCount());
        Artist last = lastOf(artists.get()).get();
        assertEquals("Hi-Per",  last.getName());
        assertEquals(2, countReq.getCount());
    }


    @Test
    public void getFirstAlbumOfMuse() {
        CountRequest countReq = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(
            countReq));
        Stream<Artist> artists = service.searchArtist("muse", 10);
        assertEquals(0, countReq.getCount());
        Artist muse = artists.findFirst().get();
        assertEquals(1, countReq.getCount());
        Stream<Album> albums = muse.getAlbums();
        assertEquals(1, countReq.getCount());
        Album first = albums.findFirst().get();
        assertEquals(2, countReq.getCount());
        assertEquals("Black Holes and Revelations", first.getName());
    }

    @Test
    public void get58AlbumsOfMuse() {
        long startTime = System.currentTimeMillis();
        CountRequest countReq = new CountRequest(new HttpRequest());
        MaestroService service =
            new MaestroService(new LastfmWebApi(countReq));
        Artist muse = service.searchArtist("muse", 10).findFirst().get();
        Stream<Album> albums = muse.getAlbums().limit(111);
        long endTime = System.currentTimeMillis();

        System.out.println("done in " + (endTime-startTime) + " ms!");
        assertEquals(58, albums.count());
        assertEquals(9, countReq.getCount());
    }



    @Test
    public void get42thTrackOfMuse() {
        CountRequest countReq = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(
            countReq));
        Stream<Track> tracks =
               service.searchArtist("muse", 10)
                       .findFirst().get()
                       .getTracks();
        assertEquals(1, countReq.getCount()); // 1 for artist + 0 for tracks because it fetches lazily

        Track track = tracks
                .skip(42)
                .findFirst()
                .get(); // + 1 to getAlbums + 4 to get tracks of first 4 albums.

        assertEquals(6, countReq.getCount());
        assertEquals("MK Ultra", track.getName());
    }




    @Test
    void get_second_song_on_top_100_tracks_in_portugal() {
        CountRequest countReq = new CountRequest(new HttpRequest());
        LastfmWebApi api = new LastfmWebApi(countReq);
        MaestroService service = new MaestroService(api);

        String second = service.getTop100("Portugal")
        .skip(1)
        .findFirst()
        .map(tr -> tr.getName())
        .orElse("Unknown");

        assertEquals("Do I Wanna Know?", second);
    }

     */

    /*
     Uncomment for phase 4

     @Test
    void get_similar_top_tracks_between_portugal_and_spain() {
        CountRequest countReq = new CountRequest(new HttpRequest());
        LastfmWebApi api = new LastfmWebApi(countReq);
        MaestroService service = new MaestroService(api);
        CommonTopTrackRanks firstExpected =
            new CommonTopTrackRanks("The Less I Know the Better",
                "Tame Impala", 1, 3);

        CommonTopTrackRanks lastExpected =
            new CommonTopTrackRanks("" +
                "Somebody Told Me", "The Killers", 100, 92);

        var portugalTop = service.getTop100("Portugal")
               .limit(100);

        var spainTop = service.getTop100("Spain")
                                 .limit(100);

        var topCommons = intersection(
                    portugalTop,
                     spainTop,
                     (tr1, tr2) ->
                         tr1.getName().equals(tr2.getName()) &&
                         tr1.getArtistMbid().equals(tr2.getArtistMbid()),
                     (tr1, tr2) -> new CommonTopTrackRanks(
                                        tr1.getName(),
                                        tr1.getArtistName(),
                                        tr1.getRank(),
                                        tr2.getRank()))
            .collect(toList());

        //topCommons.forEach(System.out::println);
        assertEquals(firstExpected, topCommons.get(0));
        assertEquals(lastExpected, topCommons.get(topCommons.size()-1));

    }


    @Test
    public void get_artists_common_to_bowie_and_ferry() {
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));

        var similars =
            service.commonArtists("Bryan+Ferry","David+Bowie" )
            .collect(toList());


        assertEquals(1, similars.size());
        assertEquals("Roxy Music", similars.get(0));
    }

     */
}
