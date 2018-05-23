package com.github.upcraftlp.foolslib.api.util;

import com.github.upcraftlp.foolslib.FoolsLib;

public class ThreadUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch(InterruptedException e) {
            FoolsLib.getLogger().error("error during thread sleeping", e);
        }
    }
}
