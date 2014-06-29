package me.IvanMazzoli.DoorLock.Util;

import java.util.ArrayList;
import java.util.HashMap;

import me.IvanMazzoli.DoorLock.Lock;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class WorldUtils {

	public static ArrayList<Player> adminMode = new ArrayList<Player>();
	public static ArrayList<Lock> lockList = new ArrayList<Lock>();
	public static HashMap<Player, Lock> justUsedLock = new HashMap<Player, Lock>();

	public static boolean isLock(Location location) {
		for (Lock lock : lockList) {
			if (lock.getLocation().equals(location))
				return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void toggleDoor(Location location, boolean open) {

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				Block block = location.getBlock().getRelative(x, -1, z);

				int state = block.getData();

				if (block.getType().equals(Material.IRON_DOOR_BLOCK)
						&& block.getData() != 12) {
					if (open && block.getData() < 4) {

						state += 4;
					} else if (!open && block.getData() > 4) {
						state -= 4;
					}

					block.setData((byte) state);

					location.getWorld().playEffect(location,
							Effect.DOOR_TOGGLE, 1);
				}
			}
		}
	}
}
