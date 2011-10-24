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

        TextFrame frame = new TextFrame(fromStamp, toStamp, "Text");

        Assert.assertEquals(fromStamp, frame.getFromStamp());
        Assert.assertEquals(toStamp, frame.getToStamp());

    }

    @Test
    public void testIncorrectStamps() {

        // Testing from should be < than to

        try {

            long fromStamp = 2;
            long toStamp = 1;

            new TextFrame(fromStamp, toStamp, "Text");

            Assert.fail();

        } catch (IllegalArgumentException exception) {
            // Pass
        }

        // Testing for negative values

        try {

            long fromStamp = Long.MIN_VALUE;
            long toStamp = Long.MIN_VALUE + 1;

            new TextFrame(fromStamp, toStamp, "Text");

            Assert.fail();

        } catch (IllegalArgumentException exception) {
            // Pass
        }

    }

    @Test
    public void testActiveStamps() {

        long fromStamp = 5321;
        long toStamp = 6543;

        TextFrame textFrame = new TextFrame(fromStamp, toStamp, "Text");

        long currentTimeMillis = 4000;

        long activeFromStamp = textFrame.getFromStamp(currentTimeMillis);
        long activeToStamp = textFrame.getToStamp(currentTimeMillis);

        Assert.assertEquals(activeFromStamp, fromStamp - currentTimeMillis);
        Assert.assertEquals(activeToStamp, toStamp - currentTimeMillis);

    }

    @Test
    public void testTextIsCorrect() {
        TextFrame frame = new TextFrame(0, 1, "Text");

        Assert.assertEquals("Text", frame.getText());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInCorrectText() {
        TextFrame frame = new TextFrame(0, 1, null);
    }

    @Test
    public void testOrderCounter() {

        long fromStamp = 1;
        long toStamp = 2;

        TextFrame frame1 = new TextFrame(fromStamp, toStamp, "Text");
        TextFrame frame2 = new TextFrame(fromStamp, toStamp, "Text");

        Assert.assertEquals(1, frame1.getOrder());
        Assert.assertEquals(2, frame2.getOrder());

    }

    @Test
    public void testReset() {

        // Original
        Assert.assertEquals(1, TextFrame.getOrderCounter());

        new TextFrame(1, 2, "Text");

        // After creating a frame
        Assert.assertEquals(2, TextFrame.getOrderCounter());

        TextFrame.reset();

        // After reset
        Assert.assertEquals(1, TextFrame.getOrderCounter());

    }
}