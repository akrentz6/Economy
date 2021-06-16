package me.scruffyboy13.Economy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.scruffyboy13.Economy.EconomyMain;

public class PlayerJoinListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if (!EconomyMain.getEco().hasAccount(player.getUniqueId())) {
			new BukkitRunnable() {

				@Override
				public void run() {
					EconomyMain.getEco().createAccount(player.getUniqueId());
				}
			}.runTaskAsynchronously(EconomyMain.getInstance());
		}
		
	}
	
}
