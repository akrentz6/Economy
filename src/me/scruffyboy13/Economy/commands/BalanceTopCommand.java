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
			
			if (args.length < 2) {
				
				if (Economy.getSortedPlayerManagerMap().isEmpty()) {
					StringUtils.sendConfigMessage(sender, "messages.top.noAccounts");
					return true;
				}
				
				int top = 0;
				if (args.length == 1) {
					try {
						top = Integer.valueOf(args[0]) - 1;
					} catch(NumberFormatException e) {
						StringUtils.sendConfigMessage(sender, "messages.top.invalidTop", ImmutableMap.of(
								"%top%", args[0]));
						return true;
					}
				}
				
				if (top < 0) {
					StringUtils.sendConfigMessage(sender, "messages.top.invalidTop", ImmutableMap.of(
							"%top%", args[0]));
					return true;
				}
				
				Object[] playerManagers = Economy.getSortedPlayerManagerMap().keySet().toArray();
				
				for (int i = top*10; i < (top+1)*10; i++) {
					if (Economy.getSortedPlayerManagerMap().size() > i) {
						UUID key = (UUID) playerManagers[i];
						PlayerManager playerManager = Economy.getSortedPlayerManagerMap().get(key);
						StringUtils.sendConfigMessage(sender, "messages.top.message", ImmutableMap.of(
								"%rank%", i+1 + "",
								"%player%", Bukkit.getOfflinePlayer(playerManager.getUUID()).getName(),
								"%balance%", Economy.getEconomyUtils().format(playerManager.getTopBalance()) +  ""
								));
					}
					else {
						if (i == top*10) {
							StringUtils.sendConfigMessage(sender, "messages.top.notEnoughPlayers");
							return true;
						}
					}
				}
				
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (Economy.getEconomyUtils().hasAccount(player)) {
						PlayerManager playerManager = Economy.getPlayerManagerMap().get(player.getUniqueId());
						int playerIndex = new ArrayList<PlayerManager>(
								Economy.getSortedPlayerManagerMap().values()).indexOf(playerManager);
						if (playerIndex < top*10 || playerIndex > (top+1)*10) {
							StringUtils.sendConfigMessage(sender, "messages.top.self", ImmutableMap.of(
									"%rank%", playerIndex + "",
									"%player%", Bukkit.getOfflinePlayer(playerManager.getUUID()).getName(),
									"%balance%", Economy.getEconomyUtils().format(playerManager.getTopBalance()) +  ""
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
