package fr.spector.survivalplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import fr.spector.survivalplugin.Main;

public class BlockListener implements Listener{
	
	private Main main;
	
	public BlockListener(Main main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		
		Block block = event.getBlock();
		
		if(block.getBlockData().getMaterial() == Material.DIAMOND_ORE) {
			
			Player player = event.getPlayer();
			int nbBlockMined = player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE) + 1;
			
			Bukkit.broadcastMessage("§b" + player.getName() + " a miné §c" + nbBlockMined + "§b diamants !");
			
			for(Player pl:main.getServer().getOnlinePlayers()) {
				if(pl!=player) {
					pl.playSound(pl.getLocation(), Sound.BLOCK_BAMBOO_BREAK, Float.MAX_VALUE, 1);
				}
			}
		}
	}
}
