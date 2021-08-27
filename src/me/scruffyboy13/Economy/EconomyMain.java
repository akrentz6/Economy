package me.scruffyboy13.Economy;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import me.scruffyboy13.Economy.commands.BalanceCommand;
import me.scruffyboy13.Economy.commands.BalanceTopCommand;
import me.scruffyboy13.Economy.commands.PayCommand;
import me.scruffyboy13.Economy.commands.money.MoneyCommandHandler;
import me.scruffyboy13.Economy.data.ConfigHandler;
import me.scruffyboy13.Economy.eco.Economy;
import me.scruffyboy13.Economy.eco.SQLEconomy;
import me.scruffyboy13.Economy.eco.VaultImpl;
import me.scruffyboy13.Economy.eco.YamlEconomy;
import me.scruffyboy13.Economy.listeners.PlayerJoinListener;
import me.scruffyboy13.Economy.runnables.BalanceTopRunnable;

public class EconomyMain extends JavaPlugin {

	private static MoneyCommandHandler moneyCommandHandler;
	private static BalanceTopRunnable balanceTopRunnable;
	private static EconomyMain instance;
	private static VaultImpl vaultImpl;
	private static Economy eco;
	private static Map<String, String> sqlColumns = new HashMap<>();
	private static Map<String, Integer> suffixes = new HashMap<>();

	@Override
	public void onEnable() {

		saveDefaultConfig();
		
		instance = this;

		addSqlColumns();

		suffixes = ConfigHandler.getSuffixes();
		vaultImpl = new VaultImpl();
		
		balanceTopRunnable = new BalanceTopRunnable();
		balanceTopRunnable.start(ConfigHandler.getBalanceTopInterval());
		
		if (!setupEconomy()) {
			disable("Economy couldn't be registed, Vault plugin is missing!");
			return;
		}
		this.getLogger().info("Vault found, Economy has been registered.");

		if (ConfigHandler.getLocale() == null) {
			disable(ConfigHandler.getLocale().getDisplayName() + " is an invalid locale! Change it in your config.yml");
			return;
		}

		moneyCommandHandler = new MoneyCommandHandler();
		this.getCommand("money").setExecutor(moneyCommandHandler);
		this.getCommand("money").setTabCompleter(moneyCommandHandler);
		
		this.getCommand("balance").setExecutor(new BalanceCommand());
		this.getCommand("pay").setExecutor(new PayCommand());
		this.getCommand("balancetop").setExecutor(new BalanceTopCommand());
		
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		
		if (ConfigHandler.isSQL()) {
			eco = new SQLEconomy();
		}
		else {
			eco = new YamlEconomy();
		}

	}

	@Override
	public void onDisable() {

	}

	private void addSqlColumns() {
		sqlColumns.put("Balance", "DECIMAL(65, 2) NOT NULL DEFAULT " + getConfig().getDouble("startingBalance"));
	}

	public static EconomyMain getInstance() {
		return instance;
	}
	
	public static String getPath() {
		return EconomyMain.getInstance().getDataFolder().getAbsolutePath();
	}
	
	public static void warn(String message) {
		EconomyMain.getInstance().getLogger().warning(message);
	}
	
	public static void disable(String message) {
		warn(message);
		Bukkit.getPluginManager().disablePlugin(EconomyMain.getInstance());
	}

	private boolean setupEconomy() {
		if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		this.getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, vaultImpl, this,
				ServicePriority.Highest);
		return true;
	}
	
	public static double getAmountFromString(String string) {
		int mult = 0;
		for (Map.Entry<String, Integer> suffix : suffixes.entrySet()) {
			if (string.endsWith(suffix.getKey())) {
				string = string.substring(0, string.length() - 1);
				mult = suffix.getValue();
			}
		}
		double pow = Math.pow(10, mult);
		return Math.round(Double.valueOf(string) * (100.0 * pow)) / (100.0 * pow) * pow;
	}
	

	@SuppressWarnings("deprecation")
	public static ArrayList<OfflinePlayer> getPlayersFromString(CommandSender sender, String name) {

		OfflinePlayer player = Bukkit.getOfflinePlayer(name);
		
		ArrayList<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		
		if (player == null && !name.equals("@a")) {
			
			return players;
			
		}
		
		if (name.equals("@a")) {
			
			players.addAll(new ArrayList<OfflinePlayer>(Bukkit.getOnlinePlayers()));

			if (sender instanceof OfflinePlayer) {
				
				players.remove((OfflinePlayer) sender);
				
			}
			
			return players;
		}
		
		return new ArrayList<OfflinePlayer>(Arrays.asList(player));
		
	}
	
	public static String format(double amount) {
		Locale locale = ConfigHandler.getLocale();
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		String formatted = numberFormat.format(amount).replace("&nbsp", " ").replace("\u00A0", " ");
		if (ConfigHandler.isCustomSymbol()) {
			formatted = formatted.replace(Currency.getInstance(locale).getSymbol(locale), ConfigHandler.getCustomSymbol());
		}
		return formatted;
	}

	public static void setSuffixes(Map<String, Integer> suffixesFromConfig) {
		EconomyMain.suffixes = suffixesFromConfig;
	}

	public static Map<String, Integer> getSuffixes() {
		return suffixes;
	}
	
	public static Map<String, String> getSQLColumns() {
		return sqlColumns;
	}

	public static BalanceTopRunnable getBalanceTopRunnable() {
		return balanceTopRunnable;
	}

	public static void setBalanceTopRunnable(BalanceTopRunnable balanceTopRunnable) {
		EconomyMain.balanceTopRunnable = balanceTopRunnable;
	}

	public static MoneyCommandHandler getMoneyCommandHandler() {
		return moneyCommandHandler;
	}

	public static Economy getEco() {
		return eco;
	}

}
