//-------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------

package com.untech.mcrcb.web.util;

import java.text.NumberFormat;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomNumberEditor;

/**
 * Description of the class
 * 
 * @author lxt
 * @version 1.0
 * @since 2013-1-20
 */

public class MyCustomNumberEditor extends CustomNumberEditor
{
    private static String NUMBER_REGEXP = "^((-)?([0-9]{1,3})?([,][0-9]{3}){0,4}([.][0-9]{0,4})?)$|^(-)?([0-9]{1,14})?([.][0-9]{1,4})$|^(-)?[0-9]{1,14}$";

    public MyCustomNumberEditor(Class numberClass, boolean allowEmpty) throws IllegalArgumentException
    {
        super(numberClass, allowEmpty);
    }

    public MyCustomNumberEditor(Class numberClass, NumberFormat numberFormat, boolean allowEmpty)
            throws IllegalArgumentException
    {
        super(numberClass, numberFormat, allowEmpty);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        text = text.trim();
        if (StringUtils.isBlank(text) && !Pattern.matches(NUMBER_REGEXP, text))
        {
            super.setAsText(null);
        }
        else
        {
            if (StringUtils.contains(text, ","))
            {
                super.setAsText(StringUtils.remove(text, ","));
            }
            else
            {
                super.setAsText(text);
            }
        }
    }
}
