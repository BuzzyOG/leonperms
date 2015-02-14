package com.leontg77.leonperms.cmds;

import org.bukkit.command.CommandSender;

public abstract class PermsCommand {

	private String name, args;
	
	public PermsCommand(String name, String args) {
		this.name = name;
		this.args = args;
	}
	
	public String getName() {
		return name;
	}
	
	public String getArgs() {
		return args;
	}
	
	public abstract void onCommand(CommandSender sender, String[] args);
}