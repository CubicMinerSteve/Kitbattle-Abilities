package me.cubicminer.kbabilities.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;

public class Explosive extends Ability{
  
    int cooldown;
	int explosionPower;

    @Override
	public String getName() {
		return "Explosive";
	}

    @Override
	public void load(FileConfiguration file) {
		this.cooldown = file.getInt("Abilities.Explosive.Cooldown");
		this.explosionPower = file.getInt("Abilities.Explosive.Explosion-Power");
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
		if (data.hasCooldown(p, "Explosive"))
		{
			return false;
		}
		data.setCooldown(p, "Explosive", cooldown, true);
		Kitbattle.getInstance().sendUseAbility(p, data);
        ProjectileHitEvent projectileHitEvent = (ProjectileHitEvent)event;
        Location targetLocation = projectileHitEvent.getEntity().getLocation();
        projectileHitEvent.getEntity().getWorld().createExplosion(targetLocation, this.explosionPower, false, false);
		return true;
	}

}
