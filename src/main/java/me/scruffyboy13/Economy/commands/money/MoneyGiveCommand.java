package me.scruffyboy13.Economy.commands.money;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.Economy.EconomyMain;
import me.scruffyboy13.Economy.commands.CommandExecutor;
import me.scruffyboy13.Economy.data.ConfigHandler;
import me.scruffyboy13.Economy.utils.StringUtils;

public class MoneyGiveCommand extends CommandExecutor {

	public MoneyGiveCommand() {
		this.setName("give");
		this.setPermission("economy.command.give");
		this.setUsage(ConfigHandler.getMessage("money.give.usage"));
		this.setBoth(true);
		this.setLengths(Arrays.asList(3));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		OfflinePlayer other = Bukkit.getOfflinePlayer(args[1]);
		
		if (other == null) {
			StringUtils.sendConfigMessage(sender, "messages.money.give.otherDoesntExist", ImmutableMap.of(
					"%player%", args[1]));
			return;
		}
		
		if (!EconomyMain.getEco().hasAccount(other.getUniqueId())) {
			StringUtils.sendConfigMessage(sender, "messages.money.give.otherNoAccount", ImmutableMap.of(
					"%player%", other.getName()));
			return;
		}
		
		double amount = 0;
		try {
			amount = EconomyMain.getAmountFromString(args[2]);
		}
		catch (NumberFormatException e){
			StringUtils.sendConfigMessage(sender, "messages.money.give.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		if (amount < 0) {
			StringUtils.sendConfigMessage(sender, "messages.money.give.invalidAmount", ImmutableMap.of(
					"%amount%", args[2]));
			return;
		}
		
		EconomyMain.getEco().deposit(other.getUniqueId(), amount);
		StringUtils.sendConfigMessage(sender, "messages.money.give.sent", ImmutableMap.of(
				"%amount%", EconomyMain.format(amount),
				"%player%", other.getName()));
		if (other instanceof Player) {
			if (!(sender instanceof Player && ((Player) sender).equals((Player) other))) {
				StringUtils.sendConfigMessage((Player) other, "messages.money.give.received", ImmutableMap.of(
						"%amount%", EconomyMain.format(amount)));
			}
		}
		
		return;
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
	
}
