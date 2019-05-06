//-------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------

package com.untech.mcrcb.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Description of the class
 *
 * @author            lxt
 * @version           1.0
 * @since             2014-5-26
 */

public class Utils
{

    /**
     * 取得前一天时间
     * @param yearmonthday 20140601
     * @return 20140530
     */
    public static int getPreDay(int yearmonthday) {
    	
       /* Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, yearmonthday/10000);
        cal.set(Calendar.MONTH, (yearmonthday%10000)/100);
        cal.set(Calendar.DAY_OF_MONTH, (yearmonthday%100));
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.get(Calendar.YEAR) * 10000 + cal.get(Calendar.MONTH) * 100+cal.get(Calendar.DAY_OF_MONTH);*/
    	return addDay(yearmonthday+"",-1);
    }
    
    /**
     * 月份加减
     
     */
    public static String getAddMonth(String month ,int value){
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMM");  
        try {  
            Date d1 = df.parse(month);  
            System.out.println("d1=="+df.format(d1));  
            Calendar  g = Calendar.getInstance();  
            g.setTime(d1);  
            g.add(Calendar.MONTH,value);             
            Date d2 = g.getTime();  
            System.out.println("d2======="+df.format(d2));
            return df.format(d2);
        } catch (ParseException e) {              
            e.printStackTrace();  
        }
		return month;  
    }
    
    /**
     * 取得当月第一天时间
     * @return 20140530
     */
    public static int getFirstDayOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH)+1) * 100+cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 取得当前时间
     * @return 20140530
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH)+1) * 100+cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取当前日期 格式： format
     * 
     * @return
     */
    public static String getTodayString(String format) {
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        temp_str = sdf.format(dt);
        return temp_str;
    }
    
    
    public static int addDay(String strDate, int rd) {
		if (strDate == null)
			return 0;
		try {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyyMMdd");
			java.util.Date date = formatter.parse(strDate);
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(GregorianCalendar.DATE, rd);
			date = calendar.getTime();
			return Integer.parseInt(formatter.format(date));
		} catch (ParseException pe) {
			return 0;
		}
	}
    /**
     *日期相减，获取相隔天数
     * @param farther
     * @param child
     * @return
     * @throws Exception
     */
    public static long dateSubstract(String farther,String child){
    	Long number = 0l;
		try {
			SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");   	
			Date fartherDate = sdf.parse(farther);
			Date childDate = sdf.parse(child);
			number = fartherDate.getTime()-childDate.getTime();
			number = number/1000/60/60/24;
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
    	return number;
    }
    
    
    /**
     * 取得指定月份的最后一天
     * @author liujiansen
     * @since  2014年8月4日
     * @param strdate
     * @return
     */
    public static int getMonthEnd(String strdate)
    {
    	SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
		try {
			date = sim.parse(strdate+"01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return Integer.parseInt(sim.format(calendar.getTime()));
    }
    
    
    /**
     * 生成凭证号
     * 卫生院简拼+年度号+缴款类型（01 缴款 02 支出 03 拨付）+5位序列号
     * @param jianPin 卫生院简拼
     * @param type
     * @param number
     * @return
     */
    public static String createCode(String jianPin,String type,int number){
    	jianPin = "";
    	Calendar calendar = Calendar.getInstance();
    	//int year = calendar.get(Calendar.YEAR);
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	String year = format.format(new Date());
    	return jianPin+year+type+getNumStr(number);
    }
    	
    private static String getNumStr(int number){
    	number++;
    	if(number > 999){
    		number = 0;
    	}
    	if(number < 10){
    		return "00"+number;
    	}else if(number>=10 && number<100){
    		return "0"+number;
    	}else if(number>=100 && number<1000){
    		return ""+number;
    	}
    	/*else if(number>=1000 && number<10000){
    		return "0"+number;
    	}*/
    	else{
    		return ""+number;
    	}
    }
    

    
    public static void main(String[] args){
//        System.out.println(getPreDay(20140101));
//        System.out.println(getPreDay(20160301));
//        System.out.println(getPreDay(20140301));
//        System.out.println(getPreDay(20140501));
//        System.out.println(getPreDay(20140606));
     //   System.out.println(getAddMonth("201412",1));
    	System.out.println(dateSubstract("20151231","20151223"));
    	System.out.println("2013141509".substring(0, 8));
    }

}

