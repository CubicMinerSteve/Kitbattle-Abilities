package me.cubicminer.kbabilities.list;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.Utils;

public class Resistance extends Ability {

	int resistanceChance;
	double triggerPercentage;
	PotionEffect resistanceEffect;

	@Override
	public String getName() {
		return "Resistance";
	}

	@Override
	public void load(FileConfiguration file) {
		this.resistanceChance = Integer.valueOf(file.getString("Abilities.Resistance.Resistance-Chance").replace("%", "")).intValue();
		this.triggerPercentage = Integer.valueOf(file.getString("Abilities.Resistance.Trigger-Health-Percentage").replace("%", "")).intValue();
		this.resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, file.getInt("Abilities.Resistance.Resistance-Lasts-For") * 20, file.getInt("Abilities.Resistance.Resistance-Level") - 1);
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
		int i = Utils.random.nextInt(100) + 1;
		if (i <= this.resistanceChance) {
			if (((EntityDamageByEntityEvent)event).getDamager().getType() == EntityType.PLAYER) {
				Player damager = (Player)(((EntityDamageByEntityEvent)event).getDamager());
				if (p.getHealth() <= (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) * this.triggerPercentage / 100) {
					Kitbattle.getInstance().sendUseAbility(p, data);
					p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					p.addPotionEffect(this.resistanceEffect);
					p.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 1.0F);
					damager.playSound(p.getLocation(), Sound.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 1.0F);
					return true;
				}
			}
		}
		return false;
	}

}
