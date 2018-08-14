package com.test.util;

import org.apache.log4j.Logger;
import org.testng.Reporter;

public class Log {
	private static Logger logger;

    @SuppressWarnings("unused")
	private static String filePath = "src/log4j.properties";

//    private static boolean flag = false;

//    private static synchronized void getPropertyFile() {
//        logger = Logger.getLogger("TestProject");
//        PropertyConfigurator.configure(new File(filePath).getAbsolutePath());
//        flag = true;
//    }
//
//    private static void getFlag() {
//        if (flag == false)
//            Log.getPropertyFile();
//    }
    
    static{
    	 logger = Logger.getLogger("TestProject");
         //PropertyConfigurator.configure(new File(filePath).getAbsolutePath());
    }

    @SuppressWarnings("static-access")
	public static void logInfo(Object message) {
//        Log.getFlag();
        logger.info(message);
        Reporter.log(new TimeString().getSimpleDateFormat()+" : "+message);
    }

    @SuppressWarnings("static-access")
	public static void logError(Object message) {
//        Log.getFlag();
        logger.error(message);
        Reporter.log(new TimeString().getSimpleDateFormat()+" : "+message);
    }

    @SuppressWarnings("static-access")
	public static void logWarn(Object message) {
//        Log.getFlag();
        logger.warn(message);
        Reporter.log(new TimeString().getSimpleDateFormat()+" : "+message);
    }
}
