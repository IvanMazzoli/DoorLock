package me.IvanMazzoli.DoorLock.Events;

import me.IvanMazzoli.DoorLock.Util.WorldUtils;

import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		// First we check if the inventory type is a dropper one
		if (!event.getInventory().getType().equals(InventoryType.DROPPER))
			return;

		// Then we get the dropper
		Dropper dropper = (Dropper) event.getInventory().getHolder();

		// We check if the dropper is a lock
		if (!WorldUtils.isLock(dropper.getLocation()))
			return;

		// Finally we have some actions that are forbidden because they
		// Can remove/add items to the lock
		if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))
			event.setCancelled(true);

		if (event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD))
			event.setCancelled(true);

		if (event.getAction().equals(InventoryAction.HOTBAR_SWAP))
			event.setCancelled(true);

		if (event.getAction().equals(InventoryAction.DROP_ALL_CURSOR))
			event.setCancelled(true);

		if (event.getAction().equals(InventoryAction.DROP_ONE_CURSOR))
			event.setCancelled(true);

		if (event.getAction().equals(InventoryAction.COLLECT_TO_CURSOR))
			event.setCancelled(true);
	}
}
