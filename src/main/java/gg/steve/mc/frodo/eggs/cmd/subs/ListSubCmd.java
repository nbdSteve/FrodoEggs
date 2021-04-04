package gg.steve.mc.frodo.eggs.cmd.subs;

import gg.steve.mc.frodo.eggs.core.Egg;
import gg.steve.mc.frodo.eggs.core.EggGroup;
import gg.steve.mc.frodo.eggs.core.EggGroupManager;
import gg.steve.mc.frodo.eggs.framework.cmd.SubCommand;
import gg.steve.mc.frodo.eggs.framework.message.DebugMessage;
import gg.steve.mc.frodo.eggs.framework.message.GeneralMessage;
import gg.steve.mc.frodo.eggs.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class ListSubCmd extends SubCommand {

    public ListSubCmd() {
        super("list", 2, 2, false, PermissionNode.LIST);
        addAlias("l");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        // /fe list group
        EggGroup group;
        try {
            group = EggGroupManager.getGroup(args[1]);
            if (group == null) throw new Exception();
        } catch (Exception e){
            DebugMessage.INVALID_GROUP.message(sender);
            return;
        }
        StringBuilder eggs = new StringBuilder();
        for (Egg egg : group.getEggs()) {
            eggs.append("&7" + egg.getId() + "\n");
        }
        GeneralMessage.EGG_LIST.message(sender, group.getName(), eggs.toString());
    }
}
