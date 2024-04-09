package me.kitsoko.deathkeeper;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class HammerEvent implements Listener{
	
	Main main;
	public HammerEvent(Main main) {
		this.main = main;
	}
	
	HashMap<String, BlockFace> faces = new HashMap<String, BlockFace>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		this.faces.put(e.getPlayer().getName(), e.getBlockFace());
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block bl = e.getBlock();
		ItemStack it = p.getItemInHand();
		if(it == null || !it.hasItemMeta()) return;
		net.minecraft.server.v1_8_R3.ItemStack nmsIt = CraftItemStack.asNMSCopy(it);
		if(!nmsIt.hasTag()) return;
		NBTTagCompound tags = nmsIt.getTag();
		String type = tags.getString("type");
		if(!type.equals("big hole")) return;
		BlockFace face = this.faces.get(p.getName());
		ArrayList<Location> locs = new ArrayList<Location>();
		switch(face) {
			case UP:
				upOrDown(locs, bl);
				break;
			case DOWN:
				upOrDown(locs, bl);
				break;
			case NORTH:
				northOrSouth(locs, bl);
				break;
			case SOUTH:
				northOrSouth(locs, bl);
				break;
			case EAST:
				eastOrWest(locs, bl);
				break;
			case WEST:
				eastOrWest(locs, bl);
				break;
			default:
				break;
		}
		for(Location loc : locs) {
			if(loc.getBlock().getType() == Material.BEDROCK) continue;
			if(loc.getBlock().getType() == Material.ENDER_PORTAL) continue;
			loc.getBlock().breakNaturally();
		}
		this.faces.remove(p.getName());
	}
	
	private void upOrDown(ArrayList<Location> locs, Block bl) {
		locs.add(bl.getLocation().add(1, 0, 1));
		locs.add(bl.getLocation().add(1, 0, 0));
		locs.add(bl.getLocation().add(1, 0, -1));
		locs.add(bl.getLocation().add(0, 0, -1));
		locs.add(bl.getLocation().add(0, 0, 1));
		locs.add(bl.getLocation().add(-1, 0, 1));
		locs.add(bl.getLocation().add(-1, 0, 0));
		locs.add(bl.getLocation().add(-1, 0, -1));
	}
	
	private void northOrSouth(ArrayList<Location> locs, Block bl) {
		locs.add(bl.getLocation().add(1, 1, 0));
		locs.add(bl.getLocation().add(0, 1, 0));
		locs.add(bl.getLocation().add(-1, 1, 0));
		locs.add(bl.getLocation().add(-1, 0, 0));
		locs.add(bl.getLocation().add(1, 0, 0));
		locs.add(bl.getLocation().add(-1, -1, 0));
		locs.add(bl.getLocation().add(0, -1, 0));
		locs.add(bl.getLocation().add(1, -1, 0));
	}
	
	private void eastOrWest(ArrayList<Location> locs, Block bl) {
		locs.add(bl.getLocation().add(0, 1, 1));
		locs.add(bl.getLocation().add(0, 1, 0));
		locs.add(bl.getLocation().add(0, 1, -1));
		locs.add(bl.getLocation().add(0, 0, -1));
		locs.add(bl.getLocation().add(0, 0, 1));
		locs.add(bl.getLocation().add(0, -1, 1));
		locs.add(bl.getLocation().add(0, -1, 0));
		locs.add(bl.getLocation().add(0, -1, -1));
	}
}
