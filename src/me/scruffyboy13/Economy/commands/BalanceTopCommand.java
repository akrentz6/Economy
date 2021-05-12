package me.scruffyboy13.Economy.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.Economy.EconomyMain;
import me.scruffyboy13.Economy.eco.PlayerBalance;
import me.scruffyboy13.Economy.utils.StringUtils;

public class BalanceTopCommand implements org.bukkit.command.CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("economy.command.balancetop")) {
			
			if (args.length < 2) {
				
				if (EconomyMain.getBalanceTopRunnable().getBalanceTop().isEmpty()) {
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
				
				List<PlayerBalance> playerBalances = EconomyMain.getBalanceTopRunnable().getBalanceTop();
				
				int i = top*10;
				int j = 0;
				while (i < (top+1)*10) {
					if (playerBalances.size() > i) {
						PlayerBalance playerBalance = playerBalances.get(i);
						OfflinePlayer player = Bukkit.getOfflinePlayer(playerBalance.getUUID());
						if (player != null && player.getName() != null) {
							StringUtils.sendConfigMessage(sender, "messages.top.message", ImmutableMap.of(
									"%rank%", i+1-j + "",
									"%player%", player.getName(),
									"%balance%", EconomyMain.format(playerBalance.getBalance()) +  ""
									));
						}
						else {
							j++;
						}
					}
					else {
						if (i == top*10) {
							StringUtils.sendConfigMessage(sender, "messages.top.notEnoughPlayers");
							return true;
						}
					}
					i++;
				}
				
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (EconomyMain.getEco().hasAccount(player.getUniqueId())) {
						PlayerBalance playerBalance = null;
						int playerIndex = -1;
						for (PlayerBalance pb : playerBalances) {
							if (pb.getUUID().equals(player.getUniqueId())) {
								playerBalance = pb;
								playerIndex = playerBalances.indexOf(pb);
							}
						}
						if (playerBalance != null) {
							if (playerIndex < top*10 || playerIndex > (top+1)*10) {
								StringUtils.sendConfigMessage(sender, "messages.top.self", ImmutableMap.of(
										"%rank%", playerIndex + "",
										"%player%", player.getName(),
										"%balance%", EconomyMain.format(playerBalance.getBalance()) +  ""
										));
							}
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
