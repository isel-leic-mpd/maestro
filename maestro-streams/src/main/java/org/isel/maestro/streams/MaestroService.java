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

import org.isel.maestro.streams.dto.*;
import org.isel.maestro.streams.model.*;


import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Stream.of;
import static org.isel.leirt.maestro.queries.LazyQueries.map;
import static org.isel.leirt.maestro.queries.LazyQueries.toList;

public class MaestroService {

    final LastfmWebApi api;

    public MaestroService(LastfmWebApi api) {
        this.api = api;
    }

    // TO IMPLEMENT missing methods

    public Stream<TrackRank> getTop100(String country) {
        // TO IMPLEMENT
        return null;
    }

    public Stream<CommonTopTrackRanks> commonTop100Tracks(String country1, String country2) {
        // TO IMPLEMENT
        return null;
    }


    public Stream<String> commonArtists(String artist1, String artist2) {
        // TO IMPLEMENT
        return null;
    }

    public ArtistDetail getArtistDetail(String artistMbid) {
        ArtistDetailDto detail = api.getArtistInfo(artistMbid);
        List<String> similar =  toList(
            map (
                detail.getSimilarArtists(),
                dto -> dto.getName()
            ));

        return new ArtistDetail(
            similar, detail.getGenres(), detail.getBio() ) ;
    }

}
