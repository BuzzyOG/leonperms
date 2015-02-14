package com.leontg77.leonperms.cmds;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
	private ArrayList<PermsCommand> cmds = new ArrayList<PermsCommand>();
	
	public CommandManager() {
		cmds.add(new GroupCommand());
		cmds.add(new UserCommand());
		cmds.add(new CheckCommand());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("perm")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Help menu for leonperms:");
				for (PermsCommand c : cmds) {
					sender.sendMessage(ChatColor.GRAY + "/perm " + c.getName() + " " + c.getArgs());
				}
				return true;
			}
			
			ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
			a.remove(0);
			
			for (PermsCommand cmds : cmds) {
				if (cmds.getName().equalsIgnoreCase(args[0])) {
					try { 
						cmds.onCommand(sender, a.toArray(new String[a.size()])); 
					} catch (Exception e) { 
						sender.sendMessage(ChatColor.RED + "An error has occurred."); 
						e.printStackTrace();
						return true;
					}
					return true;
				}
			}
			sender.sendMessage(ChatColor.RED + "Help menu for leonperms:");
			for (PermsCommand c : cmds) {
				sender.sendMessage(ChatColor.GRAY + "/perm " + c.getName() + " " + c.getArgs());
			}
		}
		return true;
	}
}