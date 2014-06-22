package me.IvanMazzoli.DoorLock.Commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import me.IvanMazzoli.DoorLock.Lock;
import me.IvanMazzoli.DoorLock.Util.WorldUtils;
import me.IvanMazzoli.DoorLock.Util.YamlUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Create extends DoorLockCommand {

	public Create() {

		super("Create a lock");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCommand(Player player, String[] args) {

		Block block = player.getTargetBlock(null, 5);

		if (!block.getType().equals(Material.DROPPER)) {
			player.sendMessage(ChatColor.RED
					+ "You must watch a dropper to create a lock!");
			return;
		}

		Dropper dropper = (Dropper) block.getState();
		Inventory inventory = dropper.getInventory();

		boolean isEmpty = true;

		for (ItemStack content : inventory.getContents())
			if (content != null)
				isEmpty = false;

		if (isEmpty) {
			player.sendMessage(ChatColor.RED
					+ "You must set your password before creating a lock!");
			return;
		}

		if (WorldUtils.isLock(dropper.getLocation())) {
			player.sendMessage(ChatColor.RED
					+ "This dropper is already a lock!");
			return;
		}

		Lock lock = new Lock(dropper.getLocation(), dropper.getInventory()
				.getContents(), player.getUniqueId());

		WorldUtils.lockList.add(lock);

		try {
			YamlUtils.save(lock);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ItemStack[] content = dropper.getInventory().getContents();
		Collections.shuffle(Arrays.asList(content));
		dropper.getInventory().setContents(content);

		player.sendMessage(ChatColor.GREEN + "Lock created successfully!");
	}
}
