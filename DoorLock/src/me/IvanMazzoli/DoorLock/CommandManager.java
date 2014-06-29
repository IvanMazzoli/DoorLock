package me.IvanMazzoli.DoorLock;

import java.util.ArrayList;
import java.util.logging.Logger;

import me.IvanMazzoli.DoorLock.Commands.Admin;
import me.IvanMazzoli.DoorLock.Commands.ChangePassword;
import me.IvanMazzoli.DoorLock.Commands.Create;
import me.IvanMazzoli.DoorLock.Commands.Delete;
import me.IvanMazzoli.DoorLock.Commands.DoorLockCommand;
import me.IvanMazzoli.DoorLock.Commands.GetOwner;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

	private ArrayList<DoorLockCommand> commands = new ArrayList<DoorLockCommand>();
	private Logger log;

	public void setup() {
		commands.add(new Admin());
		commands.add(new ChangePassword());
		commands.add(new Create());
		commands.add(new Delete());
		commands.add(new GetOwner());
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {

			log.info("Sorry, this plugin can be used only by players!");
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("doorlock")) {

			if (args.length == 0) {

				ArrayList<DoorLockCommand> adminCommands = new ArrayList<DoorLockCommand>();

				player.sendMessage(ChatColor.GREEN
						+ "===== DoorLock command list:");

				for (DoorLockCommand doorLockCommand : commands) {

					if (doorLockCommand.getDescription().contains("§")) {
						adminCommands.add(doorLockCommand);
					} else {
						player.sendMessage(ChatColor.GOLD
								+ "/doorlock "
								+ doorLockCommand.getClass().getSimpleName()
										.toLowerCase() + " - "
								+ doorLockCommand.getDescription());
					}
				}

				if (adminCommands.size() != 0) {

					player.sendMessage(ChatColor.RED
							+ "===== DoorLock Admin commands:");

					for (DoorLockCommand doorLockCommand : adminCommands) {

						player.sendMessage(ChatColor.GOLD
								+ "/doorlock "
								+ doorLockCommand.getClass().getSimpleName()
										.toLowerCase()
								+ " - "
								+ doorLockCommand.getDescription().replaceAll(
										"§", ""));
					}

				}

				return true;
			}

			DoorLockCommand doorLockCommand = getCommand(args[0]);

			if (doorLockCommand == null) {
				player.sendMessage(ChatColor.RED
						+ "This command doesn't exist!");
				return true;
			}

			doorLockCommand.onCommand(player, args);

			return true;
		}
		return true;
	}

	private DoorLockCommand getCommand(String name) {

		for (DoorLockCommand doorLockCommand : commands)
			if (doorLockCommand.getClass().getSimpleName()
					.equalsIgnoreCase(name))
				return doorLockCommand;

		return null;
	}
}