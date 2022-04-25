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

import com.google.gson.Gson;
import org.isel.maestro.lazy.dto.*;
import org.isel.leirt.maestro.requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.isel.leirt.maestro.queries.LazyQueries.filter;
import static org.isel.leirt.maestro.queries.LazyQueries.toList;


public class LastfmWebApi {
    public static final int PAGE_SIZE = 30;

    private static String LASTFM_API_KEY = getApiKeyFromResources();

    private static final String LASTFM_SERVICE = "http://ws.audioscrobbler.com/2.0/";


    private static final String LASTFM_SEARCH = LASTFM_SERVICE
                        + "?method=artist.search&format=json&artist=%s&page=%d&api_key="
                        + LASTFM_API_KEY;

    private static final String LASTFM_GET_ALBUMS = LASTFM_SERVICE
                    + "?method=artist.gettopalbums&format=json&mbid=%s&page=%d&api_key="
                    + LASTFM_API_KEY;

    private static final String LASTFM_GET_ALBUM_INFO = LASTFM_SERVICE
                    + "?method=album.getinfo&format=json&mbid=%s&api_key="
                    + LASTFM_API_KEY;

    private static final String LASTFM_ARTIST_INFO = LASTFM_SERVICE
                + "?method=artist.getinfo&format=json&mbid=%s&api_key="
                + LASTFM_API_KEY;

    /**
     * Retrieve API-KEY from resources
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile =
                ClassLoader.getSystemResource("lastfm_web_api_key.txt");
            try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                "YOU MUST GET a KEY from  openweatherapi.com and place it in src/main/resources/openweatherapi-app-key2.txt");
        }
    }


    private final Request request;
    protected final Gson gson;

    public LastfmWebApi(Request request) {
        this.request = request;
        this.gson = new Gson();
    }

    /**
     * TO CHANGE
     * must ignore artists with mbid null or empty
     * @param name
     * @param page
     * @return
     */
    public List<ArtistDto> searchArtist(String name, int page) {
        String path = String.format(LASTFM_SEARCH, name, page);

        Reader reader= request.get(path);
        SearchArtistDto searchResult = gson.fromJson(reader, SearchArtistDto.class);


        return searchResult.getResults().getArtistMatches();

    }


    public ArtistDetailDto getArtistInfo(String artistMbid) {
        String path = String.format(LASTFM_ARTIST_INFO, artistMbid);
        Reader reader= request.get(path);

        ArtistDetailQueryDto result =
            gson.fromJson(reader, ArtistDetailQueryDto.class);
        return result.getInfo();
    }

    /**
     * TO CHANGE
     * must ignore albums with mbid null or empty
     * @param artistMbid
     * @param page
     * @return
     */
    public List<AlbumDto> getAlbums(String artistMbid, int page) {
        String path = String.format(LASTFM_GET_ALBUMS, artistMbid, page);
        Reader reader= request.get(path);

        GetAlbumsDto albums = gson.fromJson(reader, GetAlbumsDto.class);
        return  albums.getAlbums();

    }


    public List<TrackDto> getAlbumInfo(String albumMbid){
        String path = String.format(LASTFM_GET_ALBUM_INFO, albumMbid);
        Reader reader= request.get(path);

        GetAlbumDto album = gson.fromJson(reader, GetAlbumDto.class);
        return  album.getAlbum().getTracks();

    }
}
