package me.cubicminer.kbabilities.list;

import org.bukkit.Material;
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

public class Weakness extends Ability{

	int weaknessChance;
	PotionEffect weaknessEffect;

  	public String getName() {
		return "Weakness";
  	}

  	public void load(FileConfiguration file) {
		this.weaknessChance = Integer.valueOf(file.getString("Abilities.Weakness.Weakness-Chance").replace("%", "")).intValue();
		this.weaknessEffect = new PotionEffect(PotionEffectType.WEAKNESS, file.getInt("Abilities.Weakness.Weakness-Lasts-For") * 20, file.getInt("Abilities.Weakness.Weakness-Level") - 1);
  	}

  	public Material getActivationMaterial() {
		return null;
  	}

  	public EntityType getActivationProjectile() {
		return null;
  	}

  	public boolean isAttackActivated() {
		return false;
  	}

	public boolean isAttackReceiveActivated() {
		return true;
  	}

  	public boolean isDamageActivated() {
		return false;
  	}

  	public boolean isEntityInteractionActivated() {
		return false;
  	}

  	public boolean execute(Player p, PlayerData data, Event event) {
		int i = Utils.random.nextInt(100) + 1;
		if (i <= this.weaknessChance) {
			if (((EntityDamageByEntityEvent)event).getDamager().getType() == EntityType.PLAYER) {
				Player damager = (Player) ((EntityDamageByEntityEvent)event).getDamager();
				Kitbattle.getInstance().sendUseAbility(p, data);
				damager.removePotionEffect(PotionEffectType.WEAKNESS);
				damager.addPotionEffect(this.weaknessEffect);
				return true;
			}
		}
		return false;
  	}

}