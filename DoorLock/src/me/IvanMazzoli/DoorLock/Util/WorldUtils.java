package me.IvanMazzoli.DoorLock.Util;

import java.util.ArrayList;
import java.util.HashMap;

import me.IvanMazzoli.DoorLock.Lock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class WorldUtils {

	public static ArrayList<Player> adminMode = new ArrayList<Player>();
	public static ArrayList<Lock> lockList = new ArrayList<Lock>();
	public static HashMap<Player, Lock> justUsedLock = new HashMap<Player, Lock>();
	
	public static Enchantment glow = new GlowEnchant(27);
	
	public static void setupRecipes() {
		
		ShapelessRecipe lockpick = new ShapelessRecipe(getLockpick());
		lockpick.addIngredient(1, Material.IRON_INGOT);
		lockpick.addIngredient(1, Material.STICK);
		Bukkit.addRecipe(lockpick);
	}

	private static ItemStack getLockpick() {

		ItemStack lockpick = new ItemStack(Material.STICK, 1);
		lockpick.addEnchantment(glow, 1);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RESET + "Click a lock to picklock it!");
		
		ItemMeta lpMeta = lockpick.getItemMeta();
		lpMeta.setDisplayName(ChatColor.RESET + "Lockpick");
		lpMeta.setLore(lore);
		
		lockpick.setItemMeta(lpMeta);
		
		return lockpick;
	}

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
