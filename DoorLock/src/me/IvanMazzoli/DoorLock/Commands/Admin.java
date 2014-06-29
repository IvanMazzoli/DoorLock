package me.IvanMazzoli.DoorLock.Commands;

import me.IvanMazzoli.DoorLock.Util.WorldUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Admin extends DoorLockCommand {

	public Admin() {
		super("§Toggles admin mode");
	}

	@Override
	public void onCommand(Player player, String[] args) {

		if (!player.hasPermission("doorlock.admin")) {
			player.sendMessage(ChatColor.RED
					+ "You don't have permission to run this command!");
			return;
		}

		if (WorldUtils.adminMode.contains(player)) {
			WorldUtils.adminMode.remove(player);
			player.sendMessage(ChatColor.GREEN + "Admin mode: " + ChatColor.RED
					+ "OFF");
		} else {
			WorldUtils.adminMode.add(player);
			player.sendMessage(ChatColor.GREEN + "Admin mode: "
					+ ChatColor.GREEN + "ON");
		}
	}
}
