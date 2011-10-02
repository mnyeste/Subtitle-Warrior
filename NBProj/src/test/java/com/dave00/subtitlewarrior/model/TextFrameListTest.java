package com.dave00.subtitlewarrior.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TextFrameListTest {

    @Test
    public void testInit() {
        
        List<TextFrame> list = new ArrayList<TextFrame>();
        list.add(new TextFrame(0, 1));
        list.add(new TextFrame(2, 3));
        
        TextFrameList frameList = new TextFrameList(list);
        
        Assert.assertEquals(list.size(), frameList.getFrameCount());
        
    }
}