package org.isel.maestro.streams.dto;

import java.util.List;

public class GetCountryTracksDto {
    private CountryTracksDto tracks;

    public List<TrackRankDto> getTopTracks() {
        return tracks.getTopTracks();
    }
}
