package me.IvanMazzoli.DoorLock;

import java.util.logging.Logger;

import me.IvanMazzoli.DoorLock.Events.BlockBreak;
import me.IvanMazzoli.DoorLock.Events.InventoryClose;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public static FileConfiguration config;
	public static Logger log;

	private static Plugin plugin;

	@Override
	public void onEnable() {

		plugin = this;
		log = this.getLogger();
		
		// Workaround temporaneo perchè senza cfg
		getDataFolder().mkdir();
		
		YamlUtils.importLocks();

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new EntityExplode(), this);
		pm.registerEvents(new InventoryClose(), this);
		pm.registerEvents(this, this);
		
		CommandManager commandManager = new CommandManager();
		commandManager.setup();
		getCommand("doorlock").setExecutor(commandManager);

		log.info("DoorLock enabled!");
	}

	@Override
	public void onDisable() {
		log.info("DoorLock disabled!");
	}

	public static Plugin getPlugin() {
		return plugin;
	}
}
