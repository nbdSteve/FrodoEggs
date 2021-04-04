package gg.steve.mc.frodo.eggs.core;

import gg.steve.mc.frodo.eggs.FrodoEggs;
import gg.steve.mc.frodo.eggs.framework.yml.Files;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EggManager {
    private static List<Egg> eggs;

    public static void onLoad() {
        eggs = new ArrayList<>();
        loadEggs();
        setupParticles();
    }

    public static void onShutdown() {
        saveEggs();
        if (eggs != null && !eggs.isEmpty()) eggs.clear();
    }

    public static void loadEggs() {
        YamlConfiguration conf = Files.DATA.get();
        for (String entry : conf.getKeys(false)) {
            UUID id = UUID.fromString(entry);
            EggGroup group = EggGroupManager.getGroup(conf.getString(entry + ".group"));
            World world = Bukkit.getWorld(conf.getString(entry + ".location.world"));
            Double x = conf.getDouble(entry + ".location.x");
            Double y = conf.getDouble(entry + ".location.y");
            Double z = conf.getDouble(entry + ".location.z");
            Location location = new Location(world, x, y, z);
            List<String> foundStrings = conf.getStringList(entry + ".found");
            List<UUID> found = new ArrayList<>();
            if (!foundStrings.isEmpty()) {
                for (String uuid : foundStrings) {
                    found.add(UUID.fromString(uuid));
                }
            }
            UUID placer = UUID.fromString(conf.getString(entry + ".placer"));
            Egg egg = new Egg(id, group, location, found, placer);
            eggs.add(egg);
            group.addEgg(egg);
        }
    }

    public static void saveEggs() {
        YamlConfiguration conf = Files.DATA.get();
        for (String entry : conf.getKeys(false)) {
            conf.set(entry, null);
        }
        if (eggs != null && !eggs.isEmpty()) {
            for (Egg egg : eggs) {
                egg.save(conf);
            }
        }
        Files.DATA.save();
    }

    public static Egg getEggByLocation(Location location) {
        if (eggs == null || eggs.isEmpty()) return null;
        for (Egg egg : eggs) {
            if (egg.getLocation().distance(location) <= 0.5d) {
                return egg;
            }
        }
        return null;
    }

    public static Egg getEggById(UUID id) {
        if (eggs == null || eggs.isEmpty()) return null;
        for (Egg egg : eggs) {
            if (egg.getId().equals(id)) return egg;
        }
        return null;
    }

    public static boolean addEgg(EggGroup group, Location placedAt, Player placer) {
        Egg egg = new Egg(group, placedAt, placer);
        group.addEgg(egg);
        return eggs.add(egg);
    }

    public static void removeEgg(Egg egg) {
        if (eggs == null || eggs.isEmpty()) return;
        if (!eggs.contains(egg)) return;
        egg.getGroup().removeEgg(egg);
        eggs.remove(egg);
    }

    public static void setupParticles() {
        Bukkit.getScheduler().runTaskTimer(FrodoEggs.getInstance(), () -> {
            for (Egg egg : eggs) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (egg.isFound(player)) continue;
                    Location loc = egg.getLocation().clone();
                    loc.add(0.5, 0.5, 0.5);
                    if (player.getLocation().getWorld() != null && !player.getLocation().getWorld().equals(egg.getLocation().getWorld()))
                        return;
                    double dist = loc.distance(player.getLocation());
                    if (dist >= 25d) return;
                    player.spawnParticle(egg.getGroup().getParticle(), loc, 7, 0.325, 0.325, 0.325);
                }
            }
        }, 0L, 10L);
    }

    public static List<Egg> getEggs() {
        return eggs;
    }
}
