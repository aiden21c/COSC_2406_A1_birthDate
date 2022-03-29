package com.company.object;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Artist {
    private char[] personName = new char[70];
    private Date[] birthDate;
    private char[] birthPlace = new char[160];
    private Date[] deathDate;
    private char[] field = new char[250];
    private char[] genre = new char[390];
    private char[] instrument = new char[545];
    private char[] nationality = new char[120];
    private char[] thumbnail = new char[295];
    private int wikiPageID;
    private char[] description = new char[470];

    /** Takes in a string array from the artist_processed csv file and creates an artist object
     * @param stringArray String array must be in the default order from the csv file
     */
    public Artist(String[] stringArray) {
        this.personName= stringToChar(stringArray[0], personName);
        this.birthDate = stringToDate(stringArray[1]);
        this.birthPlace = stringToChar(stringArray[2], birthPlace);
        this.deathDate = stringToDate(stringArray[3]);
        this.field = stringToChar(stringArray[4], field);
        this.genre = stringToChar(stringArray[5], genre);
        this.instrument = stringToChar(stringArray[6], instrument);
        this.nationality = stringToChar(stringArray[7], nationality);
        this.thumbnail = stringToChar(stringArray[8], thumbnail);
        this.wikiPageID = Integer.parseInt(stringArray[9]);
        this.description = stringToChar(stringArray[10], description);
    }

    /** Takes in a string and converts it to an array of Date objects
     * @param s The string must be in format yyyy/MM/dd and separated by '|' if there are more than one.
     * @return  Returns a date object of the desired date. Returns null if format is incorrect
     */
    private Date[] stringToDate(String s) {
        String[] dateString = s.split("|");

        Date[] date = new Date[dateString.length];

        for (int i = 0; i < dateString.length; i++) {
            if (!dateString[i].equals("NULL")) {
                try {
                    date[i] = new SimpleDateFormat("yyyy/MM/dd").parse(dateString[i]);
                } catch (ParseException e) {
                    date[i] = null;
                }
            } else {
                date[i] = null;
            }
        }
        return date;
    }

    /** Creates a byte array of the artist object
     * @return A byte array of the object attributes of the artist
     * @throws IOException If data output stream fails
     */
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.write(toBytes(personName));

        dos.write(birthDate.length);                           // Writes a 1 or 2 depending on how many dates this record has
        for (Date d : birthDate) {
            if (d != null) {
                dos.writeLong(d.getTime());                   // 8 byte long
            } else {
                dos.writeLong(0L);
            }
        }

        dos.write(toBytes(birthPlace));

        dos.write(deathDate.length);                           // Writes a 1 or 2 depending on how many dates this record has
        for (Date d : deathDate) {
            if (d != null) {
                dos.writeLong(d.getTime());                   // 8 byte long
            } else {
                dos.writeLong(0L);
            }
        }

        dos.write(toBytes(field));
        dos.write(toBytes(genre));
        dos.write(toBytes(instrument));
        dos.write(toBytes(nationality));
        dos.write(toBytes(thumbnail));
        dos.writeInt(wikiPageID);                       // 4 byte integer
        dos.write(toBytes(description));
        dos.flush();
        dos.close();
        return bos.toByteArray();
    }

    /** Takes in a string and adds each character to a given character array, preserving the character array's designated size
     * @param s The string to convert
     * @param c the character array to add to
     * @return A character array containing the given string
     */
    private char[] stringToChar(String s, char[] c) {
        for(int i = 0; i < s.length(); i++) {
            c[i] = s.charAt(i);
        }
        return c;
    }

    /** Creates a byte array from a given chraracter array
     * @param c The character array
     * @return A byte array
     */
    private byte[] toBytes(char[] c) {
        CharBuffer cb = CharBuffer.wrap(c);
        ByteBuffer bb = Charset.forName("UTF-8").encode(cb);
        byte[] b = Arrays.copyOfRange(bb.array(), bb.position(), bb.limit());
        Arrays.fill(bb.array(), (byte) 0);
        return b;

    }
}
