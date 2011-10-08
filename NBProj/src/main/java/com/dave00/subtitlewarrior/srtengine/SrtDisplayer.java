package com.dave00.subtitlewarrior.srtengine;

/**
 * Implementations can subscribe to events from the @{code SrtEngine} class
 * @author dave00
 */
public interface SrtDisplayer {
    
    /**
     * Displays the give text
     */ 
    void display(String text);
    
}
