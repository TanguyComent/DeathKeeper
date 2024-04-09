package me.kitsoko.deathkeeper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class Main extends JavaPlugin{
	
	//names
	String swordName;
	String bowName;
	String rodName;
	String pickName;
	String chestplateName;
	String hammerName;
	
	//enchants
	ArrayList<Enchantment> swordEnchant = new ArrayList<Enchantment>();
	ArrayList<Enchantment> bowEnchant = new ArrayList<Enchantment>();
	ArrayList<Enchantment> rodEnchant = new ArrayList<Enchantment>();
	ArrayList<Enchantment> pickEnchant = new ArrayList<Enchantment>();
	ArrayList<Enchantment> chestplateEnchant = new ArrayList<Enchantment>();
	ArrayList<Enchantment> hammerEnchant = new ArrayList<Enchantment>();
	
	//levels
	ArrayList<Integer> swordEnchantLevel = new ArrayList<Integer>();
	ArrayList<Integer> bowEnchantLevel = new ArrayList<Integer>();
	ArrayList<Integer> rodEnchantLevel = new ArrayList<Integer>();
	ArrayList<Integer> pickEnchantLevel = new ArrayList<Integer>();
	ArrayList<Integer> chestplateEnchantLevel = new ArrayList<Integer>();
	ArrayList<Integer> hammerEnchantLevel = new ArrayList<Integer>();
	
	//lores
	public ArrayList<String> swordLore = new ArrayList<String>();
	public ArrayList<String> bowLore = new ArrayList<String>();
	public ArrayList<String> rodLore = new ArrayList<String>();
	public ArrayList<String> pickLore = new ArrayList<String>();
	public ArrayList<String> chestplateLore = new ArrayList<String>();
	public ArrayList<String> hammerLore = new ArrayList<String>();
	
	//files
	File sword = new File(this.getDataFolder() + "/sword.yml");
	File bow = new File(this.getDataFolder() + "/bow.yml");
	File rod = new File(this.getDataFolder() + "/rod.yml");
	File pick = new File(this.getDataFolder() + "/pick.yml");
	File chestplate = new File(this.getDataFolder() + "/chestplate.yml");
	File hammer = new File(this.getDataFolder() + "/hammer.yml");
	
	@Override
	public void onEnable() {
		swordEnchant.clear();
		bowEnchant.clear();
		rodEnchant.clear();
		pickEnchant.clear();
		chestplateEnchant.clear();
		hammerEnchant.clear();
		swordEnchantLevel.clear();
		bowEnchantLevel.clear();
		rodEnchantLevel.clear();
		pickEnchantLevel.clear();
		chestplateEnchantLevel.clear();
		hammerEnchantLevel.clear();
		swordLore.clear();
		bowLore.clear();
		rodLore.clear();
		pickLore.clear();
		chestplateLore.clear();
		hammerLore.clear();
		//event and cmd
		getCommand("send").setExecutor(new giverItemsCommand(this));
		getCommand("deathkeeper").setExecutor(new DeathKeeperCmd(this));
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		getServer().getPluginManager().registerEvents(new HammerEvent(this), this);
		
		//files and folder
		File folder = new File(this.getDataFolder() + "");
		if(!folder.exists()) folder.mkdir();
		if(!sword.exists()) {
			try {
				sword.createNewFile();
				defaultSword();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!bow.exists()) {
			try {
				bow.createNewFile();
				defaultBow();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!rod.exists()) {
			try {
				rod.createNewFile();
				defaultRod();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!pick.exists()) {
			try {
				pick.createNewFile();
				defaultPick();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!chestplate.exists()) {
			try {
				chestplate.createNewFile();
				defaultChestplate();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!hammer.exists()) {
			try {
				hammer.createNewFile();
				defaultHammer();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		swordValues();
		bowValues();
		rodValues();
		pickValues();
		chestplateValues();
		hammerValues();
	}
	
	//getter items value
	public void swordValues() {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(sword);
		this.swordName = conf.getString("name");
		conf.getString("lore").lines().forEach(swordLore::add);
		int i = 1;
		while(conf.get("enchantment." + i + ".enchant") != null) {
			Enchantment enchant = Enchantment.getByName(conf.getString("enchantment." + i + ".enchant"));
			this.swordEnchant.add(enchant);
			this.swordEnchantLevel.add(conf.getInt("enchantment." + i + ".level"));
			i++;
		}
	}
	
	public void bowValues() {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(bow);
		this.bowName = conf.getString("name");
		conf.getString("lore").lines().forEach(bowLore::add);
		int i = 1;
		while(conf.get("enchantment." + i + ".enchant") != null) {
			Enchantment enchant = Enchantment.getByName(conf.getString("enchantment." + i + ".enchant"));
			this.bowEnchant.add(enchant);
			this.bowEnchantLevel.add(conf.getInt("enchantment." + i + ".level"));
			i++;
		}
	}
	
	public void rodValues() {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(rod);
		this.rodName = conf.getString("name");
		conf.getString("lore").lines().forEach(rodLore::add);
		int i = 1;
		while(conf.get("enchantment." + i + ".enchant") != null) {
			Enchantment enchant = Enchantment.getByName(conf.getString("enchantment." + i + ".enchant"));
			this.rodEnchant.add(enchant);
			this.rodEnchantLevel.add(conf.getInt("enchantment." + i + ".level"));
			i++;
		}
	}
	
	public void pickValues() {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(pick);
		this.pickName = conf.getString("name");
		conf.getString("lore").lines().forEach(pickLore::add);
		int i = 1;
		while(conf.get("enchantment." + i + ".enchant") != null) {
			Enchantment enchant = Enchantment.getByName(conf.getString("enchantment." + i + ".enchant"));
			this.pickEnchant.add(enchant);
			this.pickEnchantLevel.add(conf.getInt("enchantment." + i + ".level"));
			i++;
		}
	}
	
	public void chestplateValues() {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(chestplate);
		this.chestplateName = conf.getString("name");
		conf.getString("lore").lines().forEach(chestplateLore::add);
		int i = 1;
		while(conf.get("enchantment." + i + ".enchant") != null) {
			Enchantment enchant = Enchantment.getByName(conf.getString("enchantment." + i + ".enchant"));
			this.chestplateEnchant.add(enchant);
			this.chestplateEnchantLevel.add(conf.getInt("enchantment." + i + ".level"));
			i++;
		}
	}
	
	public void hammerValues() {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(hammer);
		this.hammerName = conf.getString("name");
		conf.getString("lore").lines().forEach(hammerLore::add);
		int i = 1;
		while(conf.get("enchantment." + i + ".enchant") != null) {
			Enchantment enchant = Enchantment.getByName(conf.getString("enchantment." + i + ".enchant"));
			this.hammerEnchant.add(enchant);
			this.hammerEnchantLevel.add(conf.getInt("enchantment." + i + ".level"));
			i++;
		}
	}
	
	//generate default values
	public void defaultSword() throws IOException {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(sword);
		conf.set("name", "default");
		conf.set("enchantment.1.enchant", "DAMAGE_ALL");
		conf.set("enchantment.1.level", 5);
		conf.set("lore", "This is the item lore !\n" +
				"%name% = first owner name \n" +
				"use $ carater to put color into the name or the lore !\n" +
				"for exemple $4 correspont to red \n");
		conf.save(sword);
	}
	
	public void defaultBow() throws IOException {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(bow);
		conf.set("name", "default");
		conf.set("enchantment.1.enchant", "DAMAGE_ALL");
		conf.set("enchantment.1.level", 5);
		conf.set("lore", "This is the item lore !\n" +
				"%name% = first owner name \n" +
				"use $ carater to put color into the name or the lore !\n" +
				"for exemple $4 correspont to red \n");
		conf.save(bow);
	}
	
	public void defaultRod() throws IOException {

		YamlConfiguration conf = YamlConfiguration.loadConfiguration(rod);
		conf.set("name", "default");
		conf.set("enchantment.1.enchant", "DAMAGE_ALL");
		conf.set("enchantment.1.level", 5);
		conf.set("lore", "This is the item lore !\n" +
				"%name% = first owner name \n" +
				"use $ carater to put color into the name or the lore !\n" +
				"for exemple $4 correspont to red \n");
		conf.save(rod);
	}
	
	public void defaultPick() throws IOException {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(pick);
		conf.set("name", "default");
		conf.set("enchantment.1.enchant", "DAMAGE_ALL");
		conf.set("enchantment.1.level", 5);
		conf.set("lore", "This is the item lore !\n" +
				"%name% = first owner name \n" +
				"use $ carater to put color into the name or the lore !\n" +
				"for exemple $4 correspont to red \n");
		conf.save(pick);
	}
	
	public void defaultChestplate() throws IOException {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(chestplate);
		conf.set("name", "default");
		conf.set("enchantment.1.enchant", "DAMAGE_ALL");
		conf.set("enchantment.1.level", 5);
		conf.set("lore", "This is the item lore !\n" +
				"%name% = first owner name \n" +
				"use $ carater to put color into the name or the lore !\n" +
				"for exemple $4 correspont to red \n");
		conf.save(chestplate);
	}
	
	public void defaultHammer() throws IOException {
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(hammer);
		conf.set("name", "default");
		conf.set("enchantment.1.enchant", "DAMAGE_ALL");
		conf.set("enchantment.1.level", 5);
		conf.set("lore", "This is the item lore !\n" +
				"%name% = first owner name \n" +
				"use $ carater to put color into the name or the lore !\n" +
				"for exemple $4 correspont to red \n");
		conf.save(hammer);
	}
	
	//create items
	public ItemStack normalSword(String firstDetenteur) {
		
		ItemStack it = new ItemStack(Material.DIAMOND_SWORD);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(it);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInt("kills", 0);
		tag.setString("type", "never lost sword");
		tag.setString("counter", "kill counter");
		tag.setString("crafter", firstDetenteur);
		nmsItem.setTag(tag);
		it = CraftItemStack.asCraftMirror(nmsItem);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(this.swordName.replace("$", "§"));
		int i = 0;
		for(Enchantment enchant : this.swordEnchant) {
			meta.addEnchant(enchant, swordEnchantLevel.get(i++), true);
		}
		ArrayList<String> lore = new ArrayList<String>();
		for(String str : this.swordLore) {
			lore.add(str);
		}
		int integer = 0;
		for(String part : lore) {
			lore.set(integer, part.replace("$", "§").replace("%kill%", "0").replace("%name%", firstDetenteur));
			integer++;
		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		
		return it;
	}

	public ItemStack normalBow(String firstDetenteur) {
		
		ItemStack it = new ItemStack(Material.BOW);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(it);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInt("kills", 0);
		tag.setString("type", "never lost sword");
		tag.setString("counter", "kill counter");
		tag.setString("crafter", firstDetenteur);
		nmsItem.setTag(tag);
		it = CraftItemStack.asCraftMirror(nmsItem);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(this.bowName.replace("$", "§"));
		int i = 0;
		for(Enchantment enchant : this.bowEnchant) {
			meta.addEnchant(enchant, this.bowEnchantLevel.get(i++), true);
		}
		ArrayList<String> lore = new ArrayList<String>();
		for(String str : this.bowLore) {
			lore.add(str);
		}
		int integer = 0;
		for(String part : lore) {
			lore.set(integer, part.replace("$", "§").replace("%kill%", "0").replace("%name%", firstDetenteur));
			integer++;
		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		
		return it;
	}
	
	public ItemStack normalRod(String firstDetenteur) {
		
		ItemStack it = new ItemStack(Material.FISHING_ROD);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(it);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "never lost sword");
		tag.setString("crafter", firstDetenteur);
		nmsItem.setTag(tag);
		it = CraftItemStack.asCraftMirror(nmsItem);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(this.rodName.replace("$", "§"));
		int i = 0;
		for(Enchantment enchant : this.rodEnchant) {
			meta.addEnchant(enchant, this.rodEnchantLevel.get(i++), true);
		}
		ArrayList<String> lore = rodLore;
		int integer = 0;
		for(String part : lore) {
			lore.set(integer, part.replace("$", "§").replace("%name%", firstDetenteur));
			integer++;
		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		
		return it;
	}

	public ItemStack normalPick(String firstDetenteur) {
		
		ItemStack it = new ItemStack(Material.DIAMOND_PICKAXE);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(it);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "never lost sword");
		tag.setString("crafter", firstDetenteur);
		nmsItem.setTag(tag);
		it = CraftItemStack.asCraftMirror(nmsItem);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(this.pickName.replace("$", "§"));
		int i = 0;
		for(Enchantment enchant : this.pickEnchant) {
			meta.addEnchant(enchant, this.pickEnchantLevel.get(i++), true);
		}
		ArrayList<String> lore = pickLore;
		int integer = 0;
		for(String part : lore) {
			lore.set(integer, part.replace("$", "§").replace("%name%", firstDetenteur));
			integer++;
		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		
		return it;
	}

	public ItemStack normalChestplate(String firstDetenteur) {
		
		ItemStack it = new ItemStack(Material.DIAMOND_CHESTPLATE);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(it);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "never lost sword");
		tag.setString("crafter", firstDetenteur);
		nmsItem.setTag(tag);
		it = CraftItemStack.asCraftMirror(nmsItem);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(this.chestplateName.replace("$", "§"));
		int i = 0;
		for(Enchantment enchant : this.chestplateEnchant) {
			meta.addEnchant(enchant, this.chestplateEnchantLevel.get(i++), true);
		}
		ArrayList<String> lore = chestplateLore;
		int integer = 0;
		for(String part : lore) {
			lore.set(integer, part.replace("$", "§").replace("%name%", firstDetenteur));
			integer++;
		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		
		return it;
	}

	public ItemStack normalHammer(String firstDetenteur) {
		
		ItemStack it = new ItemStack(Material.DIAMOND_PICKAXE);
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(it);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "big hole");
		tag.setString("crafter", firstDetenteur);
		nmsItem.setTag(tag);
		it = CraftItemStack.asCraftMirror(nmsItem);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(this.hammerName.replace("$", "§"));
		int i = 0;
		for(Enchantment enchant : this.hammerEnchant) {
			meta.addEnchant(enchant, this.hammerEnchantLevel.get(i++), true);
		}
		ArrayList<String> lore = hammerLore;
		int integer = 0;
		for(String part : lore) {
			lore.set(integer, part.replace("$", "§").replace("%name%", firstDetenteur));
			integer++;
		}
		meta.setLore(lore);
		it.setItemMeta(meta);
		
		return it;
	}
}
