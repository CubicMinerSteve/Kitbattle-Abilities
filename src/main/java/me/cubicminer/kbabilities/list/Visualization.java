package me.cubicminer.kbabilities.list;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.managers.PlayerDataManager;

public class Visualization extends Ability {

	int range;
	int duration;

	@Override
	public String getName() {
		return "Visualization";
	}

	@Override
	public void load(FileConfiguration file) {
		this.range = file.getInt("Abilities.Visualization.Range");
		this.duration = file.getInt("Abilities.Visualization.Spell-Lasts-For") * 20;
	}

	@Override
	public Material getActivationMaterial() {
		return null;
	}

	@Override
	public EntityType getActivationProjectile() {
		return null;
	}

	@Override
	public boolean isAttackActivated() {
		return false;
	}

	@Override
	public boolean isAttackReceiveActivated() {
		return true;
	}

	@Override
	public boolean isDamageActivated() {
		return false;
	}

	@Override
	public boolean isEntityInteractionActivated() {
		return false;
	}

	@Override
	public boolean execute(Player p, PlayerData data, Event event) {
        final HashMap<String,PotionEffect> playerPotionEffect = new HashMap<>();
        final HashMap<String,String> playerKitName = new HashMap<>();
		Kitbattle Plugin = Kitbattle.getInstance();
        // Get all nearby entities.
        for (Entity nearbyEntity : p.getNearbyEntities(this.range, this.range, this.range)) {
            // Check nearby entities to see whether they are players.
            if (nearbyEntity.getType() != EntityType.PLAYER) {
                continue;
            }
            Player playertoRemoveEffectWith = (Player)nearbyEntity;
            PlayerData playerData = PlayerDataManager.get(playertoRemoveEffectWith);
            // Check the player is playing Kitbattle game or not. Or is in the spawn region.
            if (playerData != null && playerData.getKit() != null && !data.getMap().isInSpawn(playertoRemoveEffectWith) && !playertoRemoveEffectWith.getActivePotionEffects().isEmpty()) {
                // Iterate over all potion effects and check whether the target has invisibility effect or not.
                for (PotionEffect potionEffect : playertoRemoveEffectWith.getActivePotionEffects()) {
                    if (potionEffect.getType().equals(PotionEffectType.INVISIBILITY)) {
						// Send use ability information to database.
						Plugin.sendUseAbility(p, data);
                        playerPotionEffect.put(playertoRemoveEffectWith.getName(), potionEffect);
                        playerKitName.put(playertoRemoveEffectWith.getName(), playerData.getKit().getName());
                        playertoRemoveEffectWith.removePotionEffect(potionEffect.getType());
						playertoRemoveEffectWith.playSound(playertoRemoveEffectWith.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1.0F, 1.0F);
						playertoRemoveEffectWith.sendMessage((String)Plugin.msgs.messages.get("Visualization-Activate").replace("%player%", p.getName()));
                    }
                }
            }
        }
        if (!playerPotionEffect.isEmpty()) {
			(new BukkitRunnable() {
				public void run() {
					// Iterate over all player on the potion effect hashmap, if the player doesn't exist (not online) then continue on to the next.
				  	for (String playerName : playerPotionEffect.keySet()) {
						Player playerToApplyEffectOn = Bukkit.getPlayer(playerName);
						// Check whether the target player is online or not, if not then continue on to the next.
						if (playerToApplyEffectOn == null) {
							continue;
						}
						PlayerData playerToApplyEffectOnData = PlayerDataManager.get(playerToApplyEffectOn);
						// Check whether the target player has selected the kit or not, if not go to the next player.
						// Check whether the target player's kit stays the same as before, if not then it indicates that the player has rejoined the game or has been killed.
						// Once the player has been detected that it has quitted the game or has been killed, skip the apply effect process.
						if (playerToApplyEffectOnData.getKit() == null || playerToApplyEffectOnData.getKit().getName() != playerKitName.get(playerToApplyEffectOn.getName())) {
							continue;
						}
						playerToApplyEffectOn.addPotionEffect(playerPotionEffect.get(playerName));
						playerToApplyEffectOn.playSound(playerToApplyEffectOn.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1.0F, 1.0F);
						playerToApplyEffectOn.sendMessage((String)Plugin.msgs.messages.get("Visualization-Deactivate"));
				  	}
				}
			}).runTaskLater(Plugin, this.duration);
		  	return true;
		}
		return false;
	}

}
