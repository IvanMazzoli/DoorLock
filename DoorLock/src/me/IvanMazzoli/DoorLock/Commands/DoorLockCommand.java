package me.IvanMazzoli.DoorLock.Commands;

import org.bukkit.entity.Player;

public abstract class DoorLockCommand {

	public abstract void onCommand(Player p, String[] args);

	private String description;

	public DoorLockCommand(String description) {
		this.description = description;
	}

	public final String getDescription() {
		return description;
	}
}