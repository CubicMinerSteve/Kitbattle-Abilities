package me.cubicminer.kbabilities.manager;

import org.bukkit.configuration.file.FileConfiguration;

import me.cubicminer.kbabilities.list.Evoker;
import me.cubicminer.kbabilities.list.Freeze;
import me.cubicminer.kbabilities.list.Weakness;
import me.cubicminer.kbabilities.list.Whirlwind;
import me.cubicminer.kbabilities.list.Wither;
import me.wazup.kitbattle.Kitbattle;
import me.wazup.kitbattle.abilities.AbilityManager;

public class PluginManager {

    public static PluginManager instance = new PluginManager();

    public static void loadAbilities(){
        //To register your own abilities, you have to use the method below.
        AbilityManager.getInstance().registerAbility(new Evoker());
        AbilityManager.getInstance().registerAbility(new Freeze());
        AbilityManager.getInstance().registerAbility(new Weakness());
        AbilityManager.getInstance().registerAbility(new Whirlwind());
        AbilityManager.getInstance().registerAbility(new Wither());
        //If your custom abilities read values from the abilities.yml file, then use
        AbilityManager.getInstance().loadAbilitiesConfig();
        //After registering all of your custom abilities, you should call the following method.
        AbilityManager.getInstance().updateKitAbilities();
    }

    public static void initializeAbilities() {
        FileConfiguration fileConfiguration = Kitbattle.getInstance().fileManager.getConfig("abilities.yml");
        // Set Evoker Ability Configurations.
        fileConfiguration.set("Abilities.Evoker.Cooldown", 5);
        fileConfiguration.set("Abilities.Evoker.Radius", 3);
        // Set Freeze Ability Configurations.
        fileConfiguration.set("Abilities.Freeze.Cooldown", 45);
        fileConfiguration.set("Abilities.Freeze.IceCube-Lasts-For", 5);
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

    public static void initializeMessages() {
        FileConfiguration fileConfiguration = Kitbattle.getInstance().fileManager.getConfig("messages.yml");

        fileConfiguration.set("Messages.Freeze-No-Space", "There is not enough space above the target!");
        fileConfiguration.set("Messages.Whirlwind-Strike", "Player %player% summoned a whirlwind and you are taken off from the ground!");

        Kitbattle.getInstance().fileManager.saveConfig("messages.yml");
    }

}
