package me.scruffyboy13.Economy.eco;

import java.util.UUID;

public class PlayerBalance {

	private UUID uuid;
	private double balance;
	
	public PlayerBalance(UUID uuid, double balance) {
		this.uuid = uuid;
		this.balance = balance;
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
	
}
