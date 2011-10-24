package com.dave00.subtitlewarrior.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author dave00
 */
public class SubtitlePanel extends JPanel 

    implements com.dave00.subtitlewarrior.srtengine.SrtDisplayer {

    private JLabel label;

    public SubtitlePanel() {

        setLayout(new BorderLayout());

        label = new JLabel("Proba");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        add(label);

        System.out.println("Created...");

    }

    /**
     * Displays the give text
     */
    public void display(String text) {

        label.setText(text);

    }

    /**
     * Shows empty screen (no text to display)
     */
    public void clear() {

        label.setText("");

    }
}