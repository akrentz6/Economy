package me.scruffyboy13.Economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.Economy.Economy;
import me.scruffyboy13.Economy.utils.StringUtils;

public class BalanceCommand implements org.bukkit.command.CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender.hasPermission("economy.command.balance")) {
		
			if (args.length == 0) {
				
				if (!(sender instanceof Player)) {
					StringUtils.sendConfigMessage(sender, "messages.playersOnly");
					return true;
				}
				
				Player player = (Player) sender;
				
				if (!Economy.getEconomyCore().hasAccount(player)) {
					StringUtils.sendConfigMessage(player, "messages.balance.noAccount");
					return true;
				}
				
				Double balance = Economy.getEconomyCore().getBalance(player);
				StringUtils.sendConfigMessage(player, "messages.balance.balance", ImmutableMap.of(
						"%balance%", Economy.getEconomyCore().format(balance) + ""));
				
				return true;
				
			}
			else if (args.length == 1){
				
				OfflinePlayer other = Bukkit.getOfflinePlayer(args[0]);
				
				if (!Economy.getEconomyCore().hasAccount(other)) {
					StringUtils.sendConfigMessage(sender, "messages.money.otherNoAccount", ImmutableMap.of(
							"%player%", other.getName()));
					return true;
				}
	
				Double balance = Economy.getEconomyCore().getBalance(other);
				StringUtils.sendConfigMessage(sender, "messages.money.balance.otherBalance", ImmutableMap.of(
						"%player%", other.getName(),
						"%balance%", Economy.getEconomyCore().format(balance) + ""));
				
				return true;
				
			}
			else {
				
				StringUtils.sendConfigMessage(sender, "messages.balance.usage");
				
				return true;
				
			}
			
		}
		else {
			
			StringUtils.sendConfigMessage(sender, "messages.nopermission");
			
			return true;
			
		}
	
	}
	
}
