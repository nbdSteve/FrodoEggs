package gg.steve.mc.frodo.eggs.core;

import gg.steve.mc.frodo.eggs.framework.nbt.NBTItem;
import gg.steve.mc.frodo.eggs.framework.utils.ColorUtil;
import gg.steve.mc.frodo.eggs.framework.utils.ItemBuilderUtil;
import gg.steve.mc.frodo.eggs.framework.yml.Files;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.security.auth.login.ConfigurationSpi;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EggGroup {
    private String name;
    private List<Egg> eggs;
    private List<Integer> skins;
    private List<String> commands, finalCommands, message;
    private Particle particle;
    private int particleCount;

    public EggGroup(String name) {
        this.name = name;
        this.eggs = new ArrayList<>();
        this.skins = Files.CONFIG.get().getIntegerList("groups." + this.name + ".skins");
        this.commands = Files.CONFIG.get().getStringList("groups." + this.name + ".commands");
        this.finalCommands = Files.CONFIG.get().getStringList("groups." + this.name + ".all-found-commands");
        this.message = Files.CONFIG.get().getStringList("groups." + this.name + ".message");
        this.particle = Particle.valueOf(Files.CONFIG.get().getString("groups." + this.name + ".particle.type").toUpperCase());
        this.particleCount = Files.CONFIG.get().getInt("groups." + this.name + ".particle.count");
    }

    public EggGroup(String name, List<Egg> eggs) {
        this.eggs = eggs;
        this.name = name;
        this.skins = Files.CONFIG.get().getIntegerList("groups." + this.name + ".skins");
        this.commands = Files.CONFIG.get().getStringList("groups." + this.name + ".commands");
        this.finalCommands = Files.CONFIG.get().getStringList("groups." + this.name + ".all-found-commands");
        this.message = Files.CONFIG.get().getStringList("groups." + this.name + ".message");
        this.particle = Particle.valueOf(Files.CONFIG.get().getString("groups." + this.name + ".particle.type").toUpperCase());
        this.particleCount = Files.CONFIG.get().getInt("groups." + this.name + ".particle.count");
    }

    public boolean addEgg(Egg egg) {
        if (this.eggs.contains(egg)) return false;
        return this.eggs.add(egg);
    }

    public boolean removeEgg(Egg egg) {
        if (!this.eggs.contains(egg)) return false;
        return this.eggs.remove(egg);
    }

    public String getName() {
        return name;
    }

    public List<Egg> getEggs() {
        return eggs;
    }

    public List<Integer> getSkins() {
        return skins;
    }

    public List<String> getCommands() {
        return commands;
    }

    public List<String> getFinalCommands() {
        return finalCommands;
    }

    public List<String> getMessage() {
        return message;
    }

    public Particle getParticle() {
        return particle;
    }

    public void messagePlayer(Player player) {
        for (String line : message) {
            player.sendMessage(ColorUtil.colorize(line));
        }
    }

    public ItemStack getEggItem() {
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial("hdb-" + getRandomSkin(), "0");
        ConfigurationSection section = Files.CONFIG.get().getConfigurationSection("item");
        builder.addName(section.getString("name").replace("{group}", this.name));
        builder.setLorePlaceholders("{group}");
        builder.addLore(section.getStringList("lore"), this.name);
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.setUnbreakable(section.getBoolean("unbreakable"));
        NBTItem item = new NBTItem(builder.getItem());
        item.setString("egg.group", this.name);
        item.setString("egg.rand-uuid", String.valueOf(UUID.randomUUID()));
        return item.getItem();
    }

    public void giveEggsToPlayer(int amount, Player player) {
        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(getEggItem());
        }
    }

    public int getRandomSkin() {
        if (this.skins == null || this.skins.isEmpty()) return 0;
        int index = new Random().nextInt(this.skins.size());
        return this.skins.get(index);
    }

    public boolean isFinalEgg(Player player) {
        for (Egg egg : this.eggs) {
            if (!egg.isFound(player)) return false;
        }
        return true;
    }

    public int getEggsFound(Player player) {
        int found = 0;
        for (Egg egg : this.eggs) {
            if (egg.isFound(player)) found++;
        }
        return found;
    }
}
