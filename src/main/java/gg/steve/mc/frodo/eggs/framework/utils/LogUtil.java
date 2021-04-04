package gg.steve.mc.frodo.eggs.framework.utils;

import gg.steve.mc.frodo.eggs.FrodoEggs;

public class LogUtil {

    public static void info(String message) {
        FrodoEggs.getInstance().getLogger().info(message);
    }

    public static void warning(String message) {
        FrodoEggs.getInstance().getLogger().warning(message);
    }
}
