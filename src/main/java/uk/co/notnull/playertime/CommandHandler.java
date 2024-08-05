package uk.co.notnull.playertime;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static io.papermc.paper.command.brigadier.Commands.argument;
import static io.papermc.paper.command.brigadier.Commands.literal;

@SuppressWarnings("UnstableApiUsage")
public final class CommandHandler {

	public PlayerTime plugin;

	public CommandHandler(PlayerTime plugin, Commands commands) {
		this.plugin = plugin;

		LiteralCommandNode<CommandSourceStack> command = literal("ptime")
				.requires(source -> source.getSender().hasPermission("ptime.command"))
				.then(literal("day").executes(ctx -> onCommand(ctx.getSource().getSender(), 1000)))
				.then(literal("noon").executes(ctx -> onCommand(ctx.getSource().getSender(), 6000)))
				.then(literal("night").executes(ctx -> onCommand(ctx.getSource().getSender(), 13000)))
				.then(literal("midnight").executes(ctx -> onCommand(ctx.getSource().getSender(), 18000)))
				.then(literal("reset").executes(ctx -> onCommand(ctx.getSource().getSender(), null)))
				.then(argument("time", integer(0, 24000))
							  .executes(ctx -> onCommand(ctx.getSource().getSender(),
														 ctx.getArgument("time", Integer.class))))
				.build();

		commands.register(command, "Sets the time for yourself");
	}

	public int onCommand(CommandSender sender, Integer time) {
		if (sender instanceof Player player) {
			if(time != null) {
				player.sendMessage(Component.text("Time set to " + time).color(NamedTextColor.GREEN));
				player.setPlayerTime(time, false);
			} else {
				player.sendMessage(Component.text("Time reset to " + player.getWorld().getTime())
										   .color(NamedTextColor.GREEN));
				player.resetPlayerTime();
			}

		} else {
			sender.sendMessage(Component.text("You have to be a player to do that.").color(NamedTextColor.RED));
		}

		return Command.SINGLE_SUCCESS;
	}
}