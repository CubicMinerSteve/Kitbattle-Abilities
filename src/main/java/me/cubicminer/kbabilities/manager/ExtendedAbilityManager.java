package me.cubicminer.kbabilities.manager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import me.cubicminer.kbabilities.list.Berserk;
import me.cubicminer.kbabilities.list.Blindness;
import me.cubicminer.kbabilities.list.Conduit;
import me.cubicminer.kbabilities.list.Enchanting;
import me.cubicminer.kbabilities.list.Evocation;
import me.cubicminer.kbabilities.list.Explosive;
import me.cubicminer.kbabilities.list.Freeze;
import me.cubicminer.kbabilities.list.Resistance;
import me.cubicminer.kbabilities.list.Visualization;
import me.cubicminer.kbabilities.list.Weakness;
import me.cubicminer.kbabilities.list.Whirlwind;
import me.cubicminer.kbabilities.list.Wither;
import me.cubicminer.kbabilities.utils.ExtendedListeners;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.abilities.AbilityManager;

public class ExtendedAbilityManager {

	public static ExtendedAbilityManager instance = new ExtendedAbilityManager();

	public static void loadAbilities(){
		//To register your own abilities, you have to use the method below.
		AbilityManager.getInstance().registerAbility(new Berserk());
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
		//If your custom abilities read values from the abilities.yml file, then use
		AbilityManager.getInstance().loadAbilitiesConfig();
		//After registering all of your custom abilities, you should call the following method.
		AbilityManager.getInstance().updateKitAbilities();

		Bukkit.getPluginManager().registerEvents(new ExtendedListeners(Kitbattle.getInstance()), Kitbattle.getInstance());
	}

	public static void initializeAbilities() {
		FileConfiguration fileConfiguration = Kitbattle.getInstance().fileManager.getConfig("abilities.yml");

		// Set Berserk Ability Configurations.
		fileConfiguration.set("Abilities.Berserk.Cooldown", 45);
		fileConfiguration.set("Abilities.Berserk.Berserk-Lasts-For", 7);
		// Set Blindness Ability Configurations.
		fileConfiguration.set("Abilities.Blindness.Cooldown", 40);
		fileConfiguration.set("Abilities.Blindness.Range", 8);
		fileConfiguration.set("Abilities.Blindness.Blindness-Lasts-For", 10);
		// Set Conduit Ability Configurations.
		fileConfiguration.set("Abilities.Conduit.Regeneration-Lasts-For", 6);
		fileConfiguration.set("Abilities.Conduit.Absorption-Lasts-For", 6);
		// Set Enchanting Ability Configurations.
		fileConfiguration.set("Abilities.Enchanting.Cooldown", 60);
		// Set Evocation Ability Configurations.
		fileConfiguration.set("Abilities.Evocation.Cooldown", 5);
		fileConfiguration.set("Abilities.Evocation.Radius", 3);
		// Set Explosive Ability Configurations.
		fileConfiguration.set("Abilities.Explosive.Cooldown", 5);
		fileConfiguration.set("Abilities.Explosive.Explosion-Power", 4);
		// Set Freeze Ability Configurations.
		fileConfiguration.set("Abilities.Freeze.Cooldown", 45);
		fileConfiguration.set("Abilities.Freeze.IceCube-Lasts-For", 5);
		// Set Resistance Ability Configurations.
		fileConfiguration.set("Abilities.Resistance.Resistance-Chance", "30%");
		fileConfiguration.set("Abilities.Resistance.Trigger-Health-Percentage", "25%");
		fileConfiguration.set("Abilities.Resistance.Resistance-Lasts-For", 2);
		fileConfiguration.set("Abilities.Resistance.Resistance-Level", 2);
		// Set Visualization Ability Configurations.
		fileConfiguration.set("Abilities.Visualization.Range", 8);
		fileConfiguration.set("Abilities.Visualization.Spell-Lasts-For", 15);
		// Set Weakness Ability Configurations.
		fileConfiguration.set("Abilities.Weakness.Weakness-Chance", "100%");
		fileConfiguration.set("Abilities.Weakness.Weakness-Lasts-For", 10);
		fileConfiguration.set("Abilities.Weakness.Weakness-Level", 1);
		// Set Whirlwind Ability Configurations.
		fileConfiguration.set("Abilities.Whirlwind.Cooldown", 45);
		fileConfiguration.set("Abilities.Whirlwind.Whirlwind-Range", 6);
		fileConfiguration.set("Abilities.Whirlwind.Nausea-Lasts-For", 15);
		// Set Wither Ability Configurations.
		fileConfiguration.set("Abilities.Wither.Wither-Chance", "100%");
		fileConfiguration.set("Abilities.Wither.Wither-Lasts-For", 10);
		fileConfiguration.set("Abilities.Wither.Wither-Level", 2);
		Kitbattle.getInstance().fileManager.saveConfig("abilities.yml");
	}

}
