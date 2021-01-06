package me.scruffyboy13.Economy;

import java.sql.SQLException;
import java.util.UUID;

import me.scruffyboy13.Economy.utils.FileUtils;
import me.scruffyboy13.Economy.utils.SQLUtils;

public class PlayerManager {

	private UUID uuid;
	private double balance;
	private double topBalance;
	
	private boolean valid;
	
	public PlayerManager() {
		
	}
	
	public PlayerManager(UUID uuid, double balance, double bank) {
		
		this.uuid = uuid;
		this.balance = balance;
		this.topBalance = balance;
		
		if (Economy.getInstance().getConfig().getBoolean("mysql.use-mysql")) {
			try {
				SQLUtils.addPlayerToDatabase(this);
			} catch (SQLException e) {
				valid = false;
				Economy.getInstance().getLogger().warning("There was an error with adding a player to the database! " + e.getMessage());
				return;
			}
		}
		else {
			FileUtils.createIslandFile(this);
		}

		Economy.getPlayerManagerMap().put(uuid, this);
		
		valid = true;
		
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void deposit(double amount) {
		balance += amount;
	}
	
	public void withdraw(double amount) {
		balance -= amount;
	}

	public double getTopBalance() {
		return topBalance;
	}

	public void setTopBalance(double topBalance) {
		this.topBalance = topBalance;
	}
	
}
