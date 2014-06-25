package me.IvanMazzoli.DoorLock.Util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import me.IvanMazzoli.DoorLock.Lock;
import me.IvanMazzoli.DoorLock.Main;
import net.minecraft.util.org.apache.commons.io.FileUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlUtils {

	public static void save(Lock lock) throws IOException {

		FileConfiguration lockInfo = getYamlConfiguration(lock.getLocation());

		lockInfo.set("owner", lock.getOwner().toString());
		lockInfo.set("password",
				InventoryUtils.InventoryToString(lock.getInventory()));

		lockInfo.save(getYamlFile(lock.getLocation()));
	}

	public static Lock load(Location location) {

		FileConfiguration lockInfo = getYamlConfiguration(location);

		return new Lock(location, InventoryUtils.StringToInventory(
				lockInfo.getString("password")).getContents(),
				UUID.fromString(lockInfo.getString("owner")));
	}

	public static void delete(Lock lock) {
		getYamlFile(lock.getLocation()).delete();
	}

	public static File getYamlFile(Location location) {
		return new File(Main.getPlugin().getDataFolder().getPath()
				+ File.separator + "locks" + File.separatorChar
				+ location.getWorld().getName() + File.separator
				+ condenseLocation(location) + ".yml");
	}

	public static FileConfiguration getYamlConfiguration(Location location) {
		return YamlConfiguration.loadConfiguration(getYamlFile(location));
	}

	public static void importLocks() {

		Collection<File> yamls = FileUtils.listFiles(new File(Main.getPlugin()
				.getDataFolder().getPath()
				+ File.separator + "locks"), null, true);

		for (File file : yamls) {

			String coords = file.getName().replaceAll(".yml", "");
			String world = file.getParentFile().getName();

			WorldUtils.lockList.add(load(expandLocation(world, coords)));
		}
		
		Main.log.info("Loaded " + WorldUtils.lockList.size() + " lock(s)");
	}

	private static String condenseLocation(Location location) {
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		return x + "_" + y + "_" + z;
	}

	private static Location expandLocation(String world, String coords) {
		String[] param = coords.split("_");
		return new Location(Bukkit.getWorld(world), Double.valueOf(param[0]),
				Double.valueOf(param[1]), Double.valueOf(param[2]));
	}
}
