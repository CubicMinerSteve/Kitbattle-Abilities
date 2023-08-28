package code.cubicminer.kbabilities.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import code.cubicminer.kbabilities.manager.FileReader;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;

@SuppressWarnings("null")
public class Evocation extends Ability {

	int cooldown;
	int radius;

	@Override
	public String getName() {
		return "Evocation";
	}

	@Override
	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = FileReader.getConfigurationFile("abilities.yml");

		// The Followings are Evocation Ability Settings.
		this.cooldown = file.getInt("Abilities." + getName() + ".Cooldown");
		this.radius = file.getInt("Abilities." + getName() + ".Radius");

		// To make the activition materital customizable. Added in version 1.2.1.
		this.activationMaterial = XMaterial.matchXMaterial(file.getString("Abilities." + getName() + ".Activation-Material")).get().parseMaterial();
	}

	Material activationMaterial = XMaterial.IRON_INGOT.parseMaterial();

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
		if (data.hasCooldown(p, "Evocation"))
		{
			return false;
		}
		if (data.getMap().isInSpawn(p)) {
			Kitbattle Plugin = Kitbattle.getInstance();
			p.sendMessage((String)Plugin.msgs.messages.get("Use-Ability-Deny"));
		}
		data.setCooldown(p, "Evocation", cooldown, true);
		Kitbattle.getInstance().sendUseAbility(p, data);
		World world = p.getWorld();
		if (p.isSneaking()) {
			for (int i = 0; i <= 7; i++) {
				Location centre = p.getLocation();
				double yaw = (90 + p.getLocation().getYaw()) * 0.017 + i * 0.785;
				world.spawnEntity(centre.add(radius * Math.cos(yaw), 0, radius * Math.sin(yaw)), EntityType.EVOKER_FANGS);
			}
		}
		else {
			Location initialpoint = p.getLocation();
			double yaw = (90 + p.getLocation().getYaw()) * 0.017;
			for (int i = 1; i <= 5; i++) {
				world.spawnEntity(initialpoint.add(1.5 * Math.cos(yaw), 0, 1.5 * Math.sin(yaw)), EntityType.EVOKER_FANGS);
			}
		}
		return true;
	}

}