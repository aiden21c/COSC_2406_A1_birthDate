package com.company.object;

import java.util.ArrayList;
import java.util.Date;

public class Heap {

//    An arraylist of artists to store all artists
    private ArrayList<Artist> artists;

    public Heap() {
        artists = new ArrayList<>();
    }

    public void addToArtistArray(Artist a) { artists.add(a); }

    /** Searches the ArrayList 'artists' for artists with matching 'birthdates' within the given range
     * @param dates A range of dates to search for matching artists between
     * @return A ArrayList containing the 'toString' of all matching records within the ArrayList
     */
    public ArrayList<String> birthdateSearch(Date[] dates) {
        ArrayList<String> output = new ArrayList<>();

        for (Artist a : artists) {
            boolean artistAdded = false;
            for(int i = 0; i < a.getBirthdate().length; i++) {
                if(a.getBirthdate()[i] != null) {
                    if (a.getBirthdate()[i].before(dates[1]) && a.getBirthdate()[i].after(dates[0])) {
                        if (!artistAdded) {
                            output.add(a.toString());
                            artistAdded = true;
                        }
                    }
                }
            }
        }
        return output;

    }

    public void clearHeap() {artists.clear();}
}
