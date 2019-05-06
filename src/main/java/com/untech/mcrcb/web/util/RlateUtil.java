package com.untech.mcrcb.web.util;

import java.util.UUID;

public class RlateUtil {
	
	
	public static String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
    public static void main(String[] args) {
	    System.out.println(getUuid().length());
    }
}
