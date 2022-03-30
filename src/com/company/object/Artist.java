package com.company.object;

import com.sun.prism.shader.DrawEllipse_Color_AlphaTest_Loader;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
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

        str.append("{\n");
        str.append("\t\"name\":\"").append(String.valueOf(personName)).append("\",\n");

        str.append("\t\"birthdate\":[\n");
        for (Date date : birthDate) {
            if (date != null) {
                str.append("\t\t\"").append(sdf.format(date)).append("\"\n");
            }
        }
        str.append("\t],\n");

        str.append("\t\"birthplace\":\"").append(String.valueOf(birthPlace)).append("\",\n");

        str.append("\t\"deathdate\":[\n");
        for (Date date : deathDate) {
            if (date != null) {
                str.append("\t\t\"").append(sdf.format(date)).append("\"\n");
            }
        }
        str.append("\t],\n");

        str.append("\t\"field\":\"").append(String.valueOf(field)).append("\",\n");
        str.append("\t\"genre\":\"").append(String.valueOf(genre)).append("\",\n");
        str.append("\t\"instrument\":\"").append(String.valueOf(instrument)).append("\",\n");
        str.append("\t\"nationality\":\"").append(String.valueOf(nationality)).append("\",\n");
        str.append("\t\"thumbnail\":\"").append(String.valueOf(thumbnail)).append("\",\n");
        str.append("\t\"wikipageid\":\"").append(wikiPageID).append("\",\n");
        str.append("\t\"description\":\"").append(String.valueOf(description)).append("\"\n");
        str.append("}");
        return str.toString();
    }
}
