package me.kitsoko.deathkeeper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathKeeperCmd implements CommandExecutor {
	
	private Main main;
	public DeathKeeperCmd(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
	if(args[0].equalsIgnoreCase("rl") || args[0].equalsIgnoreCase("reload")) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage("§7[§6DeathKeeper§7] : §2The plugin has succesfully reload");
		}else {
			System.out.println("[DeathKeeper] : The plugin has succesfully reload");
		}
		main.onEnable();
	}
		return false;
	}

}
