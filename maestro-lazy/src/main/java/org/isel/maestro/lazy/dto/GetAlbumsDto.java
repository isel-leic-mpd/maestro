package org.isel.maestro.lazy.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAlbumsDto {

    @SerializedName("topalbums")
    private AlbumsDto topAlbums;

    public List<AlbumDto> getAlbums() { return topAlbums.getAlbums(); }

}
