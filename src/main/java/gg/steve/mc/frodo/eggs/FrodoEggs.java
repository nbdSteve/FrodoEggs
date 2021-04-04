package gg.steve.mc.frodo.eggs;

import gg.steve.mc.frodo.eggs.framework.SetupManager;
import gg.steve.mc.frodo.eggs.framework.yml.utils.FileManagerUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class FrodoEggs extends JavaPlugin {
    private static FrodoEggs instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        SetupManager.setupFiles(new FileManagerUtil(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        SetupManager.loadPluginCache();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SetupManager.shutdownPluginCache();
    }

    public static FrodoEggs getInstance() {
        return instance;
    }
}
