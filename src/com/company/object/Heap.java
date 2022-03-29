package com.company.object;

import java.util.ArrayList;

public class Heap {

//    An arraylist of artists to store all artists
    private ArrayList<Artist> artists;

    public Heap() {
        artists = new ArrayList<>();
    }

    public void addToArtistArray(Artist a) { artists.add(a); }

    public ArrayList<Artist> getArtists() {
        return artists;
    }
}
