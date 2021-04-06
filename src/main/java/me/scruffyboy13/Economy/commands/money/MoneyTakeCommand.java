package me.scruffyboy13.Economy.commands.money;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.Economy.Economy;
import me.scruffyboy13.Economy.commands.CommandExecutor;
import me.scruffyboy13.Economy.utils.StringUtils;

public class MoneyTakeCommand extends CommandExecutor {

	public MoneyTakeCommand() {
		this.setName("take");
		this.setPermission("economy.command.take");
		this.setUsage(Economy.getInstance().getConfig().getStringList("messages.money.take.usage"));
		this.setBoth(true);
		this.setLengths(Arrays.asList(3));
		this.setAliases(Arrays.asList("remove"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {

		OfflinePlayer other = Bukkit.getOfflinePlayer(args[1]);
		
		if (other == null) {
			StringUtils.sendConfigMessage(sender, "messages.money.take.otherDoesntExist", ImmutableMap.of(
					"%player%", args[1]));
			return;
		}
		
		if (!Economy.getEconomyUtils().hasAccount(other)) {
			StringUtils.sendConfigMessage(sender, "messages.money.take.otherNoAccount", ImmutableMap.of(
					"%player%", other.getName()));
			return;
		}
		
		double amount = 0;
		try {
			amount = Economy.getAmountFromString(args[2]);
		}
		catch (NumberFormatException e){
			StringUtils.sendConfigMessage(sender, "messages.money.take.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		if (amount < 0) {
			StringUtils.sendConfigMessage(sender, "messages.money.take.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		
		if (!Economy.getEconomyUtils().has(other, amount)) {
			StringUtils.sendConfigMessage(sender, "messages.money.take.insufficientFunds", ImmutableMap.of(
					"%player%", other.getName()));
			return;
		}
		
		Economy.getEconomyUtils().withdrawPlayer(other, amount);
		StringUtils.sendConfigMessage(sender, "messages.money.take.take", ImmutableMap.of(
				"%amount%", Economy.getEconomyUtils().format(amount),
				"%player%", other.getName()));
		if (other instanceof Player) {
			if (!(sender instanceof Player && ((Player) sender).equals((Player) other))) {
				StringUtils.sendConfigMessage((Player) other, "messages.money.take.taken", ImmutableMap.of(
						"%amount%", Economy.getEconomyUtils().format(amount)));
			}
		}
		
		return;
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
	
}
