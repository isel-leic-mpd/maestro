package org.isel.maestro.streams.model;

public class TrackRank {
    private String name;
    private String url;
    private int duration;
    private String artistMbid;
    private int rank;
    private String artistName;

    public TrackRank(String name,
                     String url,
                     int duration,
                     int rank,
                     String artistMbid,
                     String artistName)  {
        this.name = name; this.url = url; this.duration = duration;
        this.rank = rank;
        this.artistMbid = artistMbid;
        this.artistName = artistName;
    }

    public String getName() { return name; }
    public String getArtistName() { return artistName; }
    public String getUrl() { return url; }
    public int getDuration() { return duration; }
    public int getRank() { return rank; }
    public String getArtistMbid() { return artistMbid; }

    @Override
    public String toString() {
        return "{"
                + "name=" + '"' + getName() + '"'
                + ", artist=" + '"' + getArtistName()+ '"'
                + ", duration=" +  getDuration()
                + ", rank=" + getRank()
                + "}";
    }
}
