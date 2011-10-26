package com.dave00.subtitlewarrior.srtengine;

/**
 * @author dave00
 */
public class WatchableSrtDisplayer implements SrtDisplayer {

    int counter;

    public void display(String text) {
        counter++;
        System.out.println("WSD: " + text);
    }

    public void clear() {
        System.out.println("WSD: EMPTY");
    }
    
    
}
