package com.dave00.subtitlewarrior.smoke;

import com.dave00.subtitlewarrior.srtengine.SrtDisplayer;
import com.dave00.subtitlewarrior.srtengine.SrtEngine;

/**
 * @author dave00
 */
public class PlayItHard {

    public static void main(String[] args) {
        
        SrtEngine.loadFromFile("PlayTest.srt");

        SrtEngine.subscribe(new SrtDisplayer() {

            public void display(String text) {
                System.out.println(text);
            }

            public void clear() {
                System.out.println("Empty");
            }
        });

        SrtEngine.play();
        
    }
}
