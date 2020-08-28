package fr.spector.survivalplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import fr.spector.survivalplugin.Main;

public class PlayerListeners implements Listener {
	
	private Main main;
	
	public PlayerListeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String title = "§cBienvenue sur Raccoonia !";
		String subtitle = "§fTournez-vous vers §mDieu§r §4LA CITROUILLE§r et vous serez sauvés";
		main.title.sendTitle(player, title, subtitle, 60);
		
		player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, Float.MAX_VALUE, 1);
		return;
	}
	
	@EventHandler
	public void onLevelChange(PlayerLevelChangeEvent event) {
		if(event.getNewLevel() == 30) {
			Player player = event.getPlayer();
			String message = "§d" + player.getName() + " vient d'atteindre le niveau 30 !";
			Bukkit.broadcastMessage(message);
			for(Player pl:main.getServer().getOnlinePlayers()) {
				if(pl!=player) {
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, Float.MAX_VALUE, 1);
				}
			}
		}
	}
	
	@EventHandler
	public void onBedEnter(PlayerBedEnterEvent event) {
		if(event.getBedEnterResult() == BedEnterResult.OK) {
			main.addSleepingPlayer(event.getPlayer());
		}
	}
	
	
	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent event) {
		main.removeSleepingPlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		for(Player pl:main.getServer().getOnlinePlayers()) {
			if(pl!=player) {
				pl.playSound(pl.getLocation(), Sound.BLOCK_BELL_USE, Float.MAX_VALUE, 1);
			}
		}
		
		int level = player.getLevel();
		String deathMessage = event.getDeathMessage() + ". " + player.getName() + " is dead with " + level + " lvl.";
		
		EntityDamageEvent damage = player.getLastDamageCause();
		DamageCause cause = damage.getCause();
		String name = player.getName();
		
		switch (cause) {
		case BLOCK_EXPLOSION:
			deathMessage = name + " s'est fait dynamité et a perdu " + level + " lvl.";
			break;
		
		case CONTACT:
			deathMessage = name + " s'est fait agressé par un cactus et a perdu " + level + " lvl.";
			break;
		
		case DRAGON_BREATH:
			deathMessage = name + " a respiré de l'haleine de dragon et a perdu " + level + " lvl.";
			break;
		
		case DROWNING:
			deathMessage = name + " a respiré de l'eau et a perdu " + level + " lvl.";
			break;
		
		case ENTITY_EXPLOSION:
			deathMessage = name + " a explosé et a perdu " + level + " lvl.";
			break;
			
		case FALL:
			deathMessage = name + " a raté son saut en triple flip position carpé et a perdu " + level + " lvl.";
			break;
			
		case FALLING_BLOCK:
			deathMessage = name + " s'est pris un coup sur la tête et a perdu " + level + " lvl.";
			break;
		
		case FIRE:
			deathMessage = name + " a joué avec le feu et a perdu " + level + " lvl.";
			break;
		
		case FIRE_TICK:
			deathMessage = name + " est mort suite a de grâves brûlures et a perdu " + level + " lvl.";
			break;
		
		case FLY_INTO_WALL:
			deathMessage = name + " a raté son atterrissage et a perdu " + level + " lvl.";
			break;
		
		case HOT_FLOOR:
			deathMessage = name + " s'est brûlé les pieds jusqu'à en mourir et a perdu " + level + " lvl.";
			break;
			
		case ENTITY_ATTACK:
			EntityDamageByEntityEvent damageP = (EntityDamageByEntityEvent)damage;
			Entity killer = damageP.getDamager();
			deathMessage = name + " s'est fait trucider par un(e) " + killer.getName() + " et a perdu " + level + " lvl.";
			if(killer instanceof Player) {
				deathMessage = name + " s'est fait trucider par " + killer.getName() + " et a perdu " + level + " lvl.";
			}
			break;
		
		case LAVA:
			deathMessage = name + " s'est baigné dans la lave et a perdu " + level + " lvl (et aussi tout son stuff).";
			break;
		
		case LIGHTNING:
			deathMessage = name + " s'est abrité sous un arbre par temps d'orage et a perdu " + level + " lvl.";
			break;
			
		case MAGIC:
			deathMessage = name + " s'est pris un §oAvada Kedavra§r et a perdu " + level + " lvl.";
			break;
			
		case MELTING:
			deathMessage = name + " a réussi à mourir face un bonhomme de neige et a perdu " + level + " lvl.";
			break;
			
		case POISON:
			deathMessage = name + " s'est fait lâchement empoisonné et a perdu " + level + " lvl.";
			break;
			
		case STARVATION:
			deathMessage = name + " a oublié de se nourrir et a perdu " + level + " lvl.";
			break;
			
		case SUFFOCATION:
			deathMessage = name + " a respiré du caillou et a perdu " + level + " lvl.";
			break;
			
		case WITHER:
			deathMessage = name + " s'est fait flétrir par un Wither et a perdu " + level + " lvl.";
			break;
			
		default:
			break;
		}
		
		event.setDeathMessage(deathMessage);
		
		Location tomb = player.getLocation();
		String tombMessage = "You died at X:" + Math.round(tomb.getX()) + " , Y:" + Math.round(tomb.getY()) + " , Z:" + Math.round(tomb.getZ());
		player.sendMessage(tombMessage);
		return;
	}
}
