package code.cubicminer.kbabilities.list;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import code.cubicminer.kbabilities.manager.FileReader;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.Utils;

public class Weakness extends Ability{

	int weaknessChance;
	int weaknessTime;
	int weaknessLevel;

	PotionEffect weaknessEffect;

	@Override
  	public String getName() {
		return "Weakness";
  	}

	@Override
  	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = FileReader.getConfigurationFile("abilities.yml");

		// The Followings are Weakness Ability Settings.
		this.weaknessChance = Integer.valueOf(file.getString("Abilities." + getName() + ".Weakness-Chance").replace("%", "")).intValue();
		this.weaknessTime = file.getInt("Abilities." + getName() + ".Weakness-Lasts-For") * 20;
		this.weaknessLevel = file.getInt("Abilities." + getName() + ".Weakness-Level") - 1;

		this.weaknessEffect = new PotionEffect(PotionEffectType.WEAKNESS, this.weaknessTime, this.weaknessLevel);
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
		if (i <= this.weaknessChance) {
			if (((EntityDamageByEntityEvent)event).getDamager().getType() == EntityType.PLAYER) {
				Player damager = (Player)(((EntityDamageByEntityEvent)event).getDamager());
				Kitbattle.getInstance().sendUseAbility(p, data);
				damager.removePotionEffect(PotionEffectType.WEAKNESS);
				damager.addPotionEffect(this.weaknessEffect);
				return true;
			}
		}
		return false;
  	}

}