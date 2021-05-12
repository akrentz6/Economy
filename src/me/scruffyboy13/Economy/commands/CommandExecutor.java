package me.scruffyboy13.Economy.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class CommandExecutor {

	private String permission = new String();
	private String name = new String();
	private List<Integer> lengths = new ArrayList<Integer>();
	private List<String> usage = new ArrayList<String>();
	private List<String> aliases = new ArrayList<String>();
	private boolean both = false;
	private boolean console = false;
	private boolean player = false;
	
	public abstract void execute(CommandSender sender, String[] args);
	
	public abstract List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args);
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBoth() {
		return both;
	}
	
	public void setBoth(boolean both) {
		this.both = both;
	}

	public boolean isConsole() {
		return console;
	}
	
	public void setConsole(boolean console) {
		this.console = console;
	}

	public boolean isPlayer() {
		return player;
	}
	
	public void setPlayer(boolean player) {
		this.player = player;
	}

	public List<Integer> getLengths() {
		return lengths;
	}
	
	public void setLengths(List<Integer> lengths) {
		this.lengths = lengths;
	}

	public List<String> getAliases() {
		return aliases;
	}

	public void setAliases(List<String> aliases) {
		this.aliases = aliases;
	}

	public List<String> getUsage() {
		return usage;
	}

	public void setUsage(List<String> list) {
		this.usage = list;
	}
	
}
