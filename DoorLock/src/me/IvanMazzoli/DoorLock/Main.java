package me.IvanMazzoli.DoorLock;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import me.IvanMazzoli.DoorLock.Events.BlockBreak;
import me.IvanMazzoli.DoorLock.Events.BlockDispense;
import me.IvanMazzoli.DoorLock.Events.EntityExplode;
import me.IvanMazzoli.DoorLock.Events.InventoryClick;
import me.IvanMazzoli.DoorLock.Events.InventoryClose;
import me.IvanMazzoli.DoorLock.Events.InventoryDrag;
import me.IvanMazzoli.DoorLock.Events.InventoryMoveItem;
import me.IvanMazzoli.DoorLock.Events.ItemSpawn;
import me.IvanMazzoli.DoorLock.Events.PlayerInteract;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;
import net.gravitydevelopment.updater.Updater;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

public class Main extends JavaPlugin implements Listener {

	public static FileConfiguration config;
	public static Logger log;

	private static Plugin plugin;

	@Override
	public void onEnable() {

		plugin = this;
		log = this.getLogger();
		config = getConfig();

		// Config stuff
		if (!new File(this.getDataFolder().getPath() + File.separatorChar
				+ "config.yml").exists())
			saveDefaultConfig();

		// Checks for Updates
		checkUpdates();

		YamlUtils.importLocks();

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new BlockDispense(), this);
		pm.registerEvents(new EntityExplode(), this);
		pm.registerEvents(new InventoryClick(), this);
		pm.registerEvents(new InventoryClose(), this);
		pm.registerEvents(new InventoryDrag(), this);
		pm.registerEvents(new InventoryMoveItem(), this);
		pm.registerEvents(new ItemSpawn(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(this, this);

		CommandManager commandManager = new CommandManager();
		commandManager.setup();
		getCommand("doorlock").setExecutor(commandManager);

		try {
			inizializeMetrics();
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("DoorLock enabled!");
	}

	private void checkUpdates() {

		if (!config.getBoolean("Updater.Check for updates"))
			return;

		Updater.UpdateType updateType = config.getBoolean("Updater.Auto-update") ? Updater.UpdateType.DEFAULT
				: Updater.UpdateType.NO_DOWNLOAD;

		new Updater(this, 81834, this.getFile(), updateType, true);
	}

	private void inizializeMetrics() throws IOException {
		Metrics metrics = new Metrics(this);

		Graph lockQuantityGraph = metrics.createGraph("Total number of locks");

		lockQuantityGraph.addPlotter(new Metrics.Plotter("Locks") {

			@Override
			public int getValue() {
				return WorldUtils.lockList.size();
			}

		});

		metrics.start();
	}

	@Override
	public void onDisable() {
		log.info("DoorLock disabled!");
	}

	public static Plugin getPlugin() {
		return plugin;
	}
}
