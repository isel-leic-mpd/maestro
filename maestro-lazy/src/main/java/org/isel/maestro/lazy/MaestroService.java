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

import org.isel.leirt.maestro.queries.LazyQueries;
import org.isel.maestro.lazy.dto.AlbumDto;
import org.isel.maestro.lazy.dto.ArtistDetailDto;
import org.isel.maestro.lazy.dto.ArtistDto;
import org.isel.maestro.lazy.dto.TrackDto;
import org.isel.maestro.lazy.model.Album;
import org.isel.maestro.lazy.model.Artist;
import org.isel.maestro.lazy.model.ArtistDetail;
import org.isel.maestro.lazy.model.Track;
import org.isel.leirt.maestro.iterators.IteratorArray;
import static org.isel.leirt.maestro.queries.LazyQueries.*;


import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.isel.leirt.maestro.queries.LazyQueries.iterate;

public class MaestroService {

    final LastfmWebApi api;

    public MaestroService(LastfmWebApi api) {
        this.api = api;
    }

    /**
     * TO IMPLEMENT
     * @param name
     * @param maxItems
     * @return
     */
    public Iterable<Artist> searchArtist(String name, int maxItems) {
        return null;
    }

    /**
     * TO IMPLEMENT
     * @param artistMbid
     * @return
     */
    public Iterable<Album> getAlbums(String artistMbid) {
        return null;
    }

    /**
     * TO IMPLEMENT
     * @param artistMbid
     * @return
     */
    public ArtistDetail  getArtistDetail(String artistMbid) {
        return null;
    }

    /**
     * TO IMPLEMENT
     * @param albumMbid
     * @return
     */
    public Iterable<Track> getAlbumTracks(String albumMbid) {
        return null;
    }

    /**
     * TO IMPLEMENT
     * @param artistMbid
     * @return
     */
    public Iterable<Track> getTracks(String artistMbid) {
        return null;
    }

    private Artist dtoToArtist(ArtistDto dto) {
        return null;
        // TO IMPLEMENT
        /*
        return new Artist(
                ....
        );
         */
    }

    private Album dtoToAlbum(AlbumDto dto) {
        return null;
        // TO IMPLEMENT
        /*
        return new Album(
               ...
        );
         */
    }

    private Track dtoToTrack(TrackDto dto) {
        return new Track(dto.getName(), dto.getUrl(), dto.getDuration());
    }

}
