package me.IvanMazzoli.DoorLock.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import me.IvanMazzoli.DoorLock.Lock;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.Location;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class ItemSpawn implements Listener {

	@EventHandler
	public void onItemSpawn(ItemSpawnEvent event) {

		// First we get the location of the spawned item
		Location location = event.getLocation();

		// Then we get all the players in a radius of 1.5 blocks
		// Of the spawned item
		ArrayList<Player> near = new ArrayList<Player>();

		for (Entity entity : location.getWorld().getEntities())
			if (entity instanceof Player)
				if (entity.getLocation().distance(location) <= 1.5)
					near.add((Player) entity);

		// Then we check if someone of them has used a lock recently
		// If not, we don't need to go further
		ArrayList<Player> hasUsedLock = new ArrayList<Player>();

		for (Player player : near)
			if (WorldUtils.justUsedLock.containsKey(player))
				hasUsedLock.add(player);

		for (Player player : hasUsedLock) {
			
			// First we get the amount of items contained in the dropper
			Lock worldLock = WorldUtils.justUsedLock.get(player);
			Dropper dropper = (Dropper) worldLock.getLocation().getBlock().getState();
			
			int worldItems = 0;

			for (ItemStack itemStack : dropper.getInventory().getContents())
				if (itemStack != null)
					worldItems += itemStack.getAmount();
			
			// Now we get the amount of items of the password stored 
			// On the config file 
			Lock configLock = YamlUtils.load(worldLock.getLocation());

			int configItems = 0;

			for (ItemStack itemStack : configLock.getInventory().getContents())
				if (itemStack != null)
					configItems += itemStack.getAmount();
			
			// If the amount of items on the dispenser is different from
			// The amount of the password stored in the config we destroy the
			// Spawned item, load the original password, put in the dispenser
			// And shuffle the content			
			if (worldItems != configItems) {
				
				event.setCancelled(true);
				
				ItemStack[] content = worldLock.getInventory().getContents();
				Collections.shuffle(Arrays.asList(content));
				dropper.getInventory().setContents(content);
			}
		}
	}
}
