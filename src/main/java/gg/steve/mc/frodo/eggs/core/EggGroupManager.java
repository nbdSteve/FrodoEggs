package gg.steve.mc.frodo.eggs.core;

import gg.steve.mc.frodo.eggs.framework.yml.Files;

import java.util.ArrayList;
import java.util.List;

public class EggGroupManager {
    private static List<EggGroup> groups;

    public static void onLoad() {
        groups = new ArrayList<>();
        for (String name : Files.CONFIG.get().getConfigurationSection("groups").getKeys(false)) {
            groups.add(new EggGroup(name));
        }
    }

    public static void onShutdown() {
        if (groups != null && !groups.isEmpty()) groups.clear();
    }

    public static EggGroup getGroup(String name) {
        if (groups == null || groups.isEmpty()) return null;
        for (EggGroup group : groups) {
            if (group.getName().equalsIgnoreCase(name)) return group;
        }
        return null;
    }

    public static List<EggGroup> getGroups() {
        return groups;
    }
}
