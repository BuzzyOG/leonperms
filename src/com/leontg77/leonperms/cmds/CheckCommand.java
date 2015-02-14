package com.leontg77.leonperms.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand extends PermsCommand {

	public CheckCommand() {
		super("check", "<player> <perm>");
	}
	
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage(ChatColor.RED + "You didn't enter a permission.");
			return;
		}
		
		Player target = Bukkit.getServer().getPlayer(args[0]);
		
		if (target == null) {
			sender.sendMessage(ChatColor.RED + "That player is not online.");
			return;
		}
		
		if (target.hasPermission(args[1])) {
			sender.sendMessage(ChatColor.GREEN + target.getName() + " does have the permission: " + args[1] + ".");
		} else {
			sender.sendMessage(ChatColor.RED + target.getName() + " does not have the permission: " + args[1] + ".");
		}
	}
}