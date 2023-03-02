package code.cubicminer.kbabilities.list;

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

import code.cubicminer.kbabilities.manager.Configurations;
import code.cubicminer.kbabilities.manager.Messages;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.managers.PlayerDataManager;
import me.wazup.kitbattle.utils.Utils;
import me.wazup.kitbattle.utils.XMaterial;

public class Whirlwind extends Ability {

	int cooldown;
	double range;
	Material activationMaterial = XMaterial.FEATHER.parseMaterial();
	PotionEffect nauseaEffect;

	@Override
	public String getName() {
		return "Whirlwind";
	}

	@Override
	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = Configurations.getConfigurationFile("abilities.yml");
		// The Followings are Whirlwind Ability Settings.
		this.cooldown = file.getInt("Abilities.Whirlwind.Cooldown");
		this.range = file.getDouble("Abilities.Whirlwind.Whirlwind-Range");
		this.nauseaEffect = new PotionEffect(PotionEffectType.CONFUSION, file.getInt("Abilities.Whirlwind.Nausea-Lasts-For") * 20, 0);
	}

	@Override
	public Material getActivationMaterial() {
		return this.activationMaterial;
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
		return false;
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
		if (data.hasCooldown(p, "Whirlwind")) {
			return false;
		}
    	data.setCooldown(p, "Whirlwind", this.cooldown, true);
    	Kitbattle.getInstance().sendUseAbility(p, data);
		Location[] particles = {
			p.getLocation(),
			p.getLocation().clone().add(0.0D, this.range, 0.0D),
			p.getLocation().clone().add(0.0D, -this.range, 0.0D),
			p.getLocation().clone().add(this.range, 0.0D, 0.0D),
			p.getLocation().clone().add(0.0D, 0.0D, this.range),
			p.getLocation().clone().add(this.range, 0.0D, this.range),
			p.getLocation().clone().add(-this.range, 0.0D, -this.range),
			p.getLocation().clone().add(this.range, 0.0D, -this.range),
			p.getLocation().clone().add(-this.range, 0.0D, this.range)
		};
		for (Location particleSpawn : particles) {
			p.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, particleSpawn, 8);
		}
		p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
    	for (Entity nearbyEntity : p.getNearbyEntities(this.range, this.range, this.range)) {
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
				affectedPlayer.addPotionEffect(this.nauseaEffect);
				affectedPlayer.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F);
				affectedPlayer.sendMessage(((String)Messages.loadedMessages.get("Whirlwind-Strike")).replace("%player%", p.getName()));
      		}
    	}
    	return true;
	}

}