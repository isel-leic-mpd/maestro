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

import org.isel.maestro.async.model.Album;
import org.isel.maestro.async.model.Artist;
import org.isel.maestro.async.model.Track;
import org.isel.maestro.async.utils.requests.AsyncHttpRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;


import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MaestroServiceTest {

    public static class CountRequest extends AsyncHttpRequest {
        private int count;

        @Override
        public CompletableFuture<String> getAsync(String path) {
            count++;
            return super.getAsync(path);
        }

        public int getCount() {
            return count;
        }


    }

    private <T> Optional<T> lastOf(Stream<T> stream) {
        return stream.reduce((a, b) -> b);
    }

    @Test
    public void search_David_artists() {

        CountRequest countRequest = new CountRequest();
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtistPar("david", 50).join();
        assertEquals(60, artists.count());
    }

   /*
   @Test
    public void search_David_Bowie_and_count_albums_parallel_search() {

        CountRequest countRequest = new CountRequest( );
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        List<Artist> artists =
            service.searchArtistPar("david", 50)
           .join()
            .collect(Collectors.toList());

        Optional<Artist> davidBowieOrEmpty =
            artists
                .stream()
                .dropWhile(a -> !a.getName().equalsIgnoreCase("David Bowie"))
                .findFirst();
        assertTrue(!davidBowieOrEmpty.isEmpty());

        Artist davidBowie = davidBowieOrEmpty.get();

        assertEquals(2, countRequest.getCount());
        assertEquals(50, artists.size());
        assertEquals("David Bowie", davidBowie.getName());
        assertEquals(200, davidBowie.getAlbums().join().count());
        assertEquals(6, countRequest.getCount());
    }

    @Test
    public void search_David_Bowie_and_count_albums_parallel_allof_search() {

        CountRequest countRequest = new CountRequest( );
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        List<Artist> artists =
            service.searchArtistPar2("david", 50)
                   .join()
                   .collect(Collectors.toList());

        Optional<Artist> davidBowieOrEmpty =
            artists
                .stream()
                .dropWhile(a -> !a.getName().equalsIgnoreCase("David Bowie"))
                .findFirst();
        assertTrue(!davidBowieOrEmpty.isEmpty());

        Artist davidBowie = davidBowieOrEmpty.get();

        assertEquals(2, countRequest.getCount());
        assertEquals(50, artists.size());
        assertEquals("David Bowie", davidBowie.getName());
        assertEquals(200, davidBowie.getAlbums().join().count());
        assertEquals(6, countRequest.getCount());
    }

    @Test
    public void search_David_Bowie_and_count_albums_serial_search() {

        CountRequest countRequest = new CountRequest( );
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        List<Artist> artists =
            service.searchArtistSerial("david", 50)
                   .join()
                   .collect(Collectors.toList());

        Optional<Artist> davidBowieOrEmpty =
            artists
                .stream()
                .dropWhile(a -> !a.getName().equalsIgnoreCase("David Bowie"))
                .findFirst();
        assertTrue(!davidBowieOrEmpty.isEmpty());

        Artist davidBowie = davidBowieOrEmpty.get();

        assertEquals(2, countRequest.getCount());
        assertEquals(50, artists.size());
        assertEquals("David Bowie", davidBowie.getName());
        assertEquals(200, davidBowie.getAlbums().join().count());
        assertEquals(6, countRequest.getCount());
    }

    @Test
    public void search_ColdPlay_and_count_all_results() {
        CountRequest countRequest = new CountRequest( );
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtistPar("cold", 30).join();

        Artist second = artists.skip(1).findFirst().get();
        assertEquals("Cold War Kids", second.getName());
        assertEquals(1, countRequest.getCount());
    }

    @Test
    public void searchHiperAndDavidAndCountAllResultsParallelTest() {

        CountRequest httpGet = new CountRequest();
        MaestroService service =
                new MaestroService(new LastfmWebApi( httpGet));

        long start = System.currentTimeMillis();
        CompletableFuture<Stream<Artist>> artistsHiperFut =
                service.searchArtistPar("hiper", 100);
        CompletableFuture<Stream<Artist>> artistsDavidFut =
            service.searchArtistPar("david", 100);


        assertEquals(100, artistsHiperFut.join().count());
        assertEquals(8, httpGet.count);

        Artist last = lastOf(artistsDavidFut.join()).get();
        assertEquals("David Morales", last.getName());
        assertEquals(8, httpGet.count);
        System.out.printf("done in %dms\n", System.currentTimeMillis()-start);
    }


    @Test
    public void getFirstAlbumOfMuse() {

        CountRequest countRequest = new CountRequest();
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));
        var artists =
            service.searchArtistSerial("muse", 10);
        assertEquals(1, countRequest.getCount());
        Artist muse = artists.join().findFirst().get();
        assertEquals(1, countRequest.getCount());
        Stream<Album> albums = muse.getAlbums().join();
        assertEquals(5, countRequest.getCount());
        Album first = albums.findFirst().get();
        assertEquals(5, countRequest.getCount());
        assertEquals("Black Holes and Revelations", first.getName());

    }


    @Test
    public void get42thTrackOfMuse() {
        CountRequest countRequest = new CountRequest();
        MaestroService service = new MaestroService(new LastfmWebApi(
            countRequest));
        Artist muse =
            service.searchArtistSerial("muse", 10)
                   .join()
                   .findFirst().get();
        Stream<Track> tracks = muse
                   .getTracks()
                   .join();

        Track track = tracks
            .skip(42)
            .findFirst()
            .get(); // + 1 to getAlbums + 4 to get tracks of first 4 albums.

        assertEquals(7, countRequest.getCount());
        assertEquals("MK Ultra", track.getName());
    }



    @Test
    void get_second_song_on_top_100_tracks_in_portugal() {
        CountRequest countRequest = new CountRequest();
        LastfmWebApi api = new LastfmWebApi(countRequest);
        MaestroService service = new MaestroService(api);

        String second = service.getTop100("Portugal")
                               .join()
                               .skip(1)
                               .findFirst()
                               .map(tr -> tr.getName())
                               .orElse("Unknown");

        assertEquals(2, countRequest.getCount());
        assertEquals("Do I Wanna Know?", second);

        int[] index = {0};

        service.getTop100("Portugal")
            .join()
            .forEach( tr -> System.out.printf("%3d: %s\n", ++index[0], tr));
    }

     @Test
    public void get_artists_common_to_bowie_and_ferry() {
        CountRequest countRequest = new CountRequest();
        MaestroService service = new MaestroService(new LastfmWebApi(countRequest));

        var davidBowie =
            service.searchArtistPar("David+Bowie", 10)
                   .thenApply(s ->
                       s.dropWhile(a -> !a.getName().equalsIgnoreCase("David Bowie"))
                        .findFirst())
                   .join();


        var bryanFerry =
            service.searchArtistPar("Bryan+Ferry", 10)
                   .thenApply(s ->
                       s.dropWhile(a -> !a.getName().equalsIgnoreCase("Bryan Ferry"))
                        .findFirst())
                   .join();
        assertTrue(davidBowie.isPresent());
        assertTrue(bryanFerry.isPresent());

        var davidSimilars  =
            davidBowie.get().getDetail().join().getSimilarArtists();
        var bryanSimilars  =
            bryanFerry.get().getDetail().join().getSimilarArtists();

        for (String davidSimilar : davidSimilars) {
            System.out.println(davidSimilar);
        }
        System.out.println();
        for (String bryanSimilar : bryanSimilars) {
            System.out.println(bryanSimilar);
        }

        assertEquals(davidSimilars.get(4), bryanSimilars.get(0));
    }
     */


}
