package me.IvanMazzoli.DoorLock.Commands;

import me.IvanMazzoli.DoorLock.Lock;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;

public class GetOwner extends DoorLockCommand {

	public GetOwner() {
		super("Gets the owner of a lock");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(Player player, String[] args) {

		Block block = player.getTargetBlock(null, 5);

		if (!block.getType().equals(Material.DROPPER)) {
			player.sendMessage(ChatColor.RED
					+ "You must watch a dropper to get the owner of a lock!");
			return;
		}

		Dropper dropper = (Dropper) block.getState();
		Location location = block.getLocation();

		if (!WorldUtils.isLock(dropper.getLocation())) {
			player.sendMessage(ChatColor.RED + "This dropper isn't a lock!");
			return;
		}

		Lock lock = YamlUtils.load(location);

		String[] info = { "- Lock info:",
				"Owner: " + Bukkit.getOfflinePlayer(lock.getOwner()).getName(),
				"Owner UUID: " + lock.getOwner() };
		
		for (String string : info)
			player.sendMessage(ChatColor.GREEN + string);
	}

}
