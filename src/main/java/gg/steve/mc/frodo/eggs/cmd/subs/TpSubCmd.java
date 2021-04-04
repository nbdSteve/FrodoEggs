package gg.steve.mc.frodo.eggs.cmd.subs;

import gg.steve.mc.frodo.eggs.core.Egg;
import gg.steve.mc.frodo.eggs.core.EggManager;
import gg.steve.mc.frodo.eggs.framework.cmd.SubCommand;
import gg.steve.mc.frodo.eggs.framework.message.DebugMessage;
import gg.steve.mc.frodo.eggs.framework.message.GeneralMessage;
import gg.steve.mc.frodo.eggs.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpSubCmd extends SubCommand {

    public TpSubCmd() {
        super("tp", 2, 2, true, PermissionNode.TP);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        //fe tp <uuid>
        Egg egg;
        try {
            egg = EggManager.getEggById(UUID.fromString(args[1]));
            if (egg == null) throw new Exception();
        } catch (Exception e) {
            DebugMessage.INVALID_EGG.message(sender);
            return;
        }
        Player player = (Player) sender;
        player.teleport(egg.getLocation());
        GeneralMessage.EGG_TP.message(sender, String.valueOf(egg.getId()),
                egg.getLocation().getWorld().getName(),
                String.valueOf(egg.getLocation().getX()),
                String.valueOf(egg.getLocation().getY()),
                String.valueOf(egg.getLocation().getZ()));
    }
}
