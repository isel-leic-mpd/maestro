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

package org.isel.maestro.lazy;

import org.isel.leirt.maestro.requests.CountRequest;
import org.isel.maestro.lazy.model.Album;
import org.isel.maestro.lazy.model.Artist;
import org.isel.maestro.lazy.model.Track;
import org.isel.leirt.maestro.requests.HttpRequest;
import org.junit.jupiter.api.Test;

import static org.isel.leirt.maestro.queries.LazyQueries.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class MaestroServiceTest {

    @Test
    public void search_David_Bowie_and_count_albums() {

        CountRequest countRequest = new CountRequest(new HttpRequest());

        MaestroService service = new MaestroService(
            new LastfmWebApi(countRequest));
        Iterable<Artist> artists = service.searchArtist("david", 50);
        assertEquals(0, countRequest.getCount());


        Optional<Artist> davidBowieOrEmpty = first(
            skipWhile(artists,
                a -> !a.getName().equalsIgnoreCase("David Bowie"))
        );

        assertTrue(!davidBowieOrEmpty.isEmpty());

        Artist davidBowie = davidBowieOrEmpty.get();
        assertEquals(1, countRequest.getCount());
        assertEquals("David Bowie", davidBowie.getName());

        // stop on first empty or null album identifier
        Iterable<Album> bowieAlbums =
            takeWhile(
                davidBowie.getAlbums(),
                a -> a.getMbid() != null && !a.getMbid().isEmpty()
            );
        assertEquals(151, count(bowieAlbums));
        assertEquals(30, countRequest.getCount());
        assertEquals(50, count(artists));
        assertEquals(32, countRequest.getCount());
    }


    @Test
    public void get_first_album_of_Muse() {
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Iterable<Artist> artists = service.searchArtist("muse", 100);
        assertEquals(0, countRequest.getCount());
        Artist muse = first(artists).get();
        assertEquals(1, countRequest.getCount());
        Iterable<Album> albums = muse.getAlbums();
        assertEquals(1, countRequest.getCount());
        Album first = first(albums).get();
        assertEquals(2, countRequest.getCount());
        assertEquals("Black Holes and Revelations", first.getName());
    }

    @Test
    public void get_first_58_albums_of_Muse() {
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Artist muse = first(
                        service.searchArtist("muse", 10)
                    ).get();
        Iterable<Album> albums =
            cache(
                limit(
                    muse.getAlbums(),
                    58
                )
            );

        albums.forEach(System.out::println);
        assertEquals(58, count(albums));
        assertEquals(8, countRequest.getCount()); // 1 for artist + 7 pages of albums each with 30 albums max
    }

    @Test
    public void get_second_song_from_Black_Holes_album_of_Muse() {
        String MUSE_MBID = "fd857293-5ab8-40de-b29e-55a69d4e4d0f";
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Album blackHoles = first(
            service.getAlbums(MUSE_MBID)
        ).get();

        assertEquals(1, countRequest.getCount()); // 1 for artist + 1 page of albums
        assertEquals("Black Holes and Revelations", blackHoles.getName());
        Track song = first(skip(blackHoles.getTracks(), 1)).get();
        assertEquals(2, countRequest.getCount()); // + 1 to getTracks
        assertEquals("Starlight", song.getName());
    }

    @Test
    public void get_42th_track_of_Muse() {
        String MUSE_MBID = "fd857293-5ab8-40de-b29e-55a69d4e4d0f";
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Iterable<Track> tracks = service.getTracks(MUSE_MBID);
        assertEquals(0, countRequest.getCount()); // 1 for artist + 0 for tracks because it fetches lazily
        Track track = first(skip(tracks, 42)).get(); // + 1 to getAlbums + 4 to get tracks of first 4 albums.
        assertEquals("MK Ultra", track.getName());
        assertEquals(5, countRequest.getCount());
    }

    @Test
    public void get_artists_common_to_bowie_and_ferry() {
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        var bryanFerry = first(service.searchArtist("Bryan+Ferry", 10)).get();

        var davidBowie = first(service.searchArtist("David+Bowie", 10)).get();

        var ferrySimilars =  bryanFerry.getDetail().getSimilarArtists();
        var bowieSimilars =  davidBowie.getDetail().getSimilarArtists();

        var common = ferrySimilars
            .stream()
            .filter(
                s1 -> bowieSimilars.stream()
                    .filter(
                        s2 -> s2.equalsIgnoreCase(s1)
                    )
                    .findFirst().isPresent()
            )
            .collect(toList());
        assertEquals(1, common.size());
        assertEquals("Roxy Music", common.get(0));
    }

    /*---
    @Test
    public void search_Coldplay_and_count_all_results_with_cache() {
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Iterable<Artist> artists = cache(service.searchArtist("cold", 30));
        assertEquals(0, countRequest.getCount());

        assertEquals(30, count(artists));
        assertEquals(2, countRequest.getCount());
        Artist second = first(skip(artists, 1)).get();
        assertEquals("Cold War Kids", second.getName());
        assertEquals(30, count(artists));
        assertEquals(2, countRequest.getCount());
    }

     */


}
