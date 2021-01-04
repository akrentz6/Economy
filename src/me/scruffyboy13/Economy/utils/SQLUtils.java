package me.scruffyboy13.Economy.utils;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import me.scruffyboy13.Economy.Economy;
import me.scruffyboy13.Economy.PlayerManager;

public class SQLUtils {

	public static Map<UUID, PlayerManager> getPlayerDataFromDatabase() throws SQLException {
		Map<UUID, PlayerManager> playerManagerMap = new HashMap<>();
		Statement statement = Economy.getSQL().getConnection().createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM Economy;");
		while (result.next()) {
			PlayerManager playerManager = new PlayerManager();
			playerManager.setUUID(UUID.fromString(result.getString("UUID")));
			playerManager.setBalance(result.getDouble("Balance"));
			playerManager.setTopBalance(playerManager.getBalance());
			playerManager.setValid(true);
			playerManagerMap.put(playerManager.getUUID(), playerManager);
		}
		return playerManagerMap;
	}
	
	public static void addPlayerToDatabase(PlayerManager playerManager) throws SQLException {
		PreparedStatement statement = Economy.getSQL().getConnection().prepareStatement("INSERT INTO Economy "
				+ "(UUID, Balance) VALUES (?, ?);");
		statement.setString(1, playerManager.getUUID().toString());
		statement.setDouble(2, playerManager.getBalance());
		statement.executeUpdate();
		statement.close();
	}
	
	public static void savePlayerToDatabase(PlayerManager playerManager) throws SQLException {
		PreparedStatement statement = Economy.getSQL().getConnection().prepareStatement("UPDATE Economy SET "
				+ "UUID=?, Balance=? WHERE UUID=?");
		statement.setString(1, playerManager.getUUID().toString());
		statement.setDouble(2, playerManager.getBalance());
		statement.setString(3, playerManager.getUUID().toString());
		statement.executeUpdate();
		statement.close();
	}
	
	public static void deletePlayerFromDatabase(PlayerManager playerManager) throws SQLException {
		PreparedStatement statement = Economy.getSQL().getConnection().prepareStatement("DELETE FROM Economy WHERE UUID=?");
		statement.setString(1, playerManager.getUUID().toString());
		statement.executeUpdate();
		statement.close();
	}

	public static void createTable() throws SQLException {
		Statement statement = Economy.getSQL().getConnection().createStatement();
		DatabaseMetaData md = Economy.getSQL().getConnection().getMetaData();
		statement.execute("CREATE TABLE IF NOT EXISTS Economy (UUID VARCHAR(36) NOT NULL);");
		for (Map.Entry<String, String> column : Economy.getSQLColumns().entrySet()) {
			if (!md.getColumns(null, null, "Economy", column.getKey()).next()) {
				statement.execute("ALTER TABLE Economy ADD " + column.getKey() + " " + column.getValue() + ";");
			}
		}
		statement.close();
	}

}
