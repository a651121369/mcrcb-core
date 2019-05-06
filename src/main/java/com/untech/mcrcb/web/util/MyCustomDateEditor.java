//-------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------

package com.untech.mcrcb.web.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * Description of the class
 * 
 * @author lxt
 * @version 1.0
 * @since 2013-1-20
 */

public class MyCustomDateEditor extends PropertyEditorSupport
{

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        if (StringUtils.isBlank(text))
        {
            // Treat empty String as null value.
            setValue(null);
        }
        else
        {
            try
            {
                setValue(DateUtils.parseDate(text, new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" }));
            }
            catch (ParseException ex)
            {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText()
    {
        Date value = (Date) getValue();
        return (value != null ? DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss") : "");
    }
}
