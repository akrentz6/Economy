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
import me.scruffyboy13.Economy.PlayerManager;
import me.scruffyboy13.Economy.commands.CommandExecutor;
import me.scruffyboy13.Economy.utils.StringUtils;

public class MoneySetCommand extends CommandExecutor {

	public MoneySetCommand() {
		this.setName("set");
		this.setPermission("economy.command.set");
		this.setUsage(Economy.getInstance().getConfig().getStringList("messages.money.set.usage"));
		this.setBoth(true);
		this.setLengths(Arrays.asList(3));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {

		OfflinePlayer other = Bukkit.getOfflinePlayer(args[1]);
		
		if (other == null) {
			StringUtils.sendConfigMessage(sender, "messages.money.set.otherDoesntExist", ImmutableMap.of(
					"%player%", args[1]));
			return;
		}
		
		if (!Economy.getEconomyUtils().hasAccount(other)) {
			StringUtils.sendConfigMessage(sender, "messages.money.set.otherNoAccount", ImmutableMap.of(
					"%player%", other.getName()));
			return;
		}
		
		double amount = 0;
		try {
			amount = Economy.getAmountFromString(args[2]);
		}
		catch (NumberFormatException e){
			StringUtils.sendConfigMessage(sender, "messages.money.set.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		if (amount < 0) {
			StringUtils.sendConfigMessage(sender, "messages.money.set.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		
		PlayerManager playerManager = Economy.getPlayerManagerMap().get(other.getUniqueId());
		playerManager.setBalance(amount);
		StringUtils.sendConfigMessage(sender, "messages.money.set.setter", ImmutableMap.of(
				"%balance%", Economy.getEconomyUtils().format(amount),
				"%player%", other.getName()));
		if (other instanceof Player) {
			if (!(sender instanceof Player && ((Player) sender).equals((Player) other))) {
				StringUtils.sendConfigMessage((Player) other, "messages.money.set.set", ImmutableMap.of(
						"%amount%", Economy.getEconomyUtils().format(amount)));
			}
		}
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
	
}
