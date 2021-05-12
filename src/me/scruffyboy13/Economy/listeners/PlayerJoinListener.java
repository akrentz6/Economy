package me.scruffyboy13.Economy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.scruffyboy13.Economy.EconomyMain;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if (!EconomyMain.getEco().hasAccount(player.getUniqueId())) {
			EconomyMain.getEco().createAccount(player.getUniqueId());
		}
		
	}
	
}
