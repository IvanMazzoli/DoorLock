package me.IvanMazzoli.DoorLock.Events;

import me.IvanMazzoli.DoorLock.Util.WorldUtils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class BlockDispense implements Listener {

	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event) {
		
		// We check if the block is a lock, if so we cancel the event
		if (WorldUtils.isLock(event.getBlock().getLocation()))
			event.setCancelled(true);
	}
}
