package me.kitsoko.deathkeeper;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class giverItemsCommand implements CommandExecutor {
	
	private Main main;
	public giverItemsCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(sender instanceof Player) return false;
		if(args.length != 2) {
			System.out.println("Mauvaise utilisation : send [item] [player]");
			System.out.println("Item inconnu !");
			System.out.println("épée = sword");
			System.out.println("arc = bow");
			System.out.println("rod = rod");
			System.out.println("pioche = pick");
			System.out.println("plastron = plate");
			System.out.println("hammer = hammer");
			return false;
		}
		Player p = Bukkit.getPlayer(args[1]);
		try {
			p.getDisplayName();
		}catch (Exception e) {
			System.out.println("Player not found");
			return false;
		}
		if(isFull(p)) {
			System.out.println("The target player is full");
			p.sendMessage("§4[Error] : You are full");
			return false;
		}
		switch(args[0].toLowerCase()) {
		case "sword": 
			p.getInventory().addItem(main.normalSword(p.getName()));
			System.out.println("Le joueur " + p.getName() + " a bien recu une épée");
			break;
		case "bow":
			p.getInventory().addItem(main.normalBow(p.getName()));
			System.out.println("Le joueur " + p.getName() + " a bien recu un arc");
			break;
		case "rod":
			p.getInventory().addItem(main.normalRod(p.getName()));
			System.out.println("Le joueur " + p.getName() + " a bien recu une rod");
			break;
		case "pick":
			p.getInventory().addItem(main.normalPick(p.getName()));
			System.out.println("Le joueur " + p.getName() + " a bien recu une pioche");
			break;
		case "plate":
			p.getInventory().addItem(main.normalChestplate(p.getName()));
			System.out.println("Le joueur " + p.getName() + " à bien recu un plastron");
			break;
		case "hammer":
			p.getInventory().addItem(main.normalHammer(p.getName()));
			System.out.println("Le joueur " + p.getName() + " à bien recu un hammer");
			break;
		default:
			System.out.println("Item inconnu !");
			System.out.println("épée = sword");
			System.out.println("arc = bow");
			System.out.println("rod = rod");
			System.out.println("pioche = pick");
			System.out.println("plastron = plate");
			System.out.println("hammer = hammer");
			break;
		}
		return false;
	}
	
	public Boolean isFull(Player p) {
		for(int i = 0; i <= 35; i++) {
			if(p.getInventory().getItem(i) == null) return false;
		}
		return true;
	}

}
