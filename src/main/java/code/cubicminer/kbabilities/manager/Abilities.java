package code.cubicminer.kbabilities.manager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import code.cubicminer.kbabilities.list.Ascension;
import code.cubicminer.kbabilities.list.Berserker;
import code.cubicminer.kbabilities.list.Blindness;
import code.cubicminer.kbabilities.list.Conduit;
import code.cubicminer.kbabilities.list.Enchanting;
import code.cubicminer.kbabilities.list.Evocation;
import code.cubicminer.kbabilities.list.Explosive;
import code.cubicminer.kbabilities.list.Freeze;
import code.cubicminer.kbabilities.list.Resistance;
import code.cubicminer.kbabilities.list.Visualization;
import code.cubicminer.kbabilities.list.Weakness;
import code.cubicminer.kbabilities.list.Whirlwind;
import code.cubicminer.kbabilities.list.Wither;
import code.cubicminer.kbabilities.utils.Listeners;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.abilities.AbilityManager;

public class Abilities {

	public static Abilities instance = new Abilities();

	public static FileConfiguration abiConfig = FileReader.getConfigurationFile("abilities.yml");

	public static void loadAbilities(){
		// To register your own abilities, you have to use the method below.
		AbilityManager.getInstance().registerAbility(new Ascension());
		AbilityManager.getInstance().registerAbility(new Berserker());
		AbilityManager.getInstance().registerAbility(new Blindness());
		AbilityManager.getInstance().registerAbility(new Conduit());
		AbilityManager.getInstance().registerAbility(new Enchanting());
		AbilityManager.getInstance().registerAbility(new Evocation());
		AbilityManager.getInstance().registerAbility(new Explosive());
		AbilityManager.getInstance().registerAbility(new Freeze());
		AbilityManager.getInstance().registerAbility(new Resistance());
		AbilityManager.getInstance().registerAbility(new Visualization());
		AbilityManager.getInstance().registerAbility(new Weakness());
		AbilityManager.getInstance().registerAbility(new Whirlwind());
		AbilityManager.getInstance().registerAbility(new Wither());
		// If your custom abilities read values from the abilities.yml file, then use
		AbilityManager.getInstance().loadAbilitiesConfig();
		// After registering all of your custom abilities, you should call the following method.
		AbilityManager.getInstance().updateKitAbilities();
		Bukkit.getPluginManager().registerEvents(new Listeners(Kitbattle.getInstance()), Kitbattle.getInstance());
	}

	@Deprecated // No longer uses this method.
	public static void initAbilities() {
		// Set Ascension Ability Configurations.
		abiConfig.set("Abilities.Ascension.Cooldown", 30);
		abiConfig.set("Abilities.Ascension.Levitation-Lasts-For", 8);
		abiConfig.set("Abilities.Ascension.Slow-Falling-Lasts-For", 8);
		// Set Berserker Ability Configurations.
		abiConfig.set("Abilities.Berserker.Cooldown", 40);
		abiConfig.set("Abilities.Berserker.Berserk-Lasts-For", 7);
		// Set Blindness Ability Configurations.
		abiConfig.set("Abilities.Blindness.Cooldown", 40);
		abiConfig.set("Abilities.Blindness.Range", 8);
		abiConfig.set("Abilities.Blindness.Blindness-Lasts-For", 10);
		// Set Conduit Ability Configurations.
		abiConfig.set("Abilities.Conduit.Regeneration-Lasts-For", 6);
		abiConfig.set("Abilities.Conduit.Absorption-Lasts-For", 6);
		// Set Enchanting Ability Configurations.
		abiConfig.set("Abilities.Enchanting.Cooldown", 60);
		// Set Evocation Ability Configurations.
		abiConfig.set("Abilities.Evocation.Cooldown", 5);
		abiConfig.set("Abilities.Evocation.Radius", 3);
		// Set Explosive Ability Configurations.
		abiConfig.set("Abilities.Explosive.Cooldown", 5);
		abiConfig.set("Abilities.Explosive.Explosion-Power", 4);
		// Set Freeze Ability Configurations.
		abiConfig.set("Abilities.Freeze.Cooldown", 45);
		abiConfig.set("Abilities.Freeze.IceCube-Lasts-For", 5);
		// Set Resistance Ability Configurations.
		abiConfig.set("Abilities.Resistance.Resistance-Chance", "30%");
		abiConfig.set("Abilities.Resistance.Trigger-Health-Percentage", "25%");
		abiConfig.set("Abilities.Resistance.Resistance-Lasts-For", 2);
		abiConfig.set("Abilities.Resistance.Resistance-Level", 2);
		// Set Visualization Ability Configurations.
		abiConfig.set("Abilities.Visualization.Range", 8);
		abiConfig.set("Abilities.Visualization.Spell-Lasts-For", 15);
		// Set Weakness Ability Configurations.
		abiConfig.set("Abilities.Weakness.Weakness-Chance", "20%");
		abiConfig.set("Abilities.Weakness.Weakness-Lasts-For", 10);
		abiConfig.set("Abilities.Weakness.Weakness-Level", 1);
		// Set Whirlwind Ability Configurations.
		abiConfig.set("Abilities.Whirlwind.Cooldown", 45);
		abiConfig.set("Abilities.Whirlwind.Whirlwind-Range", 6);
		abiConfig.set("Abilities.Whirlwind.Nausea-Lasts-For", 15);
		// Set Wither Ability Configurations.
		abiConfig.set("Abilities.Wither.Wither-Chance", "20%");
		abiConfig.set("Abilities.Wither.Wither-Lasts-For", 10);
		abiConfig.set("Abilities.Wither.Wither-Level", 2);
		FileReader.saveConfigurationFile("abilities.yml");
	}
}