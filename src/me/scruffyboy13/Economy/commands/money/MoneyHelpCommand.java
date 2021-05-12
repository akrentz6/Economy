package me.scruffyboy13.Economy.commands.money;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.scruffyboy13.Economy.commands.CommandExecutor;
import me.scruffyboy13.Economy.data.ConfigHandler;
import me.scruffyboy13.Economy.utils.StringUtils;

public class MoneyHelpCommand extends CommandExecutor {

	public MoneyHelpCommand() {
		this.setName("help");
		this.setPermission("economy.command.help");
		this.setUsage(ConfigHandler.getMessage("money.help.usage"));
		this.setPlayer(true);
		this.setConsole(false);
		this.setLengths(Arrays.asList(1));
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		StringUtils.sendConfigMessage(sender, "messages.money.help.message");
		
		return;
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return null;
	}
	
}
