package org.isel.maestro.streams.dto;

import org.isel.maestro.streams.LastfmWebApi;

public class RankDto {
    private int rank;
    private int page;

    public void setPage(int page) { this.page = page;}
    public int getRank() {
        return (page-1)* LastfmWebApi.PAGE_SIZE +rank+1;
    }
}
