package me.scruffyboy13.Economy.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import me.scruffyboy13.Economy.DataManager;
import me.scruffyboy13.Economy.Economy;
import me.scruffyboy13.Economy.PlayerManager;

public class FileUtils {

	public static void saveToFile(DataManager file, PlayerManager playerManager) {
		FileConfiguration config = file.getConfig();
		config.set("UUID", playerManager.getUUID().toString());
		config.set("Balance", playerManager.getBalance());
		file.saveConfig();
	}
	
	public static Map<UUID, PlayerManager> getPlayerDataFromDatabase() {
		Map<UUID, PlayerManager> playerManagerMap = new HashMap<>();
		File[] files = new File(Economy.getInstance().getDataFolder().getAbsolutePath() + "/data").listFiles();
		for (File file : files) {
			DataManager data = new DataManager(file.getName(), 
					Economy.getInstance().getDataFolder().getAbsolutePath() + "/data");
			FileConfiguration config = data.getConfig();
			PlayerManager playerManager = new PlayerManager();
			playerManager.setUUID(UUID.fromString(config.getString("UUID")));
			playerManager.setBalance(config.getDouble("Balance"));
			playerManager.setTopBalance(playerManager.getBalance());
			playerManager.setValid(true);
			playerManagerMap.put(playerManager.getUUID(), playerManager);
		}
		return playerManagerMap;
	}
	
	public static DataManager createIslandFile(PlayerManager playerManager) {
		DataManager islandFile = new DataManager(playerManager.getUUID().toString() + ".yml", 
				Economy.getDataFolderPath() + "/data");
		saveToFile(islandFile, playerManager);
		return islandFile;
	}
	
	public static boolean deleteIslandFile(PlayerManager playerManager) {
		File islandFile = new File(Economy.getDataFolderPath() + "/data/" + playerManager.getUUID().toString());
		return islandFile.delete();
	}
	
	public static DataManager getIslandFile(PlayerManager playerManager) {
		DataManager config = new DataManager(playerManager.getUUID().toString() + ".yml", Economy.getDataFolderPath() + "/data");
		return config;
	}
	
}
