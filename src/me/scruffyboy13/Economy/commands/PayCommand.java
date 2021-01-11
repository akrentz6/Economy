package me.scruffyboy13.Economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.Economy.Economy;
import me.scruffyboy13.Economy.utils.StringUtils;

public class PayCommand implements org.bukkit.command.CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lebel, String[] args) {

		if (sender.hasPermission("economy.command.pay")) {
			
			if (args.length == 2) {
			
			if (!(sender instanceof Player)) {
				StringUtils.sendConfigMessage(sender, "messages.playersOnly");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (!Economy.getEconomyUtils().hasAccount(player)) {
				StringUtils.sendConfigMessage(player, "messages.pay.noAccount");
				return true;
			}
			
			OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
			
			if (other == null) {
				StringUtils.sendConfigMessage(player, "messages.pay.otherDoesntExist", ImmutableMap.of(
						"%player%", args[1]));
				return true;
			}
			
			if (!Economy.getEconomyUtils().hasAccount(other)) {
				StringUtils.sendConfigMessage(player, "messages.pay.otherNoAccount", ImmutableMap.of(
						"%player%", other.getName()));
				return true;
			}
			
			if (other.getUniqueId().equals(player.getUniqueId())) {
				StringUtils.sendConfigMessage(player, "messages.pay.cannotPaySelf");
				return true;
			}
			
			double amount = 0;
			try {
				amount = Economy.getAmountFromString(args[1]);
			}
			catch (NumberFormatException e){
				StringUtils.sendConfigMessage(player, "messages.pay.invalidAmount", ImmutableMap.of(
						"%amount%", args[1]));
				return true;
			}
			if (amount <= 0) {
				StringUtils.sendConfigMessage(sender, "messages.money.give.invalidAmount", ImmutableMap.of(
						"%amount%", args[2]));
				return true;
			}
			
			if (!Economy.getEconomyUtils().has(player, amount)) {
				StringUtils.sendConfigMessage(player, "messages.pay.insufficientFunds");
				return true;
			}
			
			Economy.getEconomyUtils().withdrawPlayer(player, amount);
			StringUtils.sendConfigMessage(player, "messages.pay.paid", ImmutableMap.of(
					"%player%", other.getName(), 
					"%amount%", Economy.getEconomyUtils().format(amount)));
			
			Economy.getEconomyUtils().depositPlayer(other, amount);
			if (other instanceof Player) {
				StringUtils.sendConfigMessage((Player) other, "messages.pay.received", ImmutableMap.of(
						"%player%", player.getName(), 
						"%amount%", Economy.getEconomyUtils().format(amount)));
			}
			
			return true;
			
			}
			else {
				
				StringUtils.sendConfigMessage(sender, "messages.pay.usage");
				
				return true;
				
			}
			
		}
		else {

			StringUtils.sendConfigMessage(sender, "messages.nopermission");
			
			return true;
			
		}

	}
	
}
