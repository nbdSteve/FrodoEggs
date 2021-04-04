package gg.steve.mc.frodo.eggs.cmd.subs;

import gg.steve.mc.frodo.eggs.core.EggGroup;
import gg.steve.mc.frodo.eggs.core.EggGroupManager;
import gg.steve.mc.frodo.eggs.framework.cmd.SubCommand;
import gg.steve.mc.frodo.eggs.framework.message.DebugMessage;
import gg.steve.mc.frodo.eggs.framework.message.GeneralMessage;
import gg.steve.mc.frodo.eggs.framework.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSubCmd extends SubCommand {

    public GiveSubCmd() {
        super("give", 3, 4, false, PermissionNode.GIVE);
        addAlias("g");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        //fe give player group amount
        Player player;
        try {
            player = Bukkit.getPlayer(args[1]);
            if (player == null) throw new Exception();
        } catch (Exception e) {
            DebugMessage.INVALID_PLAYER.message(sender);
            return;
        }
        EggGroup group;
        try {
            group = EggGroupManager.getGroup(args[2]);
            if (group == null) throw new Exception();
        } catch (Exception e){
            DebugMessage.INVALID_GROUP.message(sender);
            return;
        }
        int amount = 1;
        if (args.length == 4) {
            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                DebugMessage.INVALID_AMOUNT.message(sender);
                return;
            }
        }
        group.giveEggsToPlayer(amount, player);
        GeneralMessage.EGG_RECEIVE.message(player, group.getName());
    }
}
