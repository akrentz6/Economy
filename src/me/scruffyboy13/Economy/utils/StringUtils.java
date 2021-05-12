package me.scruffyboy13.Economy.utils;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableMap;

import me.scruffyboy13.Economy.EconomyMain;

public class StringUtils {

	private static String getPrefix() {
		return color(EconomyMain.getInstance().getConfig().getString("messages.prefix"));
	}

	public static String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void sendMessage(CommandSender sender, List<String> message) {
		for (String line : message) {
			line = line.replace("%prefix%", getPrefix());
			sender.sendMessage(color(line));
		}
	}

	public static void sendMessage(Player player, List<String> message) {
		for (String line : message) {
			line = line.replace("%prefix%", getPrefix());
			player.sendMessage(color(line));
		}
	}
	
	public static void sendMessage(Player player, List<String> message, ImmutableMap<String, String> placeholders) {
		for (String line : message) {
			line = line.replace("%prefix%", getPrefix());
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			player.sendMessage(color(line));
		}
	}
	
	public static void sendMessage(CommandSender sender, List<String> message, ImmutableMap<String, String> placeholders) {
		for (String line : message) {
			line = line.replace("%prefix%", getPrefix());
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			sender.sendMessage(color(line));
		}
	}
	
	public static void sendConfigMessage(CommandSender sender, String path) {
		for (String line : EconomyMain.getInstance().getConfig().getStringList(path)) {
			line = line.replace("%prefix%", getPrefix());
			sender.sendMessage(color(line));
		}
	}
	
	public static void sendConfigMessage(Player player, String path) {
		for (String line : EconomyMain.getInstance().getConfig().getStringList(path)) {
			line = line.replace("%prefix%", getPrefix());
			player.sendMessage(color(line));
		}
	}

	public static void sendConfigMessage(Player player, String path, ImmutableMap<String, String> placeholders) {
		for (String line : EconomyMain.getInstance().getConfig().getStringList(path)) {
			line = line.replace("%prefix%", getPrefix());
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			player.sendMessage(color(line));
		}
	}

	public static void sendConfigMessage(CommandSender sender, String path, ImmutableMap<String, String> placeholders) {
		for (String line : EconomyMain.getInstance().getConfig().getStringList(path)) {
			line = line.replace("%prefix%", getPrefix());
			for (Map.Entry<String, String> placeholder : placeholders.entrySet()) {
				line = line.replace(placeholder.getKey(), placeholder.getValue());
			}
			sender.sendMessage(color(line));
		}
	}

}
