package code.cubicminer.kbabilities.list;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

import code.cubicminer.kbabilities.manager.FileReader;
import code.cubicminer.kbabilities.utils.MoreUtils;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.Utils;
import me.wazup.kitbattle.utils.XMaterial;

@SuppressWarnings("null")
public class Freeze extends Ability {

	int cooldown;
	int duration;

	@Override
	public String getName() {
		return "Freeze";
	}

	@Override
	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = FileReader.getConfigurationFile("abilities.yml");

		// The Followings are Freeze Ability Settings.
		this.cooldown = file.getInt("Abilities." + getName() + ".Cooldown");
		this.duration = file.getInt("Abilities." + getName() + ".IceCube-Lasts-For") * 20;

		// To make the activition materital customizable. Added in version 1.2.1.
		this.activationMaterial = XMaterial.matchXMaterial(file.getString("Abilities." + getName() + ".Activation-Material")).get().parseMaterial();
	}

	Material activationMaterial = XMaterial.ICE.parseMaterial();
	EntityType activationProjectile = EntityType.SNOWBALL;

	@Override
	public Material getActivationMaterial() {
		return activationMaterial;
	}

	@Override
	public EntityType getActivationProjectile() {
		return activationProjectile;
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
		if (event.getEventName().equals("PlayerInteractEvent")) {
			if (data.hasCooldown(p, "Freeze")) {
				return false;
			}
			data.setCooldown(p, "Freeze", cooldown, true);
			Kitbattle.getInstance().sendUseAbility(p, data);
			p.launchProjectile(Snowball.class).setMetadata("Freeze", new FixedMetadataValue(Kitbattle.getInstance(), true));
			return true;
		} else {
			// The event the player got hit by snowball.
			EntityDamageByEntityEvent hit = (EntityDamageByEntityEvent) event;
			// Check whether the snowball has given metadata and whether the entity is player.
			if (hit.getDamager().hasMetadata("Freeze") && hit.getEntityType() == EntityType.PLAYER) {
				Kitbattle kitbattlePlugin = Kitbattle.getInstance();
				// Get the player's location.
				Location cubeLocation = ((Player)hit.getEntity()).getLocation().add(0, 9, 0);
				// Create a BlockState list of blocks to be rollbacked.
				List<BlockState> blocks = new ArrayList<BlockState>();
				// Create a Location list of locations to be modified.
				ArrayList<Location> blocksLocation = new ArrayList<Location>();
				// Set the cube's material type.
				boolean canActivate = true;
				// Check if the space is enough.
				for (Location CubeBlock : MoreUtils.getIceCage(cubeLocation)) {
					if (!(CubeBlock.getBlock().getType().equals(Material.AIR))) {
						canActivate = false;
						break;
					}
					// Add the corresponding block into the rollback list.
					blocks.add(CubeBlock.getBlock().getState());
				}
				if (canActivate) {
					blocksLocation = MoreUtils.getIceCage(cubeLocation);
					// Generate ice cube. Material is snow block.
					Material iceMaterial = XMaterial.SNOW_BLOCK.parseMaterial();
					blocksLocation.get(0).getBlock().setType(iceMaterial);
					blocksLocation.get(1).getBlock().setType(iceMaterial);
					// Generate ice cube. Material is light blue stained glass pane.
					iceMaterial = XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE.parseMaterial();
					for (int i = 2; i <= 9; i++) {
						blocksLocation.get(i).getBlock().setType(iceMaterial);
					}
					// Generate ice cube. Material is ice block.
					iceMaterial = XMaterial.ICE.parseMaterial();
					blocksLocation.get(10).getBlock().setType(iceMaterial);
					blocksLocation.get(11).getBlock().setType(iceMaterial);
					// Teleport the player.
					((Player)hit.getEntity()).teleport(((Player)hit.getEntity()).getLocation().add(0, 9.0D, 0));
					// Schedule the rollback task and wait for running.
					Bukkit.getScheduler().scheduleSyncDelayedTask(kitbattlePlugin, new Runnable() {
						public void run(){
							cubeLocation.getWorld().createExplosion(cubeLocation, 0, false);
							p.playSound(cubeLocation, Sound.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
							for (BlockState state : blocks){
								Utils.Rollback(state);
								kitbattlePlugin.toRollback.remove(state);
							}
						}
					}, duration);
					return true;
				}
				p.sendMessage(((String)kitbattlePlugin.msgs.messages.get("Abilities.Freeze-No-Space")));
			}
			return false;
		}
	}

}