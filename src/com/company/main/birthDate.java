package com.company.main;

import com.company.system.DBManager;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class birthDate {

    public static void main(String[] args) {
        LocalDate[] dates = {stringToDate(args[0]), stringToDate(args[1])};
        DBManager dbManager = new DBManager(getFileName());
    }

    /** Takes in a date string and turns it into a Local Date object
     * @param s the date string in format YYYYMMDD
     * @return a LocalDate object of the given string
     */
    private static LocalDate stringToDate(String s) {
        LocalDate d = null;
        try {
            d = LocalDate.parse(s, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            System.out.println("Arguments entered are not in correct date format");
        }
        return d;
    }

    /** Checks the entire resources directory and returns the name of the heap file contained within it
     * @return the name of the heap file within the resources directory
     */
    private static String getFileName() {
        String[] fileNames;
        String heapName = "";

        File f = new File("resources");
        fileNames = f.list();

        for (String file : fileNames) {
            if (file.contains("heap.")) {
                heapName = file;
            }
        }
        return heapName;
    }
}
