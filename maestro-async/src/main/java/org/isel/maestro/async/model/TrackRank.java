package org.isel.maestro.async.model;

public class TrackRank {
    private String name;
    private String url;
    private int duration;
    private String artistMbid;
    private int rank;

    public TrackRank(String name,
                     String url,
                     int duration,
                     int rank,
                     String artistMbid)  {
        this.name = name; this.url = url; this.duration = duration;
        this.rank = rank;
        this.artistMbid = artistMbid;
    }

    public String getName() { return name; }
    public String getUrl() { return url; }
    public int getDuration() { return duration; }
    public int getRank() { return rank; }
    public String getArtistMbid() { return artistMbid; }

    @Override
    public String toString() {
        return "{"
                + "name=" + '"' + getName() + '"'
                + ", artist=" + '"' + getArtistMbid()+ '"'
                + ", duration=" +  getDuration()
                + ", rank=" + getRank()
                + "}";
    }
}
