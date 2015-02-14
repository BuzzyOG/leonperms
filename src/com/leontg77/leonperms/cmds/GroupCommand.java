package com.leontg77.leonperms.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.leontg77.leonperms.Group;

public class GroupCommand extends PermsCommand {

	public GroupCommand() {
		super("group", "<name> <add|remove> <perm>");
	}
	
	public void onCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /perm group " + this.getArgs());
			return;
		}
		
		Group group = new Group(args[0]);
		
		if (args.length < 2) {
			if (group != null) {
				sender.sendMessage(ChatColor.RED + group.getName() + "'s users:");
				for (String users : group.getUsers()) {
					sender.sendMessage(ChatColor.GRAY + users);
				}
				sender.sendMessage(ChatColor.RED + group.getName() + "'s permissions:");
				for (String perm : group.getPerms()) {
					sender.sendMessage(ChatColor.GRAY + perm);
				}
				sender.sendMessage(ChatColor.RED + group.getName() + "'s parents:");
				for (Group parents : group.getParents()) {
					sender.sendMessage(ChatColor.GRAY + parents.getName());
				}
			}
		} else {
			if (args[1].equalsIgnoreCase("add")) {
				group.addPermission(args[2]);
				sender.sendMessage(ChatColor.GRAY + "Permission " + args[2] + " added.");
			}
			else if (args[1].equalsIgnoreCase("remove")) {
				group.removePermission(args[2]);
				sender.sendMessage(ChatColor.GRAY + "Permission " + args[2] + " removed.");
			}
			else {
				sender.sendMessage(ChatColor.RED + "Usage: /perm group " + this.getArgs());
			}
		}
	}
}