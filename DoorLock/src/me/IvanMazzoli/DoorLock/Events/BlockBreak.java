package me.IvanMazzoli.DoorLock.Events;

import java.util.ArrayList;

import me.IvanMazzoli.DoorLock.Lock;
import me.IvanMazzoli.DoorLock.Main;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

	ArrayList<Player> todestroy = new ArrayList<Player>();

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		final Player player = event.getPlayer();
		Block block = event.getBlock();

		if (!block.getType().equals(Material.DROPPER))
			return;

		if (!WorldUtils.isLock(block.getLocation()))
			return;

		Lock lock = YamlUtils.load(block.getLocation());
		
		if (!lock.getOwner().equals(player.getUniqueId())) {
			event.setCancelled(true);
			return;
		}

		if (todestroy.contains(player)) {
			Bukkit.dispatchCommand(player, "doorlock delete");
		} else {
			event.setCancelled(true);
			player.sendMessage(ChatColor.GOLD
					+ "This dropper is a lock created by you. Break it again to destroy it.");
			todestroy.add(player);
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
				@Override
				public void run() {
					todestroy.remove(player);
				}
			}, 60);
		}
	}
}
