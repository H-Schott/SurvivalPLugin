package fr.spector.survivalplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.spector.survivalplugin.commands.LocateCommandExecutor;
import fr.spector.survivalplugin.listeners.BlockListener;
import fr.spector.survivalplugin.listeners.PlayerListeners;

public class Main extends JavaPlugin{
	
	public Title title = new Title();
	
	private List<Player> sleepingPlayers = new ArrayList<>();
	
	public void onEnable() {
		saveDefaultConfig();
		getLogger().info("PLUGIN ON");
		
		getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		
		getCommand("locate").setExecutor(new LocateCommandExecutor());
		
	}
	
	public void addSleepingPlayer(Player player) {
		
		if(!sleepingPlayers.contains(player)) {
			sleepingPlayers.add(player);
		}
		checkBed();
	}
	
	public void removeSleepingPlayer(Player player) {
		
		if(sleepingPlayers.contains(player)) {
			sleepingPlayers.remove(player);
		}
		// checkBed();
	}
	
	public void checkBed() {
		double nbOnlinePlayers = getServer().getOnlinePlayers().size();
		double nbSleepingPlayers = sleepingPlayers.size();
		String message = "§eIl y a " + (int)nbSleepingPlayers + "/" + (int)nbOnlinePlayers + " joueurs qui dorment ! ";
		if(nbSleepingPlayers/nbOnlinePlayers >= 0.5) {
			Bukkit.broadcastMessage(message);
			Bukkit.broadcastMessage("§eIl y a assez de joueurs qui dorment ! La nuit passe !");
			Bukkit.getWorld("raccoonia2020").setTime(0);
			Bukkit.getWorld("raccoonia2020").setStorm(false);
		}else {
			int nbMissingPlayers = (int)(Math.round(nbOnlinePlayers/2) - nbSleepingPlayers);
			Bukkit.broadcastMessage(message);
			Bukkit.broadcastMessage("Il manque " + nbMissingPlayers + " joueurs pour passer la nuit.");
		}
	}
}
