package me.IvanMazzoli.DoorLock.Events;

import me.IvanMazzoli.DoorLock.Util.WorldUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		// First we must check if the player has right clicked a dropper
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;

		if (!event.getClickedBlock().getType().equals(Material.DROPPER))
			return;

		// Now we get the dropper
		Dropper dropper = (Dropper) event.getClickedBlock().getState();

		// Now we check if it's a Lock
		if (!WorldUtils.isLock(dropper.getLocation()))
			return;

		// Now we get the list of who is watching into the lock
		// If the list isn't empty, we cancel the event
		if (!dropper.getInventory().getViewers().isEmpty()) {
			player.sendMessage(ChatColor.RED
					+ "You can't use a Lock if someone else is already using it!");
			event.setCancelled(true);
		}
	}
}
