package me.scruffyboy13.Economy.commands.money;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.scruffyboy13.Economy.EconomyMain;
import me.scruffyboy13.Economy.commands.CommandExecutor;
import me.scruffyboy13.Economy.data.ConfigHandler;
import me.scruffyboy13.Economy.runnables.BalanceTopRunnable;
import me.scruffyboy13.Economy.utils.StringUtils;

public class MoneyReloadCommand extends CommandExecutor {

	public MoneyReloadCommand() {
		this.setName("reload");
		this.setPermission("economy.command.reload");
		this.setUsage(ConfigHandler.getMessage("money.reload.usage"));
		this.setBoth(true);
		this.setLengths(Arrays.asList(1));
		this.setAliases(Arrays.asList("rl"));
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		EconomyMain.getInstance().reloadConfig();
		EconomyMain.setSuffixes(ConfigHandler.getSuffixes());
		
		EconomyMain.getBalanceTopRunnable().cancel();
		EconomyMain.setBalanceTopRunnable(new BalanceTopRunnable());
		EconomyMain.getBalanceTopRunnable().start(ConfigHandler.getBalanceTopInterval());
		
		StringUtils.sendConfigMessage(sender, "messages.money.reload.reloaded");
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
	
}
