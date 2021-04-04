package gg.steve.mc.frodo.eggs.listener;

import gg.steve.mc.frodo.eggs.core.Egg;
import gg.steve.mc.frodo.eggs.core.EggGroup;
import gg.steve.mc.frodo.eggs.core.EggGroupManager;
import gg.steve.mc.frodo.eggs.core.EggManager;
import gg.steve.mc.frodo.eggs.framework.nbt.NBTItem;
import gg.steve.mc.frodo.eggs.framework.utils.CommandUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EggListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.AIR) return;
        NBTItem item = new NBTItem(event.getItemInHand());
        String groupString;
        try {
            groupString = item.getString("egg.group");
            if (groupString == null || groupString.equalsIgnoreCase("")) throw new Exception();
        } catch (Exception e) {
            return;
        }
        EggGroup group = EggGroupManager.getGroup(groupString);
        ItemStack egg = group.getEggItem();
        event.getPlayer().setItemInHand(egg);
        EggManager.addEgg(group, event.getBlockPlaced().getLocation(), event.getPlayer());
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        if (event.isCancelled()) return;
        Egg egg;
        if ((egg = EggManager.getEggByLocation(event.getBlock().getLocation())) == null) return;
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        EggManager.removeEgg(egg.getId());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Egg egg;
        if ((egg = EggManager.getEggByLocation(event.getBlock().getLocation())) == null) return;
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        egg.setFound(event.getPlayer());
        EggManager.removeEgg(egg.getId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        Egg egg;
        if ((egg = EggManager.getEggByLocation(event.getClickedBlock().getLocation())) == null) return;
        if (egg.isFound(event.getPlayer())) return;
        egg.setFound(event.getPlayer());
        CommandUtil.execute(egg.getGroup().getCommands(), event.getPlayer());
        egg.getGroup().messagePlayer(event.getPlayer());
    }
}
