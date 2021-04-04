package gg.steve.mc.frodo.eggs.framework.cmd.misc;

import gg.steve.mc.frodo.eggs.FrodoEggs;
import gg.steve.mc.frodo.eggs.framework.cmd.SubCommand;
import gg.steve.mc.frodo.eggs.framework.message.GeneralMessage;
import gg.steve.mc.frodo.eggs.framework.permission.PermissionNode;
import gg.steve.mc.frodo.eggs.framework.yml.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadSubCmd extends SubCommand {

    public ReloadSubCmd() {
        super("reload", 1, 1, false, PermissionNode.RELOAD);
        addAlias("r");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(FrodoEggs.getInstance());
        FrodoEggs.getInstance().onLoad();
        Bukkit.getPluginManager().enablePlugin(FrodoEggs.getInstance());
        GeneralMessage.RELOAD.message(sender);
    }
}
