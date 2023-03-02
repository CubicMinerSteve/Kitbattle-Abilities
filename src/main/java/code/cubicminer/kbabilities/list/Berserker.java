package code.cubicminer.kbabilities.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import code.cubicminer.kbabilities.manager.Configurations;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;

public class Berserker extends Ability{
    
    int cooldown;
    PotionEffect strengthEffect;
    PotionEffect healthEffect;

    @Override
	public String getName() {
		return "Berserker";
	}

    @Override
	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = Configurations.getConfigurationFile("abilities.yml");
		// The Followings are Berserker Ability Settings.
		this.cooldown = file.getInt("Abilities.Berserker.Cooldown");
		this.strengthEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, file.getInt("Abilities.Berserker.Berserk-Lasts-For") * 20, 1);
        this.healthEffect = new PotionEffect(PotionEffectType.HEALTH_BOOST, file.getInt("Abilities.Berserker.Berserk-Lasts-For") * 20, -3);
	}

    Material activationMaterial = XMaterial.REDSTONE_BLOCK.parseMaterial();

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
		if (data.hasCooldown(p, "Berserker"))
		{
			return false;
		}
		data.setCooldown(p, "Berserker", cooldown, true);
		Kitbattle.getInstance().sendUseAbility(p, data);
        p.addPotionEffect(this.strengthEffect);
        p.addPotionEffect(this.healthEffect);
		Location[] particles = {
			p.getLocation(),
			p.getLocation().clone().add(0.0D, 1.0D, 0.0D),
		};
		for (Location particleSpawn : particles) {
			p.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, particleSpawn, 4);
            p.getWorld().spawnParticle(Particle.SMOKE_LARGE, particleSpawn, 4);
		}
		p.playSound(p.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
		for (Entity entity : p.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
			if (!entity.getType().equals(EntityType.PLAYER)) continue; 
			((Player)entity).playSound(p.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
		}	
		return true;
	}

}
