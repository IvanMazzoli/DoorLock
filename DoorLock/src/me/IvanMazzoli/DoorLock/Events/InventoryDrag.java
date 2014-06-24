package me.IvanMazzoli.DoorLock.Events;

import me.IvanMazzoli.DoorLock.Util.WorldUtils;

import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryDrag implements Listener {

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {

		// First we check if the inventory type is a dropper one
		if (!event.getInventory().getType().equals(InventoryType.DROPPER))
			return;

		// Now we get the dropper
		Dropper dropper = (Dropper) event.getInventory().getHolder();

		// Then we check if it's a lock, if so we don't want the player to drag
		// Items in the inventory
		if (WorldUtils.isLock(dropper.getLocation()))
			event.setCancelled(true);
	}
}
