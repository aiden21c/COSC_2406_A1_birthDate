package com.company.system;

import com.company.object.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class DBManager {

    private String datafile;
    private Heap heap = new Heap();
    private int pageSize;
    static final int ARTIST_SIZE = 4636;

//    The datafile specified must be located in the resources folder
    public DBManager(String datafile) {
        this.datafile = "../resources/" + datafile;
        this.pageSize = getPageSize();
    }

    /** Gets the pagesize defined by the heapfile's name
     * @return the page size as an integer
     */
    private int getPageSize() {
        String pageSizeString = datafile.substring(18);
        return Integer.parseInt(pageSizeString);
    }


    /** Searches a heap file within the resources directory for any records with 'birthdate' fields matching a given range
     * @param dateRange the range to search for matching birthdays between
     * @return a string containing the time taken to complete the range search (in milliseconds)
     * @throws IOException if the heap file cannot be found
     */
    public String heapFileDateSearch(Date[] dateRange) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(datafile, "r");
        long startTime = System.currentTimeMillis();

        while(raf.getFilePointer() < raf.length()) {
            int count = 0;
            while (pageSize - count > ARTIST_SIZE) {
                char[] name = new char[70];             // 140 bytes
                Date[] birthdate = new Date[2];         // 16 bytes
                char[] birthPlace = new char[160];      // 320 bytes
                Date[] deathDate = new Date[2];                       // 16 bytes
                char[] field = new char[250];           // 500 bytes
                char[] genre = new char[390];           // 780 bytes
                char[] instrument = new char[545];      // 1090 bytes
                char[] nationality = new char[120];     // 240 bytes
                char[] thumbnail = new char[295];       // 590 bytes
                int wikiPageID;                         // 4 bytes
                char[] description = new char[470];     // 940 bytes

                for (int i = 0; i < name.length; i++) {
                    name[i] = raf.readChar();
                    count += 2;
                }

                for (int i = 0; i < birthdate.length; i++) {
                    long testDate = raf.readLong();
                    count += 8;
                    if(testDate != 0L) {
                        birthdate[i] = new Date(testDate);
                    }
                }

                for (int i = 0; i < birthPlace.length; i++) {
                    birthPlace[i] = raf.readChar();
                    count += 2;
                }

                for (int i = 0; i < deathDate.length; i++) {
                    long testDate = raf.readLong();
                    count += 8;
                    if(testDate != 0L) {
                        deathDate[i] = new Date(testDate);
                    }
                }

                for (int i = 0; i < field.length; i++) {
                    field[i] = raf.readChar();
                    count += 2;
                }

                for (int i = 0; i < genre.length; i++) {
                    genre[i] = raf.readChar();
                    count += 2;
                }

                for (int i = 0; i < instrument.length; i++) {
                    instrument[i] = raf.readChar();
                    count += 2;
                }

                for (int i = 0; i < nationality.length; i++) {
                    nationality[i] = raf.readChar();
                    count += 2;
                }

                for (int i = 0; i < thumbnail.length; i++) {
                    thumbnail[i] = raf.readChar();
                    count += 2;
                }

                wikiPageID = raf.readInt();
                count += 4;

                for (int i = 0; i < description.length; i++) {
                    description[i] = raf.readChar();
                    count += 2;
                }
                heap.addToArtistArray(new Artist(name, birthdate, birthPlace, deathDate, field, genre, instrument, nationality, thumbnail, wikiPageID, description));
            }
            raf.skipBytes(pageSize - count);

            ArrayList<String> found = heap.birthdateSearch(dateRange);
            if (!found.isEmpty()) {
                for(String s : found) {
                    System.out.println(s);
                }
            }
            heap.clearHeap();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        return Long.toString(totalTime);
    }
}
