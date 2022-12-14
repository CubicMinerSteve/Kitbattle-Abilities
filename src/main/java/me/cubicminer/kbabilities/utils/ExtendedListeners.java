package me.cubicminer.kbabilities.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.AbilityManager;
import me.wazup.kitbattle.events.PlayerUseAbilityEvent;
import me.wazup.kitbattle.managers.PlayerDataManager;

public class ExtendedListeners implements Listener {

	private final Kitbattle plugin;

	public ExtendedListeners(Kitbattle paramKitbattle) {
		this.plugin = paramKitbattle;
	}

	@EventHandler
	public void AbilityArrowHitEvent(ProjectileHitEvent paramProjectileHitEvent) {
		if (paramProjectileHitEvent.getEntity().getType() != EntityType.ARROW) {
			return;
		}
		Arrow arrow = (Arrow)paramProjectileHitEvent.getEntity();
		if (!(arrow.getShooter() instanceof Player)) {
			return;
		}
		Player arrowShooter = (Player)arrow.getShooter();
		if (AbilityManager.getInstance().hasSpecialAbility(arrowShooter, "Explosive")) {
			PlayerData playerData = PlayerDataManager.get(arrowShooter);
			if (playerData.getMap().isInSpawn(arrowShooter)) {
		  		arrowShooter.sendMessage((String)this.plugin.msgs.messages.get("Ability-Use-Deny"));
		  		return;
			}
			boolean bool = AbilityManager.getInstance().getAbility("Explosive").execute(arrowShooter, playerData, (Event)paramProjectileHitEvent);
			if (bool) {
		  		PlayerUseAbilityEvent playerUseAbilityEvent = new PlayerUseAbilityEvent(arrowShooter, "Explosive");
		  		Bukkit.getPluginManager().callEvent((Event)playerUseAbilityEvent);
			}
		}
		return;
	}

	@EventHandler
	public void AbilityPlayerDeathEvent(PlayerDeathEvent paramPlayerDeathEvent) {
		if (paramPlayerDeathEvent.getEntity().getKiller() == null) {
			return;
		}
		Player playerKiller = (Player)paramPlayerDeathEvent.getEntity().getKiller();
		if (AbilityManager.getInstance().hasSpecialAbility(playerKiller, "Conduit")) {
			PlayerData playerData = PlayerDataManager.get(playerKiller);
			boolean bool = AbilityManager.getInstance().getAbility("Conduit").execute(playerKiller, playerData, (Event)paramPlayerDeathEvent);
			if (bool) {
				PlayerUseAbilityEvent playerUseAbilityEvent = new PlayerUseAbilityEvent(playerKiller, "Conduit");
				Bukkit.getPluginManager().callEvent((Event)playerUseAbilityEvent);
			}
			return;
		}
	}

}