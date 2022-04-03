package com.company.object;

import java.util.Date;
import java.text.SimpleDateFormat;

// One artist is the size of 4636 bytes
public class Artist {
    private char[] personName;       // 140 bytes
    private Date[] birthDate;                       // 16 bytes
    private char[] birthPlace;      // 320 bytes
    private Date[] deathDate;                       // 16 bytes
    private char[] field;           // 500 bytes
    private char[] genre;           // 780 bytes
    private char[] instrument;      // 1090 bytes
    private char[] nationality;     // 240 bytes
    private char[] thumbnail;       // 590 bytes
    private int wikiPageID;                         // 4 bytes
    private char[] description;     // 940 bytes

    /** Creates a new Artist
     */
    public Artist(char[] personName, Date[] birthDate, char[] birthPlace, Date[] deathDate, char[]  field, char[] genre, char[] instrument, char[] nationality, char[] thumbnail, int wikiPageID, char[] description) {
        this.personName= personName;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.deathDate = deathDate;
        this.field = field;
        this.genre = genre;
        this.instrument = instrument;
        this.nationality = nationality;
        this.thumbnail = thumbnail;
        this.wikiPageID = wikiPageID;
        this.description = description;
    }

    public Date[] getBirthdate() { return birthDate; }

    public String toString() {
        StringBuilder str = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int count = 0;

        str.append("\t{\n");
        str.append("\t\t\"name\": \"");
        while(personName[count] != '\0') {
            str.append(personName[count]);
            count++;
        }
        str.append("\",\n");
        count = 0;

        str.append("\t\t\"birthdate\": [ ");
        int x = 0;
        for (Date date : birthDate) {
            if (date != null) {
                str.append("\"").append(sdf.format(date)).append("\", ");
            } else if (x == 0) {
                str.append("\"NULL\", ");
            }
            x++;
        }
        str.append("],\n");

        str.append("\t\t\"birthplace\": [ ");
        String[] s = splitString(String.valueOf(birthPlace));
        for (String l : s) {
            if (l != null) {
                str.append("\"");
                while(l.charAt(count) != '\0') {
                    str.append(l.charAt(count));
                    if(!(l.length() > ++count)) {
                        break;
                    }
                }
                count = 0;
                str.append("\", ");
            }
        }
        str.append("],\n");

        str.append("\t\t\"deathdate\": [ ");
        x = 0;
        for (Date date : deathDate) {
            if (date != null) {
                str.append("\"").append(sdf.format(date)).append("\", ");
            } else if (x == 0) {
                str.append("\"NULL\", ");
            }
            x++;
        }
        str.append("],\n");

        str.append("\t\t\"field\": [ ");
        s = splitString(String.valueOf(field));
        for (String l : s) {
            if (l != null) {
                str.append("\"");
                while(l.charAt(count) != '\0') {
                    str.append(l.charAt(count));
                    if(!(l.length() > ++count)) {
                        break;
                    }
                }
                count = 0;
                str.append("\", ");
            }
        }
        str.append("],\n");

        str.append("\t\t\"genre\": [ ");
        s = splitString(String.valueOf(genre));
        for (String l : s) {
            if (l != null) {
                str.append("\"");
                while(l.charAt(count) != '\0') {
                    str.append(l.charAt(count));
                    if(!(l.length() > ++count)) {
                        break;
                    }
                }
                count = 0;
                str.append("\", ");
            }
        }
        str.append("],\n");

        str.append("\t\t\"instrument\": \"");
        while(instrument[count] != '\0') {
            str.append(instrument[count]);
            count++;
        }
        str.append("\",\n");
        count = 0;

        str.append("\t\t\"nationality\": \"");
        while(nationality[count] != '\0') {
            str.append(nationality[count]);
            count++;
        }
        str.append("\",\n");
        count = 0;

        str.append("\t\t\"thumbnail\": \"");
        while(thumbnail[count] != '\0') {
            str.append(thumbnail[count]);
            count++;
        }
        str.append("\",\n");
        count = 0;

        str.append("\t\t\"wikipageid\": ").append(wikiPageID).append(",\n");
        str.append("\t\t\"description\": \"");
        while(description[count] != '\0') {
            str.append(description[count]);
            count++;
        }
        str.append("\"\n");
        str.append("\t}");

        return str.toString();
    }

    /** Splits a string in format "{s1|s2|s3|...}" into a string array of the individual elements
     * @param s A string in the above format
     * @return A string array of the '|' separated elements contained within the curly braces
     */
    private String[] splitString(String s) {
        String[] returnString = new String[0];
        if(s != null) {
            if(s.contains("{")) {
                s = s.substring(1, s.indexOf("}"));
                returnString = s.split("\\|");
            } else {
                returnString = new String[]{s};
            }
        }
        return returnString;
    }
}
