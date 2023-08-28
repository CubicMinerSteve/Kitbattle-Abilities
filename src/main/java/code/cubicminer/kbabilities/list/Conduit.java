package code.cubicminer.kbabilities.list;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import code.cubicminer.kbabilities.manager.FileReader;
import code.cubicminer.kbabilities.manager.Messages;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;

public class Conduit extends Ability{

	int regenerationTime;
	int absorptionTime;

    PotionEffect regenerationEffect;
    PotionEffect absorptionEffect;

    @Override
	public String getName() {
		return "Conduit";
	}

    @Override
	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = FileReader.getConfigurationFile("abilities.yml");

		// The Followings are Conduit Ability Settings.
		this.regenerationTime = file.getInt("Abilities." + getName() + ".Regeneration-Lasts-For") * 20;
		this.absorptionTime = file.getInt("Abilities." + getName() + ".Absorption-Lasts-For") * 20;

        this.regenerationEffect = new PotionEffect(PotionEffectType.REGENERATION, this.regenerationTime , 1);
        this.absorptionEffect = new PotionEffect(PotionEffectType.ABSORPTION, this.absorptionTime, 0);

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
            Kitbattle.getInstance().sendUseAbility(p, data);
            p.addPotionEffect(this.absorptionEffect);
            p.addPotionEffect(this.regenerationEffect);
			p.sendMessage(Messages.loadedMsgs.get("Abilities.Conduit-Effect-Apply"));
        }
		return true;
	}

}
