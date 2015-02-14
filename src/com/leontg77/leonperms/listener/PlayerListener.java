package com.leontg77.leonperms.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.leontg77.leonperms.Group;
import com.leontg77.leonperms.Perms;
import com.leontg77.leonperms.Settings;
import com.leontg77.leonperms.User;

public class PlayerListener implements Listener {
	private Settings settings = Settings.getInstance();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		User user = new User(event.getPlayer());
		if (user.getGroups().equals(null)) {
			user.addGroup(Perms.getDefaultGroup().getName());
		}
		for (String perms : user.getPerms()) {
			user.addPermission(perms);
		}
		for (Group perms : user.getGroups()) {
			perms.addUser(event.getPlayer());
		}
		settings.getPerms().set("users." + Perms.getUUID(event.getPlayer()) + ".name", event.getPlayer().getName());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		User user = new User(event.getPlayer());
		for (String perms : user.getPerms()) {
			user.removePermission(perms);
		}
		for (Group perms : user.getGroups()) {
			perms.removeUser(event.getPlayer());
		}
		settings.getPerms().set("users." + Perms.getUUID(event.getPlayer()) + ".name", event.getPlayer().getName());
		event.getPlayer().removeAttachment(user.getAttachments());
	}
}