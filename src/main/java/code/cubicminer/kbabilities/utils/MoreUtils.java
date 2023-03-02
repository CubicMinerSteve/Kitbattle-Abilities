package code.cubicminer.kbabilities.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import me.wazup.kitbattle.utils.Utils;

public class MoreUtils {

	public static ArrayList<Location> getIceCage(Location pLocation) {
		ArrayList<Location> arrayList = new ArrayList<Location>();
		// The following items are 'y-level = -1' blocks. Used as floor.
		arrayList.add(pLocation.clone().add(0.0D, -1.0D, 0.0D));
		// The following items are 'y-level = 2' blocks. Used as ceiling.
		arrayList.add(pLocation.clone().add(0.0D, 2.0D, 0.0D));
		// The following items are 'y-level = 0' blocks.
		arrayList.add(pLocation.clone().add(1.0D, 0.0D, 1.0D));
		arrayList.add(pLocation.clone().add(1.0D, 0.0D, 0.0D));
		arrayList.add(pLocation.clone().add(1.0D, 0.0D, -1.0D));
		arrayList.add(pLocation.clone().add(0.0D, 0.0D, 1.0D));
		arrayList.add(pLocation.clone().add(0.0D, 0.0D, -1.0D));
		arrayList.add(pLocation.clone().add(-1.0D, 0.0D, 1.0D));
		arrayList.add(pLocation.clone().add(-1.0D, 0.0D, 0.0D));
		arrayList.add(pLocation.clone().add(-1.0D, 0.0D, -1.0D));
		arrayList.add(pLocation.clone().add(0.0D, 0.0D, 0.0D));
		// The following items are 'y-level = 1' blocks.
		arrayList.add(pLocation.clone().add(0.0D, 1.0D, 0.0D));
		return arrayList;
	}

	public static ItemMeta addRandomEnch(ItemMeta pItemMeta) {
		ItemMeta itemMeta = pItemMeta;
		int enchantmentID = Utils.random.nextInt(9) + 1;
		int levelID = Utils.random.nextInt(9) + 1;
		if (enchantmentID == 1 || enchantmentID == 2) {
			if (levelID <= 6) {
				// Sharpness Enchantment I | P = 0.12
				itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			} else if (levelID > 6 && levelID <= 9) {
				// Sharpness Enchantment II | P = 0.06
				itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
			} else {
				// Sharpness Enchantment III | P = 0.02
				itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
			}
		} else if (enchantmentID == 3 || enchantmentID == 4) {
			if (levelID <= 7) {
				// Power Enchantment I | P = 0.14
				itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			} else {
				// Power Enchantment II | P = 0.06
				itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 2, true);
			}
		} else if (enchantmentID == 5 && enchantmentID == 6) {
			if (levelID <= 7) {
				// Protection Enchantment I | P = 0.14
				itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
			} else {
				// Protection Enchantment II | P = 0.06
				itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
			}
		} else if (enchantmentID == 7) {
			if (levelID <= 5) {
				// Knockback Enchantment I | P = 0.05
				itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
			} else {
				// Punch Enchantment I | P = 0.05
				itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);
			}
		} else if (enchantmentID == 8) {
			if (levelID <= 5) {
				// Flame Enchantment | P = 0.05
				itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
			} else {
				// Fire Aspect Enchantment I | P = 0.05
				itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
			}
		} else if (enchantmentID == 9){
			if (levelID <= 6) {
				// Thorns Enchantment I | P = 0.06
				itemMeta.addEnchant(Enchantment.THORNS, 1, true);
			} else if (levelID > 6 && levelID <= 9) {
				// Thorns Enchantment II | P = 0.03
				itemMeta.addEnchant(Enchantment.THORNS, 2, true);
			} else {
				// Thorns Enchantment III | P = 0.01
				itemMeta.addEnchant(Enchantment.THORNS, 3, true);
			}
		} else {
			if (levelID <= 5) {
				// Infinity Enchantment | P = 0.05
				itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
			} else {
				// Multishot Enchantment | P = 0.05
				itemMeta.addEnchant(Enchantment.MULTISHOT, 1, true);
			}
		}
		return itemMeta;
	}

}
