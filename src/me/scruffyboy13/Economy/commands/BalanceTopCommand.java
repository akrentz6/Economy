package me.scruffyboy13.Economy.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.Economy.Economy;
import me.scruffyboy13.Economy.PlayerManager;
import me.scruffyboy13.Economy.utils.StringUtils;

public class BalanceTopCommand implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("economy.command.balancetop")) {
			
			if (args.length == 0) {
				
				if (Economy.getSortedPlayerManagerMap().isEmpty()) {
					StringUtils.sendConfigMessage(sender, "messages.top.noAccounts");
					return true;
				}
				
				for (int i = 0; i < 10; i++) {
					if (Economy.getSortedPlayerManagerMap().size() > i) {
						UUID key = (UUID) Economy.getSortedPlayerManagerMap().keySet().toArray()[i];
						PlayerManager playerManager = Economy.getSortedPlayerManagerMap().get(key);
						String section = "other";
						if (i == 0) section = "first";
						else if (i == 1) section = "second";
						else if (i == 2) section = "third";
						StringUtils.sendConfigMessage(sender, "messages.top." + section, ImmutableMap.of(
								"%player%", Bukkit.getOfflinePlayer(playerManager.getUUID()).getName(),
								"%balance%", Economy.getEconomyCore().format(playerManager.getBalance()) +  ""
								));
					}
					else {
						return true;
					}
				}
				
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (Economy.getEconomyCore().hasAccount(player)) {
						PlayerManager playerManager = Economy.getPlayerManagerMap().get(player.getUniqueId());
						if (!(new ArrayList<PlayerManager>(
								Economy.getSortedPlayerManagerMap().values()).indexOf(playerManager) < 10)) {
							StringUtils.sendConfigMessage(sender, "messages.top.self", ImmutableMap.of(
									"%player%", Bukkit.getOfflinePlayer(playerManager.getUUID()).getName(),
									"%balance%", Economy.getEconomyCore().format(playerManager.getBalance()) +  ""
									));
						}
					}
				}
				
				return true;
				
			}
			else {
				
				StringUtils.sendConfigMessage(sender, "messages.top.usage");
				
				return true;
				
			}
			
		}
		else {

			StringUtils.sendConfigMessage(sender, "messages.nopermission");			
			
			return true;
			
		}
		
	}
	
}
