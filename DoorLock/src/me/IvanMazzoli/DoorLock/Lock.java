package me.IvanMazzoli.DoorLock;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Lock {

	Location location;
	ItemStack[] content;
	UUID owner;

	public Lock(Location location, ItemStack[] content, UUID owner) {
		super();
		this.location = location;
		this.content = content;
		this.owner = owner;
	}

	public ItemStack[] getContent() {
		return this.content;
	}

	public ItemStack getSlot(int slot) {
		return this.content[slot];
	}

	public Location getLocation() {
		return this.location;
	}

	public Inventory getInventory() {
		Inventory inventory = Bukkit.createInventory(null,
				InventoryType.DROPPER);
		inventory.setContents(this.content);
		return inventory;
	}
	
	public UUID getOwner() {
		return this.owner;
	}
}
