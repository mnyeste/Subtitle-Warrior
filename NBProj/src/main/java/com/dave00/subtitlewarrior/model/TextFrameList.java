package com.dave00.subtitlewarrior.model;

import java.util.Collections;
import java.util.List;

/**
 * Holds a list of @{code TextFrame}s.
 * 
 * @author dave00
 */
public class TextFrameList {

    private List<TextFrame> list;
    
    public TextFrameList(List<TextFrame> list) {
        
        this.list = Collections.unmodifiableList(list);
        
    }
    
    public int getFrameCount()
    {
        return list.size();
    }
    
}
