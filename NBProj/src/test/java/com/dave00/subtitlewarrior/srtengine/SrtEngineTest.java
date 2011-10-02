package com.dave00.subtitlewarrior.srtengine;

import com.dave00.subtitlewarrior.model.TextFrame;
import com.dave00.subtitlewarrior.model.TextFrameList;
import com.dave00.subtitlewarrior.srtengine.SrtEngine;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author dave00
 */
public class SrtEngineTest {

    @Test
    public void testLoadFromFile() {

        String fileName = "The Simpsons 01x06 - Moaning Lisa.srt";

        TextFrameList frameList = SrtEngine.loadFromFile(fileName);

        Assert.assertEquals(216, frameList.getFrameCount());

    }

    @Test
    public void testParseTextList() {

        List<String> textList = new ArrayList<String>();

        textList.add("1");
        textList.add("00:00:02,100 --> 00:00:05,600");
        textList.add("Szoveg 1 soros");
        textList.add("");
        textList.add("2");
        textList.add("00:01:42,800 --> 00:01:50,100");
        textList.add("Szoveg 2 soros");
        textList.add("Szoveg 2 soros");

        List<TextFrame> parsedTextList = SrtEngine.parseTextList(textList);
        Assert.assertEquals(2, parsedTextList.size());

    }

    @Test
    public void testConvertToStamp() {
        
        // 13:42:08:654 = 654 + (8*1000) + (42 * 1000 * 60) + (13 * 1000 * 60 * 60) = 49328654
        long stamp = SrtEngine.convertToStamp(13, 42, 8, 654);
        Assert.assertEquals(49328654, stamp);                
        
    }
}