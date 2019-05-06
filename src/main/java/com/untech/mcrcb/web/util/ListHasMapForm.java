package com.untech.mcrcb.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author            liuxiantao
 * @since             2014年5月4日
 */
public class ListHasMapForm
{
    private List<Map<String,String>> params = new ArrayList<Map<String,String>>();

    public List<Map<String, String>> getParams()
    {
        return params;
    }

    public void setParams(List<Map<String, String>> params)
    {
        this.params = params;
    }

   
}

