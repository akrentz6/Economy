package me.scruffyboy13.Economy.eco;

import java.util.List;
import java.util.UUID;

public interface Economy {

	public boolean createAccount(UUID uuid);
	public boolean hasAccount(UUID uuid);
	public boolean delete(UUID uuid);
	public boolean withdraw(UUID uuid, double amount);
	public boolean deposit(UUID uuid, double amount);
	public boolean set(UUID uuid, double amount);
	public boolean has(UUID uuid, double amount);
	
	public PlayerBalance getBalance(UUID uuid);
	
	public List<PlayerBalance> getPlayers();
	
}
