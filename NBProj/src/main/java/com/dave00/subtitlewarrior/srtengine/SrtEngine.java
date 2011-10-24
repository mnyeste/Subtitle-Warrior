package com.dave00.subtitlewarrior.srtengine;

import com.dave00.subtitlewarrior.model.TextFrame;
import com.dave00.subtitlewarrior.model.TextFrameList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Responsible for loading the @{code TextFrame}s from the given .srt file
 * and return them fetched into a @{code TextFrameList}
 * 
 * @author dave00
 */
public class SrtEngine {

    private static SrtDisplayer subscriber;
    private static TextFrameList frameList;
    private static Timer timer = new Timer();

    public static void loadFromFile(String fileName) {

        List<String> textList = readFileToBuffer(fileName);

        List<TextFrame> list = parseTextList(textList);

        frameList = new TextFrameList(list);

    }

    public static void play() {

        SrtEngine.playScheduled(0, true, 0);

    }

    private static void playScheduled(final int index, boolean emptyScreen, final long delay) {

        final TextFrame frame = frameList.getList().get(index);

        long clearDel;

        if (index == 0) {
            clearDel = 0;
        } else {
            TextFrame lastFrame = frameList.getList().get(index - 1);
            clearDel = lastFrame.getToStamp() - lastFrame.getFromStamp();
        }

        final long clearDelay = clearDel;

        if (emptyScreen) {

            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    subscriber.clear();
                    playScheduled(index, false, clearDelay + delay);
                }
                // How long will see the text displayed before it gets cleared    
            }, clearDelay);
        } else {

            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    subscriber.display(frame.getText());

                    // If not last frame
                    if (index < frameList.getList().size() - 1) {
                        playScheduled(index + 1, true, frame.getFromStamp());
                    } else {
                        timer.cancel();
                    }

                }
                // How long will see empty screen before text gets displayed
            }, frame.getFromStamp() - delay);
        }


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

        long fromStamp = Long.MIN_VALUE;
        long toStamp = Long.MIN_VALUE;
        String text = null;

        Iterator<String> iterator = textList.iterator();

        while (iterator.hasNext()) {

            // TODO: update to Java 7?

            String line = iterator.next();

            // Safety check for unneeded duplication of empty lines between frames
            if (line.isEmpty() && mode != ReadMode.READ_TEXT) {
                continue;
            }

            switch (mode) {

                case READ_ORDER: {
                    mode = ReadMode.READ_TIME;
                    break;
                }
                case READ_TIME: {

                    int hourFrom = Integer.parseInt(line.substring(0, 2));
                    int minuteFrom = Integer.parseInt(line.substring(3, 5));
                    int secondFrom = Integer.parseInt(line.substring(6, 8));
                    int millisFrom = Integer.parseInt(line.substring(9, 12));

                    fromStamp = convertToStamp(hourFrom, minuteFrom, secondFrom, millisFrom);

                    int hourTo = Integer.parseInt(line.substring(17, 19));
                    int minuteTo = Integer.parseInt(line.substring(20, 22));
                    int secondTo = Integer.parseInt(line.substring(23, 25));
                    int millisTo = Integer.parseInt(line.substring(26, 29));

                    toStamp = convertToStamp(hourTo, minuteTo, secondTo, millisTo);

                    mode = ReadMode.READ_TEXT;
                    break;
                }
                case READ_TEXT: {

                    if (text == null) {
                        text = line;
                    } else {
                        text += "\n" + line;
                    }

                    if (line.isEmpty() || !iterator.hasNext()) {

                        TextFrame textFrame = new TextFrame(fromStamp, toStamp, text);
                        list.add(textFrame);

                        text = null;
                        mode = ReadMode.READ_ORDER;

                        break;

                    }

                    break;
                }

            }

        }

        return list;

    }

    public static void subscribe(SrtDisplayer displayer) {
        subscriber = displayer;
    }

    public static TextFrameList getFrameList() {
        return frameList;
    }
}