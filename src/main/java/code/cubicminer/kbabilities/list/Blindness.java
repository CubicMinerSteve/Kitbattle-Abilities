package code.cubicminer.kbabilities.list;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import code.cubicminer.kbabilities.manager.Configurations;
import code.cubicminer.kbabilities.manager.Messages;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.managers.PlayerDataManager;
import me.wazup.kitbattle.utils.XMaterial;

public class Blindness extends Ability {

	int cooldown;
	int range;
	PotionEffect blindnessEffect;

	@Override
	public String getName() {
		return "Blindness";
	}

	@Override
	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = Configurations.getConfigurationFile("abilities.yml");
		// The Followings are Blindness Ability Settings.
		this.cooldown = file.getInt("Abilities.Blindness.Cooldown");
		this.range = file.getInt("Abilities.Blindness.Range");
		this.blindnessEffect = new PotionEffect(PotionEffectType.BLINDNESS, file.getInt("Abilities.Blindness.Blindness-Lasts-For") * 20, 0);
	}

	Material activationMaterial = XMaterial.INK_SAC.parseMaterial();

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
		if (data.hasCooldown(p, "Blindness"))
		{
			return false;
		}
		data.setCooldown(p, "Blindness", cooldown, true);
		Kitbattle.getInstance().sendUseAbility(p, data);
		p.playSound(p.getLocation(), Sound.ENTITY_SQUID_SQUIRT, 1.0F, 1.0F);
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
				affectedPlayer.addPotionEffect(this.blindnessEffect);
				affectedPlayer.playSound(p.getLocation(), Sound.ENTITY_SQUID_SQUIRT, 1.0F, 1.0F);
				affectedPlayer.sendMessage(((String)Messages.loadedMessages.get("Blindness-Activate")).replace("%player%", p.getName()));
			}
		}
		return true;
	}

}
