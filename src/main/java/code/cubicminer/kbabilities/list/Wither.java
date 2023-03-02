package code.cubicminer.kbabilities.list;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import code.cubicminer.kbabilities.manager.Configurations;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.Utils;

public class Wither extends Ability{

	int witherChance;
	PotionEffect witherEffect;

	@Override
  	public String getName() {
		return "Wither";
  	}

	@Override
  	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = Configurations.getConfigurationFile("abilities.yml");
		// The Followings are Wither Ability Settings.
		this.witherChance = Integer.valueOf(file.getString("Abilities.Wither.Wither-Chance").replace("%", "")).intValue();
		this.witherEffect = new PotionEffect(PotionEffectType.WITHER, file.getInt("Abilities.Wither.Wither-Lasts-For") * 20, file.getInt("Abilities.Wither.Wither-Level") - 1);
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
		return true;
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
		int i = Utils.random.nextInt(100) + 1;
		if (i <= this.witherChance) {
			if (((EntityDamageByEntityEvent)event).getEntity().getType() == EntityType.PLAYER) {
				Player target = (Player)((EntityDamageByEntityEvent)event).getEntity();
				Kitbattle.getInstance().sendUseAbility(p, data);
				target.removePotionEffect(PotionEffectType.WITHER);
				target.addPotionEffect(this.witherEffect);
				return true;
			}
		}
		return false;
  	}

}
