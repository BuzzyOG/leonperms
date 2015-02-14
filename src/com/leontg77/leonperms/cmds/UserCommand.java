package com.leontg77.leonperms.cmds;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.leontg77.leonperms.Group;
import com.leontg77.leonperms.Perms;
import com.leontg77.leonperms.Settings;

@SuppressWarnings("deprecation")
public class UserCommand extends PermsCommand {
	private Settings settings = Settings.getInstance();
	
	public UserCommand() {
		super("user", "<name> <setgroup|delgroup|add|remove> <group|perm>");
	}

	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /perm user " + this.getArgs());
			return;
		}

		Player target = Bukkit.getServer().getPlayer(args[0]);
		OfflinePlayer offline = Bukkit.getServer().getOfflinePlayer(args[0]);
		
		if (args.length < 2) {
			if (target != null) {
				sender.sendMessage(ChatColor.RED + args[0] + "'s permissions:");
				for (String perm : settings.getPerms().getStringList("users." + Perms.getUUID(target) + ".permissions")) {
					sender.sendMessage(ChatColor.GRAY + perm);
				}
				sender.sendMessage(ChatColor.RED + args[0] + "'s groups:");
				for (String groups : settings.getPerms().getStringList("users." + Perms.getUUID(target) + ".groups")) {
					sender.sendMessage(ChatColor.GRAY + groups);
				}
			} else {
				sender.sendMessage(ChatColor.RED + args[0] + "'s permissions:");
				for (String perm : settings.getPerms().getStringList("users." + Perms.getUUID(offline) + ".permissions")) {
					sender.sendMessage(ChatColor.GRAY + perm);
				}
				sender.sendMessage(ChatColor.RED + args[0] + "'s groups:");
				for (String groups : settings.getPerms().getStringList("users." + Perms.getUUID(offline) + ".groups")) {
					sender.sendMessage(ChatColor.GRAY + groups);
				}
			}
		} else {
			if (target == null) {
				if (args[1].equalsIgnoreCase("setgroup")) {
					Group group = new Group(args[2]);
					group.addUser(offline);
					sender.sendMessage(ChatColor.GRAY + args[1] + "is now a member of the group " + args[2] + ".");
				}
				
				else if (args[1].equalsIgnoreCase("delgroup")) {
					Group group = new Group(args[2]);
					group.removeUser(offline);
					sender.sendMessage(ChatColor.GRAY + args[1] + "is no longer a member of the group " + args[2] + ".");
				}
				
				else if (args[1].equalsIgnoreCase("add")) {
					List<String> perms = settings.getPerms().getStringList("users." + Perms.getUUID(offline) + ".permissions");
					perms.add(args[2]);
					settings.getPerms().set("users." + Perms.getUUID(offline) + ".permissions", perms);
					settings.savePerms();
					sender.sendMessage(ChatColor.GREEN + "Permission " + args[2] + " added to " + args[1]);
				}
				
				else if (args[1].equalsIgnoreCase("remove")) {
					List<String> perms = settings.getPerms().getStringList("users." + Perms.getUUID(offline) + ".permissions");
					perms.remove(args[2]);
					settings.getPerms().set("users." + Perms.getUUID(offline) + ".permissions", perms);
					settings.savePerms();
					sender.sendMessage(ChatColor.GREEN + "Permission " + args[2] + " removed from " + args[1]);
				}
				
				else {
					sender.sendMessage(ChatColor.RED + "Usage: /perm user " + this.getArgs());
				}
				return;
			}
			
			if (args[1].equalsIgnoreCase("setgroup")) {
				Group group = new Group(args[2]);
				group.addUser(target);
				sender.sendMessage(ChatColor.GRAY + args[0] + "is now a member of the group " + args[2] + ".");
			}
			
			else if (args[1].equalsIgnoreCase("delgroup")) {
				Group group = new Group(args[2]);
				group.removeUser(target);
				sender.sendMessage(ChatColor.GRAY + args[0] + "is no longer a member of the group " + args[2] + ".");
			}
			
			else if (args[1].equalsIgnoreCase("add")) {
				List<String> perms = settings.getPerms().getStringList("users." + Perms.getUUID(target) + ".permissions");
				perms.add(args[2]);
				settings.getPerms().set("users." + Perms.getUUID(target) + ".permissions", perms);
				settings.savePerms();
				sender.sendMessage(ChatColor.GREEN + "Permission " + args[2] + " added to " + args[0]);
			}
			
			else if (args[1].equalsIgnoreCase("remove")) {
				List<String> perms = settings.getPerms().getStringList("users." + Perms.getUUID(target) + ".permissions");
				perms.remove(args[2]);
				settings.getPerms().set("users." + Perms.getUUID(target) + ".permissions", perms);
				settings.savePerms();
				sender.sendMessage(ChatColor.GREEN + "Permission " + args[2] + " removed from " + args[0]);
			}
			
			else {
				sender.sendMessage(ChatColor.RED + "Usage: /perm user " + this.getArgs());
			}
		}
	}
}