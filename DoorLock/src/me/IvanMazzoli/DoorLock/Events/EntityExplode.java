package me.IvanMazzoli.DoorLock.Events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.IvanMazzoli.DoorLock.Util.WorldUtils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {

		// First we get the list of the involved blocks
		List<Block> exploded = event.blockList();
		Iterator<Block> inExplosion = exploded.iterator();

		// Then we create a list of blocks to not being destroyed
		List<Block> noDestroy = new ArrayList<Block>();

		// For every block involved in the explosion we check if
		// It is a dropper and if it is a lock
		// If so, we add to the noDestroy list
		while (inExplosion.hasNext()) {

			Block block = inExplosion.next();

			if (block.getType().equals(Material.DROPPER))
				if (WorldUtils.isLock(block.getLocation()))
					noDestroy.add(block);
		}

		// Finally we can remove the blocks
		for (Block block : noDestroy)
			exploded.remove(block);
	}
}
