package me.scruffyboy13.Economy.iterators;

import java.util.Iterator;
import java.util.List;

import me.scruffyboy13.Economy.PlayerManager;

public class PlayerManagerIterator implements Iterator<PlayerManager> {

	private List<PlayerManager> playerManagers;
	private int index;
	
	public PlayerManagerIterator(List<PlayerManager> playerManagers) {
		this.playerManagers = playerManagers;
		this.index = 0;
	}
	
	@Override
	public boolean hasNext() {
		return index < playerManagers.size();
	}

	@Override
	public PlayerManager next() {
		PlayerManager island = playerManagers.get(index);
		index++;
		return island;
	}

	public List<PlayerManager> getIslands() {
		return playerManagers;
	}
	
}
