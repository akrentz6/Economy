package me.scruffyboy13.Economy.commands.money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.scruffyboy13.Economy.commands.CommandExecutor;
import me.scruffyboy13.Economy.utils.StringUtils;

public class MoneyCommandHandler implements org.bukkit.command.CommandExecutor, TabCompleter {

	private Map<String, CommandExecutor> commands = new HashMap<>();
	
	public MoneyCommandHandler() {
		commands.put("give", new MoneyGiveCommand());
		commands.put("help", new MoneyHelpCommand());
		commands.put("reload", new MoneyReloadCommand());
		commands.put("set", new MoneySetCommand());
		commands.put("take", new MoneyTakeCommand());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		CommandExecutor command;
		
		if (args.length == 0) {
			
			command = commands.get("help");
			
			command.execute(sender, args);
			
		}
		
		else {
			
			String subcmd = args[0].toLowerCase();
			if (commands.containsKey(subcmd) || aliasesContains(subcmd)) {
				
				command = getCommand(subcmd);
				
				if (!sender.hasPermission(command.getPermission())) {
					StringUtils.sendConfigMessage(sender, "messages.nopermission");
					return true;
				}
				
				if (!command.isBoth()) {
					
					if (command.isConsole() && sender instanceof Player) {
						StringUtils.sendConfigMessage(sender, "messages.consoleOnly");
						return true;
					}
					
					if (command.isPlayer() && sender instanceof ConsoleCommandSender) {
						StringUtils.sendConfigMessage(sender, "messages.playerOnly");
						return true;
					}
					
				}
				
				if (!command.getLengths().isEmpty() && !command.getLengths().contains(args.length)) {
					StringUtils.sendMessage(sender, command.getUsage());
					return true;
				}
				
				command.execute(sender, args);
				
			}
			
			else {
				StringUtils.sendConfigMessage(sender, "messages.money.invalidSubCommand");
				return true;
			}
			
		}
		
		return false;
		
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 1) {
			
			List<String> results = new ArrayList<String>();
			
			for (String name : commands.keySet()) {
				
				CommandExecutor command = commands.get(name);
				
				if (name.toLowerCase().startsWith(args[0].toLowerCase())) {
					results.add(name.toLowerCase());
				}
				
				for (String alias : command.getAliases()) {
					if (alias.toLowerCase().startsWith(args[0].toLowerCase())) {
						results.add(alias.toLowerCase());
					}
				}
				
			}
			
			return results;
			
		}
		else if (args.length > 1) {
			
			CommandExecutor command = getCommand(args[0]);
			if (command != null) {
				return command.onTabComplete(sender, cmd, label, args);
			}
			
		}
		
		return null;
	
	}
	
	private boolean aliasesContains(String subcmd) {
		for (CommandExecutor cmd : commands.values()) {
			if (cmd.getAliases().contains(subcmd)) {
				return true;
			}
		}
		return false;
	}
	
	public CommandExecutor getCommand(String subcmd) {
		for (String name : commands.keySet()) {
			if (name.toLowerCase().equals(subcmd.toLowerCase())) {
				return commands.get(name);
			}
			for (String alias : commands.get(name).getAliases()) {
				if (alias.toLowerCase().equals(subcmd.toLowerCase())) {
					return commands.get(name);
				}
			}
		}
		return null;
	}

	public Map<String, CommandExecutor> getCommands() {
		return commands;
	}
	
	public void addCommand(String name, CommandExecutor cmd) {
		commands.put(name, cmd);
	}
	
}
