package dev.hudsonprojects.api.common.lib.util;

public final class ThreadUtil {

    private ThreadUtil(){}

    public static void delay(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
