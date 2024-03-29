package code.cubicminer.kbabilities.list;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import code.cubicminer.kbabilities.manager.FileReader;
import code.cubicminer.kbabilities.manager.Messages;
import code.cubicminer.kbabilities.utils.MoreUtils;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.PlayerData;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.utils.XMaterial;

@SuppressWarnings("null")
public class Enchanting extends Ability{

    int cooldown;

	@Override
	public String getName() {
		return "Enchanting";
	}

	@Override
	public void load(FileConfiguration file) {
		// To avoid unexpected data corruption and for future plugin compatibility. Added in version 1.2.0.
		file = FileReader.getConfigurationFile("abilities.yml");

		// The Followings are Enchanting Ability Settings.
		this.cooldown = file.getInt("Abilities." + getName() + ".Cooldown");

		// To make the activition materital customizable. Added in version 1.2.1.
		this.activationMaterial = XMaterial.matchXMaterial(file.getString("Abilities." + getName() + ".Activation-Material")).get().parseMaterial();
	}

	Material activationMaterial = XMaterial.LAPIS_LAZULI.parseMaterial();

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
		return true;
	}

	@Override
	public boolean execute(Player p, PlayerData data, Event event) {
        if (data.hasCooldown(p, "Enchanting"))
		{
			return false;
		}
		if (((PlayerInteractEntityEvent)event).getRightClicked().getType().equals(EntityType.PLAYER)) {
			data.setCooldown(p, "Enchanting", cooldown, true);
			// Get target player.
			Player targetPlayer = (Player)((PlayerInteractEntityEvent)event).getRightClicked();
			// Check if the target player is holding a valid item.
			if (targetPlayer.getInventory().getItemInMainHand().getType() == Material.AIR) {
				p.sendMessage((String)Messages.loadedMsgs.get("Abilities.Enchanting-Deny"));
				return false;
			}
			Kitbattle.getInstance().sendUseAbility(p, data);
			// Get target player's mainhand item.
			ItemStack targetItems = targetPlayer.getInventory().getItemInMainHand();
			// Check if the enchantment list is empty. If so, remove all enchantments on the item.
			if (!targetItems.getEnchantments().isEmpty()) {
				// Iterate over all enchantments.
				for (Enchantment enchantmentToRemove : targetItems.getEnchantments().keySet()) {
					targetItems.getItemMeta().removeEnchant(enchantmentToRemove);
				}
			}
			// Get item's metadata.
			ItemMeta targetItemMeta = targetPlayer.getInventory().getItemInMainHand().getItemMeta();
			// Add a new random enchantment to the given metadata.
			targetItemMeta = MoreUtils.addRandomEnch(targetItemMeta);
			// Set the new metadata to the target item.
			targetItems.setItemMeta(targetItemMeta);
			// Play the enchanting sound effect.
			p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.25F, 1.0F);
			targetPlayer.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.25F, 1.0F);
			// Send message to both of the players.
			p.sendMessage((String)Messages.loadedMsgs.get("Abilities.Enchanting-Activate").replace("%player%", targetPlayer.getName()));
			targetPlayer.sendMessage((String)Messages.loadedMsgs.get("Abilities.Enchanting-Apply").replace("%player%", p.getName()));
			return true;
		}
		return false;
	}

}
