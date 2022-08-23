package me.cubicminer.kbabilities.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.cubicminer.kbabilities.manager.MessageManagerX;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.managers.PlayerDataManager;
import me.wazup.kitbattle.utils.Utils;
import me.wazup.kitbattle.utils.XMaterial;

public class Whirlwind extends Ability {

	static int cooldown;
	static double range;
	static Material activationMaterial = XMaterial.FEATHER.parseMaterial();
	static PotionEffect nauseaEffect;

	public String getName() {
		return "Whirlwind";
	}

	public void load(FileConfiguration file) {
		Whirlwind.cooldown = file.getInt("Abilities.Whirlwind.Cooldown");
		Whirlwind.range = file.getDouble("Abilities.Whirlwind.Whirlwind-Range");
		Whirlwind.nauseaEffect = new PotionEffect(PotionEffectType.CONFUSION, file.getInt("Abilities.Whirlwind.Nausea-Lasts-For") * 20, file.getInt("Abilities.Whirlwind.Nausea-Level") - 1);
	}

	public Material getActivationMaterial() {
		return Whirlwind.activationMaterial;
	}

	public EntityType getActivationProjectile() {
		return null;
	}

	public boolean isAttackActivated() {
		return false;
	}

	public boolean isAttackReceiveActivated() {
		return false;
	}

	public boolean isDamageActivated() {
		return false;
	}

	public boolean isEntityInteractionActivated() {
		return false;
	}

	public boolean execute(Player p, PlayerData data, Event event) {
		if (data.hasCooldown(p, "Whirlwind")) {
			return false;
		} 
    	data.setCooldown(p, "Whirlwind", Whirlwind.cooldown, true);
    	Kitbattle.getInstance().sendUseAbility(p, data);
		Location[] particles = {
			p.getLocation(), 
			p.getLocation().clone().add(0.0D, Whirlwind.range, 0.0D), 
			p.getLocation().clone().add(0.0D, -Whirlwind.range, 0.0D), 
			p.getLocation().clone().add(Whirlwind.range, 0.0D, 0.0D), 
			p.getLocation().clone().add(0.0D, 0.0D, Whirlwind.range), 
			p.getLocation().clone().add(Whirlwind.range, 0.0D, Whirlwind.range), 
			p.getLocation().clone().add(-Whirlwind.range, 0.0D, -Whirlwind.range), 
			p.getLocation().clone().add(Whirlwind.range, 0.0D, -Whirlwind.range), 
			p.getLocation().clone().add(-Whirlwind.range, 0.0D, Whirlwind.range) 
		};
		for (Location particleSpawn : particles) {
			p.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleSpawn, 4);
		}
    	for (Entity nearbyEntity : p.getNearbyEntities(Whirlwind.range, Whirlwind.range, Whirlwind.range)) {
      		if (!(nearbyEntity instanceof Damageable)) {
				continue;
			} 
      		if (nearbyEntity.getType().equals(EntityType.PLAYER)) {
				Kitbattle Plugin = Kitbattle.getInstance();
				if (PlayerDataManager.get((Player)nearbyEntity).getKit() == null || data.getMap().isInSpawn((Player)nearbyEntity)) {
					p.sendMessage((String)Plugin.msgs.messages.get("Use-Ability-Deny"));
        			continue;
				}
        		Player affectedPlayer = (Player)nearbyEntity;
				Vector velocity = affectedPlayer.getVelocity();
				velocity.setX(velocity.getX() + (double)(Utils.random.nextInt(100) - 50) / 100);
				velocity.setY(velocity.getY() + 1.8);
				velocity.setZ(velocity.getZ() + (double)(Utils.random.nextInt(100) - 50) / 100);
				affectedPlayer.setVelocity(velocity);
				affectedPlayer.addPotionEffect(Whirlwind.nauseaEffect);
        		affectedPlayer.sendMessage(((String)MessageManagerX.messages.get("Whirlwind-Strike")).replace("%player%", p.getName()));
				affectedPlayer.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
      		} 
    	} 
    	return true;
	}

}