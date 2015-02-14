package com.leontg77.leonperms;
 
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * A settings class for managing data and stats files.
 * @author LeonTG77
 */
public class Settings {
 
	private Settings() {}    
	private static Settings instance = new Settings();  
	public static Settings getInstance() {
		return instance;
	}

	private FileConfiguration config;
	private File cfile;
	
	private FileConfiguration perms;
	private File pfile;
       
	/**
	 * Sets up the settings class, creates files that isn't created and makes sure everything is working.
	 * @param p the plugin or main class
	 */
	public void setup(Plugin p) {
		if (!p.getDataFolder().exists()) {
			p.getDataFolder().mkdir();
		}
               
		cfile = new File(p.getDataFolder(), "config.yml");
		
		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (IOException ex) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create config.yml!");
			}
		}     
		
		config = YamlConfiguration.loadConfiguration(cfile);
        
		pfile = new File(p.getDataFolder(), "perms.yml");
		
		if (!pfile.exists()) {
			try {
				pfile.createNewFile();
			} catch (IOException ex) {
				Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create perms.yml!");
			}
		}     
		
		perms = YamlConfiguration.loadConfiguration(pfile);
		
		perms.options().copyDefaults(true);
		savePerms();
		config.options().copyDefaults(true);
		saveConfig();
	}
    
	/**
	 * Gets the config file.
	 * @return the config file.
	 */
	public FileConfiguration getConfig() {
		return config;
	}
       
	/**
	 * Saves the config file.
	 */
	public void saveConfig() {
		try {
			config.save(pfile);
		} catch (IOException ex) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
		}
	}
	
	/**
	 * Reloads the config file.
	 */
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(pfile);
	}
    
	/**
	 * Gets the perms file.
	 * @return the perms file.
	 */
	public FileConfiguration getPerms() {
		return perms;
	}
       
	/**
	 * Saves the perms file.
	 */
	public void savePerms() {
		try {
			perms.save(pfile);
		} catch (IOException ex) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save perms.yml!");
		}
	}
	
	/**
	 * Reloads the perms file.
	 */
	public void reloadPerms() {
		perms = YamlConfiguration.loadConfiguration(pfile);
	}
}