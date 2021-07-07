package uk.co.notnull.playertime;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Map;

public class Commands implements CommandExecutor {

	public PlayerTime plugin;
	private final Map<String, Integer> times;

	public Commands(PlayerTime plugin) {
		this.plugin = plugin;
		times = Map.of(
			"day", 1000,
			"noon", 6000,
			"night", 13000,
			"midnight", 18000
		);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (label.equalsIgnoreCase("ptime")) {
				if (args.length == 0) {
					player.sendMessage(ChatColor.GREEN + "Current time is " + (player.getPlayerTime() % 24000));
					return true;
				}

				String timeArg = args[0].toLowerCase();

				if (times.containsKey(args[0].toLowerCase())) {
					player.setPlayerTime(times.get(timeArg), false);
					player.sendMessage(ChatColor.GREEN + "Time set to " + timeArg + " (" + times.get(timeArg) + ")");
				} else {
					try {
						int time = Math.min(24000, Math.max(0, Integer.parseInt(args[0])));
						player.setPlayerTime(time, false);
						player.sendMessage(ChatColor.GREEN + "Time set to " + time);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Invalid time. Valid times: day,noon,night,midnight,0-24000");
					}
				}
			}

		} else {
			sender.sendMessage(ChatColor.RED + "You have to be a player to do that.");
		}

		return true;
	}
}