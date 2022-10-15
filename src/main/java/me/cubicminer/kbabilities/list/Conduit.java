package me.cubicminer.kbabilities.list;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;

public class Conduit extends Ability{

    PotionEffect regenerationEffect;
    PotionEffect absorptionEffect;

    @Override
	public String getName() {
		return "Conduit";
	}

    @Override
	public void load(FileConfiguration file) {
        this.regenerationEffect = new PotionEffect(PotionEffectType.REGENERATION, file.getInt("Abilities.Conduit.Regeneration-Lasts-For") * 20, 1);
        this.absorptionEffect = new PotionEffect(PotionEffectType.ABSORPTION, file.getInt("Abilities.Conduit.Absorption-Lasts-For") * 20, 0);
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
        if (p.isInWater()) {
            Kitbattle Plugin = Kitbattle.getInstance();
            Kitbattle.getInstance().sendUseAbility(p, data);
            p.addPotionEffect(this.absorptionEffect);
            p.addPotionEffect(this.regenerationEffect);
            p.sendMessage((String)Plugin.msgs.messages.get("Conduit-Effect-Apply"));
        }
		return true;
	}

}
