package org.isel.maestro.async.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryTracksDto {
    @SerializedName("track")
    private List<TrackRankDto> tracks;

    public List<TrackRankDto> getTopTracks() {
        return tracks;
    }

}
