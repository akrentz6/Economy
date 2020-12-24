package me.scruffyboy13.Economy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.scruffyboy13.Economy.Economy;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if (!Economy.getEconomyCore().hasAccount(player)) {
			Economy.getEconomyCore().createPlayerAccount(player.getName());
		}
		
	}
	
}
