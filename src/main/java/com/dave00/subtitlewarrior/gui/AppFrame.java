package com.dave00.subtitlewarrior.gui;

import javax.swing.*;
import com.dave00.subtitlewarrior.srtengine.*;

/**
 *
 * @author dave00
 */
public class AppFrame extends JFrame {
    
    public AppFrame()
    {
        
        setTitle("Subtitle Warrior");
        setSize(500, 140);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        SrtEngine.loadFromFile("PlayTest.srt");        
        
        initComponents();
        
        // pack();
        setVisible(true);
        
        SrtEngine.play();
        
    }
    
    private void initComponents()
    {
        
        SubtitlePanel pn = new SubtitlePanel();
        getContentPane().add(pn);
        
        SrtEngine.subscribe(pn);
        
    }
    
    public static void main(String[] args)
    {
        new AppFrame();
    }
    
}
