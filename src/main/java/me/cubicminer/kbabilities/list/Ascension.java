package me.cubicminer.kbabilities.list;

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

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;

public class Ascension extends Ability{
    
    int cooldown;
    int levitateTime;
    PotionEffect levitationEffect;
    PotionEffect slowfallingEffect;

    @Override
	public String getName() {
		return "Ascension";
	}

    @Override
	public void load(FileConfiguration file) {
		this.cooldown = file.getInt("Abilities.Ascension.Cooldown");
        this.levitateTime = file.getInt("Abilities.Ascension.Levitation-Lasts-For") * 20;
		this.levitationEffect = new PotionEffect(PotionEffectType.LEVITATION, this.levitateTime, 1);
        this.slowfallingEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, file.getInt("Abilities.Ascension.Slow-Falling-Lasts-For") * 20, 0);
	}

    Material activationMaterial = XMaterial.PHANTOM_MEMBRANE.parseMaterial();

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
		if (data.hasCooldown(p, "Ascension"))
		{
			return false;
		}
		data.setCooldown(p, "Ascension", cooldown, true);
		Kitbattle.getInstance().sendUseAbility(p, data);
        p.addPotionEffect(this.levitationEffect);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Kitbattle.getInstance(), new Runnable() {
            public void run(){
                p.addPotionEffect(slowfallingEffect);
            }
        }, levitateTime);
        p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
        for (Entity nearbyEntity : p.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
			if (!nearbyEntity.getType().equals(EntityType.PLAYER)) continue; 
			((Player)nearbyEntity).playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
		}		
		return true;
	}

}
