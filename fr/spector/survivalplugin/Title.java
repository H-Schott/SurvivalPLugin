package fr.spector.survivalplugin;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R2.IChatBaseComponent;
import net.minecraft.server.v1_16_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R2.PacketPlayOutChat;
import net.minecraft.server.v1_16_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_16_R2.PacketPlayOutTitle.EnumTitleAction;

public class Title {
	
	public void sendTitle(Player player, String title, String subtitle, int ticks) {
		// creer un ichatbasecomponent
		IChatBaseComponent basetitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent basesubtitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		// creer le packet
		PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, basetitle);
		PacketPlayOutTitle subtitlepacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, basesubtitle);
		// envoie au joueur
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(titlepacket);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(subtitlepacket);
		// mettre du delai
		sendTime(player, ticks);
	}
	
	private void sendTime(Player player, int ticks) {
		PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	
	/*
	public void sendActionBar(Player player, String message) {
		// creer un ichatbasecomponent
		IChatBaseComponent base = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		// creer le packet
		PacketPlayOutChat packet = new PacketPlayOutChat(base);
		// envoie au joueur
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
	}
	*/
}
