package me.IvanMazzoli.DoorLock;

import java.util.logging.Logger;

import me.IvanMazzoli.DoorLock.Events.BlockBreak;
import me.IvanMazzoli.DoorLock.Events.EntityExplode;
import me.IvanMazzoli.DoorLock.Events.InventoryClick;
import me.IvanMazzoli.DoorLock.Events.InventoryClose;
import me.IvanMazzoli.DoorLock.Events.InventoryDrag;
import me.IvanMazzoli.DoorLock.Events.InventoryMoveItem;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.Bukkit;
import org.bukkit.block.Dropper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
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
		pm.registerEvents(new InventoryClick(), this);
		pm.registerEvents(new InventoryClose(), this);
		pm.registerEvents(new InventoryDrag(), this);
		pm.registerEvents(new InventoryMoveItem(), this);
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
