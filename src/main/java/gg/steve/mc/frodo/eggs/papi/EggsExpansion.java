package gg.steve.mc.frodo.eggs.papi;

import gg.steve.mc.frodo.eggs.FrodoEggs;
import gg.steve.mc.frodo.eggs.core.EggGroup;
import gg.steve.mc.frodo.eggs.core.EggGroupManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class EggsExpansion extends PlaceholderExpansion {

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "eggs";
    }

    @Override
    public String getAuthor() {
        return FrodoEggs.getInstance().getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return FrodoEggs.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        for (EggGroup group : EggGroupManager.getGroups()) {
            if (identifier.contains(group.getName())) {
                if (identifier.contains("found")) {
                    return String.valueOf(group.getEggsFound(player));
                } else if (identifier.contains("total")) {
                    return String.valueOf(group.getEggs().size());
                }
            }
        }
        return "Invalid Placeholder.";
    }
}
