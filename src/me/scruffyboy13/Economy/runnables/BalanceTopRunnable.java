package me.scruffyboy13.Economy.runnables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import me.scruffyboy13.Economy.EconomyMain;
import me.scruffyboy13.Economy.eco.PlayerBalance;

public class BalanceTopRunnable extends BukkitRunnable {
	
	private List<PlayerBalance> balanceTop = new ArrayList<PlayerBalance>();
	
	@Override
	public void run() {
		
		List<PlayerBalance> btop = new ArrayList<PlayerBalance>(EconomyMain.getEco().getPlayers());
		btop.sort(Comparator.comparingDouble(PlayerBalance::getBalance).reversed());

		this.balanceTop = btop;
		
	}

	public void start(int interval) {
		
		this.runTaskTimerAsynchronously(EconomyMain.getInstance(), 1, interval);
		
	}

	public List<PlayerBalance> getBalanceTop() {
		return balanceTop;
	}

}
