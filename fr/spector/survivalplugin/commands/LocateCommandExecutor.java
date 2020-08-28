package fr.spector.survivalplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocateCommandExecutor implements CommandExecutor {
	
	// commande affichant les coordonnees du joueur dans le chat
	
	private int cost = 1;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(label.equalsIgnoreCase("locate")) {
				if(player.getLevel() < cost) {
					player.sendMessage("Il faut " + cost + " niveaux pour éxecuter cette commande.");
				}else {
					Location playerPos = player.getLocation();
					Bukkit.broadcastMessage(player.getName() + " se trouve en X:" + Math.round(playerPos.getX()) + " , Y:" + Math.round(playerPos.getY()) + " , Z:" + Math.round(playerPos.getZ()));
					player.setLevel(player.getLevel() - cost);
				}
			}else {
				player.sendMessage("commande inconnue");
			}
		}else {
			sender.sendMessage("Seul un joueur peut executer cette commande.");
		}
		return false;
	}

}
