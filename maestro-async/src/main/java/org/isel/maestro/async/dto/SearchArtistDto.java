package org.isel.maestro.async.dto;

public class SearchArtistDto {
    private SearchArtistResultsDto results;

    public SearchArtistDto(SearchArtistResultsDto results) {
        this.results = results;
    }

    public SearchArtistResultsDto getResults() { return results; }
}
