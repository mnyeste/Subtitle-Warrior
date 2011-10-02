package com.dave00.subtitlewarrior.srtengine;

import com.dave00.subtitlewarrior.model.TextFrame;
import com.dave00.subtitlewarrior.model.TextFrameList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for loading the @{code TextFrame}s from the given .srt file
 * and return them fetched into a @{code TextFrameList}
 * 
 * @author dave00
 */
public class SrtEngine {

    public static TextFrameList loadFromFile(String fileName) {

        List<String> textList = readFileToBuffer(fileName);

        final List<TextFrame> list = parseTextList(textList);

        TextFrameList frameList = new TextFrameList(list);

        return frameList;

    }

    private static List<String> readFileToBuffer(String fileName) {

        try {

            List<String> textList = new ArrayList<String>();

            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            try {

                String line = null; //not declared within while loop
                /*
                 * readLine is a bit quirky :
                 * it returns the content of a line MINUS the newline.
                 * it returns null only for the END of the stream.
                 * it returns an empty String if two newlines appear in a row.
                 */
                while ((line = input.readLine()) != null) {

                    textList.add(line);

                }

                return textList;

            } finally {
                input.close();
            }

        } catch (IOException ex) {

            ex.printStackTrace();
            return null;

        }
    }

    static long convertToStamp(int hourFrom, int minuteFrom, int secondFrom, int millisFrom) {

        return millisFrom + (secondFrom * 1000) + (minuteFrom * 1000 * 60) + (hourFrom * 1000 * 60 * 60);

    }

    enum ReadMode {

        READ_ORDER, READ_TIME, READ_TEXT, READ_EMPTY_LINE
    }

    static List<TextFrame> parseTextList(List<String> textList) {

        List<TextFrame> list = new ArrayList<TextFrame>();

        ReadMode mode = ReadMode.READ_ORDER;

        int order;
        long fromStamp;
        long toStamp;

        for (String line : textList) {

            // TODO: update to Java 7?

            switch (mode) {

                case READ_ORDER: {
                    order = Integer.parseInt(line);
                    mode = ReadMode.READ_TIME;
                    break;
                }
                case READ_TIME: {

                    int hourFrom = Integer.parseInt(line.substring(0, 2));
                    int minuteFrom = Integer.parseInt(line.substring(3, 5));
                    int secondFrom = Integer.parseInt(line.substring(6, 8));
                    int millisFrom = Integer.parseInt(line.substring(9, 12));

                    fromStamp = convertToStamp(hourFrom, minuteFrom, secondFrom, millisFrom);

                    int hourTo = Integer.parseInt(line.substring(0, 2));
                    int minuteTo = Integer.parseInt(line.substring(3, 5));
                    int secondTo = Integer.parseInt(line.substring(6, 8));
                    int millisTo = Integer.parseInt(line.substring(9, 12));

                    toStamp = convertToStamp(hourTo, minuteTo, secondTo, millisTo);

                    mode = ReadMode.READ_TEXT;
                    break;
                }
                case READ_TEXT: {
                    
                    // TODO: TextFrame needs text field
                    
                    break;
                }

            }

        }

        /*
        textList.add("1");
        textList.add("00:00:02,100 --> 00:00:05,600");
        textList.add("Szoveg 1 soros");
        textList.add("");
        textList.add("2");
        textList.add("00:01:42,800 --> 00:01:50,100");
        textList.add("Szoveg 2 soros");
        textList.add("Szoveg 2 soros");
         */

        return list;

    }
}
