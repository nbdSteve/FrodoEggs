package gg.steve.mc.frodo.eggs.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Egg {
    private UUID id;
    private EggGroup group;
    private Location location;
    private List<UUID> found;
    private UUID placer;

    public Egg(EggGroup group, Location placedAt, Player placer) {
        this.id = UUID.randomUUID();
        this.group = group;
        this.location = placedAt;
        this.found = new ArrayList<>();
        this.placer = placer.getUniqueId();
    }

    public Egg(UUID id, EggGroup group, Location location, List<UUID> found, UUID placer) {
        this.id = id;
        this.group = group;
        this.location = location;
        this.found = found;
        this.placer = placer;
    }

    public void save(YamlConfiguration config) {
        config.set(this.id + ".group", this.group.getName());
        config.set(this.id + ".location.world", this.location.getWorld().getName());
        config.set(this.id + ".location.x", this.location.getX());
        config.set(this.id + ".location.y", this.location.getY());
        config.set(this.id + ".location.z", this.location.getZ());
        if (!this.found.isEmpty()) {
            List<String> ids = new ArrayList<>();
            for (UUID playerId : found) {
                ids.add(String.valueOf(playerId));
            }
            config.set(this.id + ".found", ids);
        } else config.set(this.id + ".found", this.found);
        config.set(this.id + ".placer", String.valueOf(this.placer));
    }

    public boolean isFound(Player player) {
        return this.found.contains(player.getUniqueId());
    }

    public String getPlacerName() {
        if (this.placer == null) return "error retrieving name.";
        return Bukkit.getOfflinePlayer(this.placer).getName();
    }

    public void setFound(Player player) {
        if (isFound(player)) return;
        this.found.add(player.getUniqueId());
    }

    public UUID getId() {
        return id;
    }

    public EggGroup getGroup() {
        return group;
    }

    public Location getLocation() {
        return location;
    }

    public List<UUID> getFound() {
        return found;
    }

    public UUID getPlacer() {
        return placer;
    }
}
