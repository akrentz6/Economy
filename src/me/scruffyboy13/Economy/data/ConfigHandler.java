package me.scruffyboy13.Economy.data;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import me.scruffyboy13.Economy.EconomyMain;

public class ConfigHandler {
	
	private static FileConfiguration getConfig() {
		return EconomyMain.getInstance().getConfig();
	}
	
	public static Object get(String path) {
		return getConfig().get(path);
	}
	
	public static List<String> getMessage(String path) {
		return getConfig().getStringList("messages." + path);
	}
	
	public static int getBalanceTopInterval() {
		return getConfig().getInt("BalanceTopTimerInterval");
	}
	
	public static String getCurrencyNameSingular() {
		return getConfig().getString("currencyNameSingular");
	}
	
	public static String getCurrencyNamePlural() {
		return getConfig().getString("currencyNamePlural");
	}
	
	public static double getStartingBalance() {
		return getConfig().getDouble("startingBalance");
	}
	
	public static Locale getLocale() {
		return Locale.forLanguageTag(getConfig().getString("locale"));
	}
	
	public static boolean isCustomSymbol() {
		return getConfig().getBoolean("customSymbolEnabled");
	}
	
	public static String getCustomSymbol() {
		return getConfig().getString("customSymbol");
	}
	
	public static boolean isSQL() {
		return getConfig().getBoolean("mysql.use-mysql");
	}
	
	public static String getHost() {
		return getConfig().getString("mysql.host");
	}
	
	public static int getPort() {
		return getConfig().getInt("mysql.port");
	}
	
	public static String getDatabase() {
		return getConfig().getString("mysql.database");
	}
	
	public static String getUsername() {
		return getConfig().getString("mysql.username");
	}
	
	public static String getPassword() {
		return getConfig().getString("mysql.password");
	}
	
	public static Map<String, Integer> getSuffixes() {
		Map<String, Integer> suffixes = new HashMap<>();
		for (String suffix : getConfig().getConfigurationSection("suffixes").getKeys(false)) {
			suffixes.put(suffix, EconomyMain.getInstance().getConfig().getInt("suffixes." + suffix));
		}
		return suffixes;
	}
	
}
