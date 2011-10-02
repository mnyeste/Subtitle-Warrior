package com.dave00.subtitlewarrior.model;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dave00
 */
public class TextFrameTest {

    @Before
    public void setUp() {
        TextFrame.reset();
    }

    @Test
    public void testStampsAreCorrect() {

        long fromStamp = 1;
        long toStamp = 2;

        TextFrame frame = new TextFrame(fromStamp, toStamp);

        Assert.assertEquals(fromStamp, frame.getFromStamp());
        Assert.assertEquals(toStamp, frame.getToStamp());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectStamps() {

        long fromStamp = 2;
        long toStamp = 1;

        TextFrame frame = new TextFrame(fromStamp, toStamp);

    }
    // TODO: order should be a static counter???

    @Test
    public void testOrderCounter() {

        long fromStamp = 1;
        long toStamp = 2;

        TextFrame frame1 = new TextFrame(fromStamp, toStamp);
        TextFrame frame2 = new TextFrame(fromStamp, toStamp);

        Assert.assertEquals(1, frame1.getOrder());
        Assert.assertEquals(2, frame2.getOrder());

    }

    @Test
    public void testReset() {

        // Original
        Assert.assertEquals(1, TextFrame.getOrderCounter());

        new TextFrame(1, 2);
        
        // After creating a frame
        Assert.assertEquals(2, TextFrame.getOrderCounter());
        
        TextFrame.reset();
        
        // After reset
        Assert.assertEquals(1, TextFrame.getOrderCounter());

    }
}