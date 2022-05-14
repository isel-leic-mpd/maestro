package org.isel.maestro.streams.model;

public class CommonTopTrackRanks {
    public final String name;
    public final String artistName;
    public final int rankCountry1;
    public final int rankCountry2;

    public CommonTopTrackRanks(String name, String artistName, int rc1, int rc2) {
        this.name = name;
        this.artistName = artistName;
        this.rankCountry1 = rc1;
        this.rankCountry2 = rc2;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != getClass()) return false;
        CommonTopTrackRanks o = (CommonTopTrackRanks) other;

        return name.equalsIgnoreCase(o.name) &&
            artistName.equalsIgnoreCase(o.artistName);
    }

    @Override
    public String toString() {
        return "{" + name + ", " + artistName + ", " +
            rankCountry1 + ", " + rankCountry2 + "}";
    }
}
