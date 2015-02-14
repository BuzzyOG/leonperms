package com.leontg77.leonperms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.leontg77.leonperms.cmds.CommandManager;
import com.leontg77.leonperms.listener.PlayerListener;
import com.leontg77.leonperms.utils.NameFetcher;
import com.leontg77.leonperms.utils.UUIDFetcher;

/**
 * The main class of leonperms.
 * @author LeonTG77
 */
public class Perms extends JavaPlugin {
	private Logger logger = Logger.getLogger("Minecraft");
	public static ArrayList<String> groups = new ArrayList<String>();
	public static Perms plugin;
	
	@Override
	public void onDisable() {
		PluginDescriptionFile file = this.getDescription();
		logger.info(file.getName() + " is now disabled."); 
		plugin = null;
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile file = this.getDescription();
		logger.info(file.getName() + " v" + file.getVersion() + " is now enabled."); 
		
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		plugin = this;
		
		getCommand("perm").setExecutor(new CommandManager());
		Settings.getInstance().setup(this);
	}

	/**
	 * Gets the default group.
	 * @return the default group.
	 */
	public static Group getDefaultGroup() {
		for (String group : Perms.groups) {
			Group groups = new Group(group);
			if (groups.isDefault()) {
				return groups;
			}
		}
		return null;
	}

	/**
	 * Gets the default group name.
	 * @deprecated Use {@link #getDefaultGroup()}.getName()
	 * @return the default group name.
	 */
	@Deprecated
	public static String getDefaultGroupName() {
		for (String group : Perms.groups) {
			Group groups = new Group(group);
			if (groups.isDefault()) {
				return groups.getName();
			}
		}
		return null;
	}
	
	/**
	 * Gets the target user.
	 * @param player the user.
	 * @return the user.
	 */
	public static User getUser(Player player) {
		return new User(player);
	}
	
	/**
	 * Gets the target group.
	 * @param name the group.
	 * @return the group.
	 */
	public static Group getGroup(String name) {
		return new Group(name);
	}
	
	/**
	 * Gets the name of a given uuid.
	 * @param uuid the uuid given.
	 * @return the player name.
	 */
	public static String getName(String uuid) {
		String name;
		
		try {
			name = new NameFetcher(Arrays.asList(UUID.fromString(uuid))).call().get(UUID.fromString(uuid));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
        }
		return name;
	}
	
	/**
	 * Gets the name of the given player.
	 * @param player the player given.
	 * @return The UUID.
	 */
	public static String getUUID(Player player) {
		String uuid;
		try {
			uuid = new com.leontg77.leonperms.utils.UUIDFetcher(Arrays.asList(player.getName())).call().get(player.getName()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return uuid;
	}
	
	/**
	 * Gets the name of the given offline player.
	 * @param player the offline player given.
	 * @return The UUID of offline player.
	 */
	public static String getUUID(OfflinePlayer player) {
		String uuid;
		try {
			uuid = new UUIDFetcher(Arrays.asList(player.getName())).call().get(player.getName()).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return uuid;
	}
}