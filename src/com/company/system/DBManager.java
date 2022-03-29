package com.company.system;

import com.company.object.*;

import java.io.*;
import java.time.LocalTime;
import java.util.Scanner;

public class DBManager {

    private String datafile;
    private Heap heap = new Heap();

//    The datafile specified must be located in the resources folder
    public DBManager(String datafile) {
        this.datafile = "resources/" + datafile;
    }


    /** Creates a byte-array of artist objects from the datafile given in the constructor.
     *      This file must be the pre-prepared artists_processed.csv
     * @throws FileNotFoundException Thrown if the specified file cannot be found
     */
    public void createArtistsFromFile() throws IOException {
        File csv = new File(datafile);      // Read in the data file specified
        Scanner s = new Scanner(csv);
        // Loop over each line of the file for processing
        while(s.hasNextLine()) {
            String[] split = customSplit(s.nextLine());             // Split the string by delimiter ','
            heap.addToArtistArray(new Artist(split));               // Create a new artist and add it to the arraylist
        }
        s.close();
    }

    /**
     * @param pageSize The size of the byte dump to write to file. Writes one page at a time
     * @throws IOException If write-out fails
     */
    public String[] writeOutBytes(int pageSize) throws IOException {
        String[] outputs = new String[3];

        long startTime = System.currentTimeMillis();
//        Create and open the output file for writing
        FileOutputStream fos = new FileOutputStream(("resources/heap." + pageSize));
        BufferedOutputStream bos = new BufferedOutputStream(fos, pageSize);

//        Keep track of the number of artists and what artist is currently being iterated over
        int artistSize = heap.getArtists().size();
        int artistCount = 0;
        int pagesWritten = 0;

//        Ensure that code is not at the end of the artist array
        while (artistSize > 0) {
            byte[] byteOut = new byte[pageSize];        // Create a new byte array of the given page size
            int bytesAdded = 0;                         // Keep track of how many bytes have been added to this array

//            Ensure the next artist can be written to the current page
            while((pageSize - bytesAdded) >= heap.getArtists().get(artistCount).getBytes().length) {
//                Write each byte of this artist to the current byte array (page)
                for(Byte b : heap.getArtists().get(artistCount).getBytes()) {
                    byteOut[bytesAdded] = b;
                    bytesAdded++;
                }
//                Ensure to update the counters
                artistCount++;
                artistSize--;
                if(artistSize == 0) {break;}
            }
//            Write the current page out to file and flush the Buffered Output Stream
            bos.write(byteOut);
            bos.flush();
            pagesWritten++;
        }

        bos.close();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;


        outputs[0] = Integer.toString(artistCount);
        outputs[1] = Integer.toString(pagesWritten);
        outputs[2] = Long.toString(totalTime);

        return outputs;
    }

    /** A function which splits a row of a csv into individual string items
     *      Accounts for the existence of commas inside items themselves. Items with commas internal are assumed to be surrounded by quotation marks
     * @param row The csv row to split
     * @return A string array containing the individual elements of the csv row
     */
    private String[] customSplit(String row) {
        String[] split = new String[11];        // Create a new string array of size 11 to hold each item
        int count = 0;                          // Set a counter to keep track of the position in the split array
        boolean notInside = true;               // Set a boolean to keep track of whether the ',' occurs within quotation marks or not

        int start = 0;                          // Set a counter to keep track of the current start of the next item in the csv row

//        Loop over each character within the csv row
        for(int i = 0; i < row.length()-1; i++) {
//            If the character encountered is a comma and it is not deemed an "inside comma", add it to the array
            if(row.charAt(i) == ',' && notInside) {
                split[count] = row.substring(start,i);        // Substring the row from the beginning of the item to the comma encountered
                start = i + 1;                                // Set the start of the next item to the character after the comma
                count++;                                      // Increment the counter on the split array
//            If the character is a quotation mark, consider all commas encountered "inside commas" until the closing quotation mark is reached
            } else if (row.charAt(i) == '"') {
                notInside = !notInside;
            }
        }
        split[count] = row.substring(start);                  // Add the final item to the split array
        return split;
    }

}
