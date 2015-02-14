package com.leontg77.leonperms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Group management class
 * @author LeonTG77
 */
@SuppressWarnings("deprecation")
public class Group {
	private Settings settings = Settings.getInstance();

	private String name;
	private List<String> perms;
	private List<String> users;
	private List<Group> parents;
	
	public Group(String name) {
		this.name = name;
		this.perms = new ArrayList<String>();
		this.users = new ArrayList<String>();
		this.parents = new ArrayList<Group>();
		
		if (settings.getPerms().getConfigurationSection("groups." + name) == null) {
			settings.getPerms().createSection("groups." + name);
			settings.getPerms().set("groups." + name + ".permissions", new ArrayList<String>());
			settings.savePerms();
			return;
		}
		
		for (Player online : Bukkit.getServer().getOnlinePlayers()) {
			if (settings.getPerms().getStringList("users." + Perms.getUUID(online) + ".groups") != null && settings.getPerms().getStringList("users." + Perms.getUUID(online) + ".groups").contains(name)) {
				users.add(online.getName());
			}
		}
		for (OfflinePlayer offline : Bukkit.getServer().getOfflinePlayers()) {
			if (settings.getPerms().getStringList("users." + Perms.getUUID(offline) + ".groups") != null && settings.getPerms().getStringList("users." + Perms.getUUID(offline) + ".groups").contains(name)) {
				users.add(offline.getName());
			}
		}
		
		perms.clear();
		users.clear();
		parents.clear();
		for (String group : settings.getPerms().getStringList("groups." + name + ".parents")) {
			Group g = new Group(group);
			parents.add(g);
		}
		perms.addAll(settings.getPerms().getStringList("groups." + name + ".permissions"));
	}
	
	/**
	 * Gets the name of the group.
	 * @return the name of the group.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Checks if the group has the user.
	 * @param player the user.
	 * @return true if it has, false if not.
	 */
	public boolean hasUser(Player player) {
		return users.contains(player.getName());
	}
	
	/**
	 * Check if the group is the default one.
	 * @return true if it is the default.
	 */
	public boolean isDefault() {
		return settings.getPerms().getBoolean("groups." + name + "default");
	}
	
	/**
	 * Adds a permission for the group.
	 * @param perm the permission.
	 */
	public void addPermission(String perm) {
		for (String user : users) {
			Player player = Bukkit.getServer().getPlayer(user);
			new User(player).addPermission(perm);
		}
		List<String> perms = getPerms();
		perms.add(perm);
		settings.getPerms().set("groups." + name + ".permissions", perms);
		settings.savePerms();
	}
	
	/**
	 * Removes a permission for the group.
	 * @param perm the permission.
	 */
	public void removePermission(String perm) {
		for (String user : users) {
			Player player = Bukkit.getServer().getPlayer(user);
			new User(player).removePermission(perm);
		}
		List<String> perms = getPerms();
		perms.remove(perm);
		settings.getPerms().set("groups." + name + ".permissions", perms);
		settings.savePerms();
	}
	
	/**
	 * Adds an player to the group.
	 * @param player the player
	 */
	public void addUser(Player player) {
		User user = new User(player);
		for (String perm : getPerms()) {
			if (!user.getPerms().contains(perm)) {
				user.getAttachments().setPermission(perm, true);
			}
		}
		List<String> groups = settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".groups");
		groups.add(name);
		settings.getPerms().set("users." + Perms.getUUID(player) + ".groups", groups);
		settings.savePerms();
		users.add(player.getName());
	}
	
	/**
	 * Removes an player to the group.
	 * @param player the player
	 */
	public void removeUser(Player player) {
		User user = new User(player);
		for (String perm : getPerms()) {
			if (!user.getPerms().contains(perm)) {
				user.getAttachments().setPermission(perm, false);
			}
		}
		List<String> groups = settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".groups");
		groups.remove(name);
		settings.getPerms().set("users." + Perms.getUUID(player) + ".groups", groups);
		settings.savePerms();
		users.remove(player.getName());
	}
	
	/**
	 * Adds an player to the group.
	 * @param player the player
	 */
	public void addUser(OfflinePlayer player) {
		List<String> groups = settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".groups");
		groups.add(name);
		settings.getPerms().set("users." + Perms.getUUID(player) + ".groups", groups);
		settings.savePerms();
		users.add(player.getName());
	}
	
	/**
	 * Removes an player to the group.
	 * @param player the player
	 */
	public void removeUser(OfflinePlayer player) {
		List<String> groups = settings.getPerms().getStringList("users." + Perms.getUUID(player) + ".groups");
		groups.remove(name);
		settings.getPerms().set("users." + Perms.getUUID(player) + ".groups", groups);
		settings.savePerms();
		users.remove(player.getName());
	}

	/**
	 * Gets the perms for the group.
	 * @return a list of perms.
	 */
	public List<String> getPerms() {
		return perms;
	}

	/**
	 * Gets the users for the group.
	 * @return a list of users.
	 */
	public List<String> getUsers() {
		return users;
	}

	/**
	 * Gets the parents for the group.
	 * @return a list of parents.
	 */
	public List<Group> getParents() {
		return parents;
	}
}