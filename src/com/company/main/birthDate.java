package com.company.main;

import com.company.system.DBManager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class birthDate {

    public static void main(String[] args) {
        Date[] dates = {stringToDate(args[0]), stringToDate(args[1])};
        try {
            DBManager dbManager = new DBManager(getFileName());
            String timetaken = dbManager.heapFileDateSearch(dates);

            System.out.println("**********************************************************************************");
            System.out.println("Time Taken To Search (ms): " + timetaken);
            System.out.println("**********************************************************************************");
        } catch (NullPointerException | IOException e) {
            System.out.println("No heap file found in resources directory");
            System.exit(0);
        }
    }

    /** Takes in a date string and turns it into a Local Date object
     * @param s the date string in format YYYYMMDD
     * @return a LocalDate object of the given string
     */
    private static Date stringToDate(String s) {
        LocalDate localDate;
        Date date = null;
        try {
            localDate = LocalDate.parse(s, DateTimeFormatter.BASIC_ISO_DATE);
            ZoneId zi = ZoneId.systemDefault();
            date = Date.from(localDate.atStartOfDay(zi).toInstant());
        } catch (Exception e) {
            System.out.println("Arguments entered are not in correct date format");
        }
        return date;
    }

    /** Checks the entire resources directory and returns the name of the heap file contained within it
     * @return the name of the heap file within the resources directory
     */
    private static String getFileName() throws NullPointerException{
        String[] fileNames;
        String heapName = "";

        File f = new File("resources");
        fileNames = f.list();

        if (fileNames != null) {
            for (String file : fileNames) {
                if (file.contains("heap.")) {
                    heapName = file;
                }
            }
        }
        return heapName;
    }
}
