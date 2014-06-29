package me.IvanMazzoli.DoorLock.Commands;

import java.util.Iterator;

import me.IvanMazzoli.DoorLock.Lock;
import me.IvanMazzoli.DoorLock.Main;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;

public class Delete extends DoorLockCommand {

	public Delete() {
		super("Delete a lock");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(Player player, String[] args) {

		Block block = player.getTargetBlock(null, 5);

		if (!block.getType().equals(Material.DROPPER)) {
			player.sendMessage(ChatColor.RED
					+ "You must watch a dropper to delete a lock!");
			return;
		}

		Dropper dropper = (Dropper) block.getState();
		Location location = block.getLocation();

		if (!WorldUtils.isLock(dropper.getLocation())) {
			player.sendMessage(ChatColor.RED + "This dropper isn't a lock!");
			return;
		}

		Lock lock = YamlUtils.load(location);

		if (!lock.getOwner().equals(player.getUniqueId())
				&& !player.hasPermission("doorlock.admin")) {
			player.sendMessage(ChatColor.RED + "You don't own this lock!");
			return;
		}

		if (player.hasPermission("doorlock.admin")
				&& !WorldUtils.adminMode.contains(player)) {
			player.sendMessage(ChatColor.RED
					+ "You must be in admin mode to delete Locks owned by other players!");
			return;
		}

		Iterator<Lock> iterator = WorldUtils.lockList.iterator();

		while (iterator.hasNext()) {

			Lock inList = iterator.next();

			if (lock.getLocation().equals(inList.getLocation()))
				iterator.remove();
			;
		}

		Main.log.info("" + WorldUtils.lockList.size());

		YamlUtils.delete(lock);

		String owned = player.hasPermission("doorlock.admin") ? " owned by "
				+ Bukkit.getOfflinePlayer(lock.getOwner()).getName() : null;
				
		player.sendMessage(ChatColor.GREEN + "Lock" + owned
				+ " deleted successfully!");
	}

}
