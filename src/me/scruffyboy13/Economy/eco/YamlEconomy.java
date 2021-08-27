package me.scruffyboy13.Economy.eco;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.scruffyboy13.Economy.EconomyMain;
import me.scruffyboy13.Economy.data.ConfigHandler;
import me.scruffyboy13.Economy.data.YamlData;

public class YamlEconomy implements Economy {
	
	public YamlEconomy() {
		
		Path dataDir = Paths.get(EconomyMain.getPath() + "/data/");
		if (!Files.exists(dataDir)) {
			try {
				Files.createDirectory(dataDir);
			} catch (IOException e) {
				EconomyMain.warn("There was an error creating the data directory.");
	            return;
			}
		}
		
	}

	@Override
	public boolean createAccount(UUID uuid) {
		set(uuid, ConfigHandler.getStartingBalance());
		return true;
	}

	@Override
	public boolean hasAccount(UUID uuid) {
		for (PlayerBalance pb : getPlayers()) {
			if (pb.getUUID().equals(uuid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean delete(UUID uuid) {
		File islandFile = new File(EconomyMain.getPath() + "/data/" + uuid.toString() + ".yml");
		islandFile.delete();
		return true;
	}

	@Override
	public boolean withdraw(UUID uuid, double amount) {
		return set(uuid, getBalance(uuid).getBalance() - amount);
	}

	@Override
	public boolean deposit(UUID uuid, double amount) {
		return set(uuid, getBalance(uuid).getBalance() + amount);
	}

	@Override
	public boolean set(UUID uuid, double amount) {
		if (amount < 0)
			return false;
		YamlData data = new YamlData(uuid.toString() + ".yml", EconomyMain.getPath() + "/data");
		data.getConfig().set("UUID", uuid.toString());
		data.getConfig().set("Balance", amount);
		data.saveConfig();
		return true;
	}

	@Override
	public boolean has(UUID uuid, double amount) {
		return getBalance(uuid).getBalance() >= amount;
	}

	@Override
	public PlayerBalance getBalance(UUID uuid) {
		try {
			YamlData data = new YamlData(uuid.toString() + ".yml", EconomyMain.getPath() + "/data");
			double balance = data.getConfig().getDouble("Balance");
			return new PlayerBalance(uuid, balance);
		} catch (Exception e) {
			return new PlayerBalance(uuid, 0);
		}
	}

	@Override
	public List<PlayerBalance> getPlayers() {
		List<PlayerBalance> playerData = new ArrayList<PlayerBalance>();
		File[] files = new File(EconomyMain.getPath() + "/data").listFiles();
		for (File file : files) {
			playerData.add(getBalance(UUID.fromString(file.getName().replace(".yml", ""))));
		}
		return playerData;
	}

}
