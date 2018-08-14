package com.test.util;

public class Util {

	public static void sleep(Double secs) {
		try {
			int sms = (int) (secs * 1000);
			Thread.sleep(sms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
