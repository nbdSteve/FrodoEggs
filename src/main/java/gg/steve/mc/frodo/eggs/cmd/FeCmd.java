package gg.steve.mc.frodo.eggs.cmd;

import gg.steve.mc.frodo.eggs.core.Egg;
import gg.steve.mc.frodo.eggs.core.EggGroup;
import gg.steve.mc.frodo.eggs.core.EggGroupManager;
import gg.steve.mc.frodo.eggs.core.EggManager;
import gg.steve.mc.frodo.eggs.framework.cmd.SubCommandType;
import gg.steve.mc.frodo.eggs.framework.message.DebugMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FeCmd implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (!SubCommandType.HELP_CMD.getSub().isValidCommand(sender, args)) return true;
            SubCommandType.HELP_CMD.getSub().onCommand(sender, args);
            return true;
        }
        for (SubCommandType subCommand : SubCommandType.values()) {
            if (!subCommand.getSub().isExecutor(args[0])) continue;
            if (!subCommand.getSub().isValidCommand(sender, args)) return true;
            subCommand.getSub().onCommand(sender, args);
            return true;
        }
        DebugMessage.INVALID_COMMAND.message(sender);
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completion = new ArrayList<>();
        switch (args.length) {
            case 0:
                completion.add("info");
                completion.add("give");
                completion.add("list");
                completion.add("tp");
                break;
            case 1:
                switch (args[0]) {
                    case "give":
                    case "g":
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            completion.add(player.getName());
                        }
                        break;
                    case "info" :
                    case "i":
                    case "tp":
                        for (Egg egg : EggManager.getEggs()) {
                            completion.add(String.valueOf(egg.getId()));
                        }
                        break;
                    case "list":
                    case "l":
                        for (EggGroup group : EggGroupManager.getGroups()) {
                            completion.add(group.getName());
                        }
                        break;
                }
            case 2:
                switch (args[0]) {
                    case "give":
                    case "g":
                        for (EggGroup group : EggGroupManager.getGroups()) {
                            completion.add(group.getName());
                        }
                        break;
                }
            case 3:
                switch (args[0]) {
                    case "give":
                    case "g":
                        completion.add("1");
                }
        }
        return completion;
    }
}
