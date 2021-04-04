package gg.steve.mc.frodo.eggs.cmd.subs;

import gg.steve.mc.frodo.eggs.core.Egg;
import gg.steve.mc.frodo.eggs.core.EggManager;
import gg.steve.mc.frodo.eggs.framework.cmd.SubCommand;
import gg.steve.mc.frodo.eggs.framework.message.DebugMessage;
import gg.steve.mc.frodo.eggs.framework.message.GeneralMessage;
import gg.steve.mc.frodo.eggs.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class InfoSubCmd extends SubCommand {

    public InfoSubCmd() {
        super("info", 2, 2, false, PermissionNode.INFO);
        addAlias("i");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // /fe info id
        Egg egg;
        try {
            egg = EggManager.getEggById(UUID.fromString(args[1]));
            if (egg == null) throw new Exception();
        } catch (Exception e) {
            DebugMessage.INVALID_EGG.message(sender);
            return;
        }
        GeneralMessage.EGG_INFO.message(sender, String.valueOf(egg.getId()),
                egg.getGroup().getName(),
                egg.getLocation().getWorld().getName(),
                String.valueOf(egg.getLocation().getX()),
                String.valueOf(egg.getLocation().getY()),
                String.valueOf(egg.getLocation().getZ()),
                String.valueOf(egg.getFound().size()),
                egg.getPlacerName());
    }
}
