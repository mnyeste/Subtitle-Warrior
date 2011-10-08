package com.dave00.subtitlewarrior.srtengine;

import com.dave00.subtitlewarrior.model.TextFrame;
import com.dave00.subtitlewarrior.model.TextFrameList;
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
        // String fileName = "Cut.srt";

        SrtEngine.loadFromFile(fileName);
        
        TextFrameList frameList = SrtEngine.getFrameList();

        Assert.assertEquals(216, frameList.getFrameCount());
        // Assert.assertEquals(3, frameList.getFrameCount());

    }

    @Test
    public void testParseTextList() {

        List<String> textList = new ArrayList<String>();

        textList.add("1");
        textList.add("00:00:02,100 --> 00:00:05,600");
        textList.add("Szoveg 1 soros");
        textList.add("");
        textList.add(""); // Intentional duplication of empty line
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

    @Test
    public void testSubscribe() {

        SrtDisplayer disp1 = new SrtDisplayer() {

            public void display(String text) {
            }
        };

        SrtDisplayer disp2 = new SrtDisplayer() {

            public void display(String text) {
            }
        };

        SrtEngine.subscribe(disp1);
        SrtEngine.subscribe(disp2);

        Assert.assertEquals(2, SrtEngine.getSubscribers().size());

    }

    /**
     * In this test we show that the @{code SrtEngine} is calling back the subscribed
     * members to 'display' actual text
     */
    @Test
    public void testPlayCallBackCount() {

        SrtEngine.loadFromFile("PlayTest.srt");

        // TODO: implement displayer with counter
        // TODO: play
        // TODO: assert count hit display method

        WatchableSrtDisplayer displayer = new WatchableSrtDisplayer();
        
        SrtEngine.subscribe(displayer);
        
        SrtEngine.play();
        
        Assert.assertEquals(3, displayer.counter); 


    }
    
    class WatchableSrtDisplayer implements SrtDisplayer
    {
        
        int counter;
        
        public void display(String text)
        {
            counter++;
        }
        
    }
    
}