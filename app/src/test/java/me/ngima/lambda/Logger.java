package me.ngima.lambda;

/**
 * Created by mangi on 16/11/2017.
 */

public class Logger {

    public static void log(String logText) {
        System.out.println(logText);
    }

    public static void log(Integer logText) {
        System.out.println(logText.toString());
    }
}
