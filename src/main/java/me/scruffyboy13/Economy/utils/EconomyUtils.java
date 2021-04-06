package me.scruffyboy13.Economy.utils;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import me.scruffyboy13.Economy.Economy;
import me.scruffyboy13.Economy.PlayerManager;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class EconomyUtils implements net.milkbowl.vault.economy.Economy {

	@Override
	public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public boolean createPlayerAccount(String name) {
		return createPlayerAccount(Bukkit.getPlayer(name).getUniqueId());
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		return createPlayerAccount(player.getUniqueId());
	}

	@Override
	public boolean createPlayerAccount(String name, String world) {
		return createPlayerAccount(Bukkit.getPlayer(name).getUniqueId());
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String world) {
		return createPlayerAccount(player.getUniqueId());
	}
	
	private boolean createPlayerAccount(UUID uuid) {
		double startingBalance = Economy.getInstance().getConfig().getDouble("startingBalance");
		double startingBankBalance = Economy.getInstance().getConfig().getDouble("startingBankBalance");
		PlayerManager playerManager = new PlayerManager(uuid, startingBalance, startingBankBalance);
		if (playerManager != null && playerManager.isValid()) {
			Economy.getPlayerManagerMap().put(uuid, playerManager);
			save(playerManager);
			return true;
		}
		return false;
	}

	@Override
	public String currencyNamePlural() {
		return Economy.getInstance().getConfig().getString("currencyNamePlural");
	}

	@Override
	public String currencyNameSingular() {
		return Economy.getInstance().getConfig().getString("currencyNameSingular");
	}

	@Override
	public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse depositPlayer(String name, double amount) {
		return deposit(Bukkit.getPlayer(name).getUniqueId(), amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		return deposit(player.getUniqueId(), amount);
	}

	@Override
	public EconomyResponse depositPlayer(String name, String world, double amount) {
		return deposit(Bukkit.getPlayer(name).getUniqueId(), amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String world, double amount) {
		return deposit(player.getUniqueId(), amount);
	}
	
	private EconomyResponse deposit(UUID uuid, double amount) {
		if (amount < 0) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot deposit negative funds!");
        }
		if (!Economy.getPlayerManagerMap().containsKey(uuid)) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account doesn't exist!");
		}
		PlayerManager playerManager = Economy.getPlayerManagerMap().get(uuid);
		playerManager.deposit(amount);
		save(playerManager);
        return new EconomyResponse(amount, playerManager.getBalance(), ResponseType.SUCCESS, "");
		
	}

	@Override
	public String format(double amount) {
		Locale locale = Locale.forLanguageTag(Economy.getInstance().getConfig().getString("locale"));
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		String formatted = numberFormat.format(amount).replace("&nbsp", " ");
		if (Economy.getInstance().getConfig().getBoolean("customSymbolEnabled")) {
			formatted = formatted.replace(Currency.getInstance(locale).getSymbol(locale), 
					Economy.getInstance().getConfig().getString("customSymbol"));
		}
		return formatted;
	}

	@Override
	public int fractionalDigits() {
		return -1;
	}

	@Override
	public double getBalance(String name) {
		return Economy.getPlayerManagerMap().get(Bukkit.getPlayer(name).getUniqueId()).getBalance();
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return Economy.getPlayerManagerMap().get(player.getUniqueId()).getBalance();
	}

	@Override
	public double getBalance(String name, String world) {
		return getBalance(name);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player);
	}

	@Override
	public List<String> getBanks() {
        return new ArrayList<String>();
	}

	@Override
	public String getName() {
		return "Economy";
	}

	@Override
	public boolean has(String name, double amount) {
		return getBalance(name) >= amount;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		return getBalance(player) >= amount;
	}

	@Override
	public boolean has(String name, String world, double amount) {
		return getBalance(name) >= amount;
	}

	@Override
	public boolean has(OfflinePlayer player, String world, double amount) {
		return getBalance(player) >= amount;
	}

	@Override
	public boolean hasAccount(String name) {
		return Economy.getPlayerManagerMap().containsKey(Bukkit.getPlayer(name).getUniqueId());
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		return Economy.getPlayerManagerMap().containsKey(player.getUniqueId());
	}

	@Override
	public boolean hasAccount(String name, String world) {
		return Economy.getPlayerManagerMap().containsKey(Bukkit.getPlayer(name).getUniqueId());
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String world) {
		return Economy.getPlayerManagerMap().containsKey(player.getUniqueId());
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Bank accounts aren't supported!");
	}

	@Override
	public boolean isEnabled() {
		return Economy.getInstance() != null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String name, double amount) {
		return withdraw(Bukkit.getPlayer(name).getUniqueId(), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		return withdraw(player.getUniqueId(), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String name, String world, double amount) {
		return withdraw(Bukkit.getPlayer(name).getUniqueId(), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String world, double amount) {
		return withdraw(player.getUniqueId(), amount);
	}
	
	private EconomyResponse withdraw(UUID uuid, double amount) {
		if (amount < 0) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds!");
		}
		if (!Economy.getPlayerManagerMap().containsKey(uuid)) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account doesn't exist!");
		}
		PlayerManager playerManager = Economy.getPlayerManagerMap().get(uuid);
		if (playerManager.getBalance() >= amount) {
			playerManager.withdraw(amount);
			save(playerManager);
			return new EconomyResponse(amount, playerManager.getBalance(), ResponseType.SUCCESS, "");
		}
		else {
			return new EconomyResponse(0, playerManager.getBalance(), ResponseType.FAILURE, "Insufficient funds!");
		}
	}
	
	private void save(PlayerManager playerManager) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (Economy.getInstance().getConfig().getBoolean("mysql.use-mysql")) {
					try {
						SQLUtils.savePlayerToDatabase(playerManager);
					} catch (SQLException e) {
						Economy.getInstance().getLogger().warning("There was an error with saving to the database, "
								+ "this isn't a problem with the plugin.");
						return;
					}
					return;
				}
				else {
					FileUtils.saveToFile(FileUtils.getIslandFile(playerManager), playerManager);
					return;
				}
			}
		}.runTaskAsynchronously(Economy.getInstance());
	}

}
