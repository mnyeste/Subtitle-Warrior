package com.dave00.subtitlewarrior.model;

/**
 * Represents a frame in the .srt file which is shown from 
 * fromStamp until toStamp.
 * 
 * @author dave00
 */
public class TextFrame {

    private static int orderCounter = 1;
    private int order;
    private long fromStamp;
    private long toStamp;
    private String text;

    public TextFrame(long fromStamp, long toStamp, String text) {

        if (fromStamp >= toStamp) {
            throw new IllegalArgumentException("toStamp should be bigger than fromStamp! fromStamp: [" + fromStamp + "] toStamp: [" + toStamp + "]");
        }

        if (fromStamp < 0 || toStamp < 0) {
            throw new IllegalArgumentException("fromStamp should be greater than or equal to zero! toStamp should be greater than zero");
        }


        if (text == null) {
            throw new IllegalArgumentException("text should not be null!");
        }

        this.order = orderCounter++;
        this.fromStamp = fromStamp;
        this.toStamp = toStamp;
        this.text = text;

    }

    public long getFromStamp() {
        return fromStamp;
    }

    public long getToStamp() {
        return toStamp;
    }

    public long getFromStamp(long beforeStamp) {
        return fromStamp - beforeStamp;
    }

    public long getToStamp(long beforeStamp) {
        return toStamp - beforeStamp;
    }

    public int getOrder() {
        return order;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextFrame{" + "order=" + order + ", fromStamp=" + fromStamp + ", toStamp=" + toStamp + ", text=" + text + '}';
    }

    // --- Static ---
    public static void reset() {
        orderCounter = 1;
    }

    public static int getOrderCounter() {
        return orderCounter;
    }
}