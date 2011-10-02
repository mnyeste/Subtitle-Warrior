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

    public TextFrame(long fromStamp, long toStamp) {

        if (fromStamp >= toStamp) {
            throw new IllegalArgumentException("toStamp should be bigger than fromStamp! fromStamp: [" + fromStamp + "] toStamp: [" + toStamp + "]");
        }

        this.order = orderCounter++;
        this.fromStamp = fromStamp;
        this.toStamp = toStamp;

    }

    public long getFromStamp() {
        return fromStamp;
    }

    public int getOrder() {
        return order;
    }

    public long getToStamp() {
        return toStamp;
    }

    @Override
    public String toString() {
        return "TextFrame{" + "order=" + order + ", fromStamp=" + fromStamp + ", toStamp=" + toStamp + '}';
    }
    
    // --- Static ---
    
    public static void reset() {
        
        orderCounter = 1;
        
    }

    public static int getOrderCounter() {
        return orderCounter;
    }
    
    
    
}