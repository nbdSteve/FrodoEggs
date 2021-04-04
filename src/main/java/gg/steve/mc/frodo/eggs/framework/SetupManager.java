package gg.steve.mc.frodo.eggs.framework;

import gg.steve.mc.frodo.eggs.cmd.FeCmd;
import gg.steve.mc.frodo.eggs.core.EggGroupManager;
import gg.steve.mc.frodo.eggs.core.EggManager;
import gg.steve.mc.frodo.eggs.framework.utils.LogUtil;
import gg.steve.mc.frodo.eggs.framework.yml.Files;
import gg.steve.mc.frodo.eggs.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.frodo.eggs.listener.EggListener;
import gg.steve.mc.frodo.eggs.papi.EggsExpansion;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {
    private static FileManagerUtil fileManagerUtil;

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     */
    public static void setupFiles(FileManagerUtil fm) {
        fileManagerUtil = fm;
        Files.CONFIG.load(fm);
        Files.PERMISSIONS.load(fm);
        Files.DATA.load(fm);
        Files.DEBUG.load(fm);
        Files.MESSAGES.load(fm);
    }

    public static void registerCommands(JavaPlugin instance) {
        instance.getCommand("fe").setExecutor(new FeCmd());
        instance.getCommand("fe").setTabCompleter(new FeCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(JavaPlugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new EggListener(), instance);
    }

    public static void registerEvent(JavaPlugin instance, Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public static void loadPluginCache() {
        // gui
        EggGroupManager.onLoad();
        EggManager.onLoad();
    }

    public static void shutdownPluginCache() {
        // gui
        EggManager.onShutdown();
        EggGroupManager.onShutdown();
    }

    public static void registerPlaceholderExpansions() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            LogUtil.info("PlaceholderAPI found, registering expansions with it now...");
            new EggsExpansion().register();
        }
    }

    public static FileManagerUtil getFileManagerUtil() {
        return fileManagerUtil;
    }
}
