package me.kitsoko.deathkeeper;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class EventListener implements Listener {
	
	Main main;
	public EventListener(Main main) {
		this.main = main;
	}
	
	HashMap<String, ArrayList<ItemStack>> itemsToGive = new HashMap<String, ArrayList<ItemStack>>();
	
	@EventHandler
	public void death(PlayerDeathEvent e) {
		Player p = e.getEntity();
		int i = -1;
		ArrayList<ItemStack> toGive = new ArrayList<ItemStack>();
		ArrayList<Integer> toRemove = new ArrayList<Integer>(); 
		for(ItemStack drop : e.getDrops()) {
			i++;
			if(!drop.hasItemMeta()) continue;
			net.minecraft.server.v1_8_R3.ItemStack nmsIt = CraftItemStack.asNMSCopy(drop);
			if(!nmsIt.hasTag()) continue;
			NBTTagCompound tag = nmsIt.getTag();
			String type = tag.getString("type");
			if(!type.equals("never lost sword")) continue;
			toGive.add(drop);
			toRemove.add(0, i);
		}
		for(int integer : toRemove) {
			e.getDrops().remove(integer);
		}
		for(ItemStack drop : e.getDrops()) {
			Bukkit.getWorld(p.getWorld().getName()).dropItemNaturally(p.getLocation(), drop);
		}
		if(toGive.size() > 0) itemsToGive.put(p.getName(), toGive);
		e.getDrops().clear();
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p.spigot().respawn();
			}
		}.runTaskLater(main, 1);
	}
	
	@EventHandler
	public void onRevive(PlayerRespawnEvent e) {
		if(itemsToGive.containsKey(e.getPlayer().getName())) {
			ArrayList<ItemStack> toGive = itemsToGive.get(e.getPlayer().getName());
			for(ItemStack it : toGive) {
				e.getPlayer().getInventory().addItem(it);
			}
			itemsToGive.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() == null) return;
		Player killer = e.getEntity().getKiller();
		ItemStack it = killer.getItemInHand();
		if(it == null) return;
		if(!it.hasItemMeta()) return;
		net.minecraft.server.v1_8_R3.ItemStack nmsIt = CraftItemStack.asNMSCopy(it);
		NBTTagCompound tag = nmsIt.getTag();
		String type = tag.getString("counter");
		if(!type.equals("kill counter")) return;
		int kills = tag.getInt("kills");
		tag.setInt("kills", kills + 1);
		it = CraftItemStack.asCraftMirror(nmsIt);
		ItemMeta meta = it.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		for(String str : main.swordLore) {
			lore.add(str);
		}
		int integer = 0;
		for(String part : lore) {
			lore.set(integer, part.replace("$", "§").replace("%kill%", tag.getInt("kills") + "").replace("%name%", tag.getString("crafter")));
			integer++;
		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		killer.getInventory().setItemInHand(it);
	}
}








