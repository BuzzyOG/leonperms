package com.leontg77.leonperms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

/**
 * User management class
 * @author LeonTG77
 */
public class User {
	private Settings settings = Settings.getInstance();

	private Player player;
	private List<String> perms;
	private List<Group> groups;
	private PermissionAttachment attach;
	
	/**
	 * Sets up everything for the player.
	 * @param player the player.
	 */
	public User(Player player) {
		this.player = player;
		this.perms = new ArrayList<String>();
		this.groups = new ArrayList<Group>();
		this.attach = new PermissionAttachment(Perms.plugin, player);
		
		if (settings.getPerms().getConfigurationSection("users." + Perms.getUUID(player)) == null) {
			settings.getPerms().createSection("users." + Perms.getUUID(player));
			settings.savePerms();
			return;
		}
		
		perms.clear();
		groups.clear();
		if (settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".permissions") == null) {
			settings.getPerms().set("users." + Perms.getUUID(player) + ".permissions", new ArrayList<String>());
			settings.savePerms();
			perms.addAll(Perms.getDefaultGroup().getPerms());
		} else {
			perms.addAll(settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".permissions"));
		}
		
		if (settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".groups") == null) {
			settings.getPerms().set("users." + Perms.getUUID(player) + ".groups", new ArrayList<String>());
			settings.savePerms();
			groups.add(Perms.getDefaultGroup());
		} else {
			for (String group : settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".groups")) {
				Group g = new Group(group);
				groups.add(g);
			}
		}
	}
	
	/**
	 * Gets the players groups.
	 * @return the groups.
	 */
	public List<Group> getGroups() {
		return groups;
	}
	
	/**
	 * Check if the player is in the target group.
	 * @param name the group.
	 * @return true if in group, flase if not.
	 */
	public boolean inGroup(String name) {
		Group group = new Group(name);
		return groups.contains(group);
	}
	
	/**
	 * Adds a permission to the player.
	 * @param perm the permission
	 */
	public void addPermission(String perm) {
		attach.setPermission(perm, true);
		List<String> perms = getPerms();
		perms.add(perm);
		settings.getPerms().set("users." + Perms.getUUID(player) + ".permissions", perms);
		settings.savePerms();
	}
	
	/**
	 * Removes a permission to the player.
	 * @param perm the permission
	 */
	public void removePermission(String perm) {
		attach.setPermission(perm, false);
		List<String> perms = getPerms();
		perms.remove(perm);
		settings.getPerms().set("users." + Perms.getUUID(player) + ".permissions", perms);
		settings.savePerms();
	}

	/**
	 * Gets a list of permissions for the player.
	 * @return the permissions.
	 */
	public List<String> getPerms() {
		return perms;
	}

	/**
	 * Adds a group to the player.
	 * @param name the group.
	 */
	public void addGroup(String name) {
		Group group = new Group(name);
		group.addUser(player);
	}
	
	/**
	 * Removes a group to the player.
	 * @param name the group.
	 */
	public void removeGroup(String name) {
		Group group = new Group(name);
		group.removeUser(player);
	}

	/**
	 * Gets the permission attachments for the player.
	 * @return the permission attachments.
	 */
	public PermissionAttachment getAttachments() {
		return attach;
	}
}