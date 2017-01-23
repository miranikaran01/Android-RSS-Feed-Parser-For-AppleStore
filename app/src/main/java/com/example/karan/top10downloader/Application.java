package com.example.karan.top10downloader;

/**
 * Created by karan on 1/22/17.
 */

public class Application {
    private String name;
    private String artist;
    private String releaseDate;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nArtist: " + artist +
                "\nDate: " + releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
