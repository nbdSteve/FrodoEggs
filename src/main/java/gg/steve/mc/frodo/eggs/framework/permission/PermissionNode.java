package gg.steve.mc.frodo.eggs.framework.permission;

import gg.steve.mc.frodo.eggs.framework.yml.Files;
import org.bukkit.command.CommandSender;

public enum PermissionNode {
    // cmd
    RELOAD("command.reload"),
    GIVE("command.give"),
    HELP("command.help"),
    TP("command.tp"),
    INFO("command.info"),
    LIST("command.list");

    private String path;

    PermissionNode(String path) {
        this.path = path;
    }

    public String get() {
        return Files.PERMISSIONS.get().getString(this.path);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(get());
    }

    public static boolean isPurchasePerms() {
        return Files.PERMISSIONS.get().getBoolean("purchase.enabled");
    }
}
