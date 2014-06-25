package me.IvanMazzoli.DoorLock.Events;

import java.util.Arrays;
import java.util.Collections;

import me.IvanMazzoli.DoorLock.Lock;
import me.IvanMazzoli.DoorLock.Main;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClose implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {

		final Player player = (Player) event.getPlayer();
		Inventory inventory = event.getInventory();

		// First we must check if the InventoryType is a Dropper one
		if (!inventory.getType().equals(InventoryType.DROPPER))
			return;

		// We grab its location
		Dropper dropper = (Dropper) inventory.getHolder();
		final Location location = dropper.getLocation();

		// Then we check if it's a "lock"
		if (!WorldUtils.isLock(location))
			return;

		// Now let's load the configuration file for this lock
		final Lock lock = YamlUtils.load(location);
		
		// We can add the player and the lock to the
		// justUsedLock HashMap, we do this to check if the player
		// Has dropped something closing the inventory (we check this
		// In the ItemSpawn class) and remove him 30 ticks later
		WorldUtils.justUsedLock.put(player, lock);
		
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				WorldUtils.justUsedLock.remove(player);
			}
		}, 30);

		// And be sure that the pattern it's the same
		if (!Arrays.equals(lock.getContent(), inventory.getContents()))
			return;

		// All is fine, now can we open the door
		WorldUtils.toggleDoor(location, true);

		// And now close it automagically after 3 seconds
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				WorldUtils.toggleDoor(location, false);
			}
		}, 60);

		// Finally we shuffle the content of the dropper so
		// other people can't know what the password is
		ItemStack[] content = dropper.getInventory().getContents();
		Collections.shuffle(Arrays.asList(content));
		dropper.getInventory().setContents(content);
	}
}
