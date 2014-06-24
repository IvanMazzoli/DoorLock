package me.IvanMazzoli.DoorLock.Events;

import me.IvanMazzoli.DoorLock.Util.WorldUtils;

import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryMoveItem implements Listener {

	@EventHandler
	public void fromOtherToDropper(InventoryMoveItemEvent event) {

		// First we must check if the items are moving into a dropper
		if (!event.getDestination().getType().equals(InventoryType.DROPPER))
			return;

		// Then we get the dropper
		Dropper dropper = (Dropper) event.getDestination().getHolder();

		// And finally we check if it's a lock, if so we cancel the event
		if (WorldUtils.isLock(dropper.getLocation()))
			event.setCancelled(true);
	}

	@EventHandler
	public void fromDropperToOther(InventoryMoveItemEvent event) {

		// First we must check if the items are moving from a dropper
		if (!event.getSource().getType().equals(InventoryType.DROPPER))
			return;

		// Then we get the dropper
		Dropper dropper = (Dropper) event.getSource().getHolder();

		// And finally we check if it's a lock, if so we cancel the event
		if (WorldUtils.isLock(dropper.getLocation()))
			event.setCancelled(true);
	}
}
