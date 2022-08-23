package me.cubicminer.kbabilities.manager;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import me.cubicminer.kbabilities.list.Evoker;
import me.cubicminer.kbabilities.list.Freeze;
import me.cubicminer.kbabilities.list.Weakness;
import me.cubicminer.kbabilities.list.Whirlwind;
import me.cubicminer.kbabilities.list.Wither;
import me.wazup.kitbattle.abilities.Ability;
import me.wazup.kitbattle.abilities.AbilityManager;

public class AbilityManagerX {

    public static AbilityManagerX instance = new AbilityManagerX();

    public static HashMap<String, Ability> appendedAbilities = new HashMap<>();

    private static AbilityManagerX getInstance() {
        return instance;
    }

    public static void loadAbilities(){
        //To register your own abilities, you have to use the method below.
        AbilityManager.getInstance().registerAbility(new Evoker());
        AbilityManager.getInstance().registerAbility(new Freeze());
        AbilityManager.getInstance().registerAbility(new Weakness());
        AbilityManager.getInstance().registerAbility(new Whirlwind());
        AbilityManager.getInstance().registerAbility(new Wither());
        //After registering all of your custom abilities, you should call the following method.
        AbilityManager.getInstance().updateKitAbilities();

        AbilityManagerX.getInstance().registerAbility(new Evoker());
        AbilityManagerX.getInstance().registerAbility(new Freeze());
        AbilityManagerX.getInstance().registerAbility(new Weakness());
        AbilityManagerX.getInstance().registerAbility(new Whirlwind());
        AbilityManagerX.getInstance().registerAbility(new Wither());
        //If your custom abilities read values from the abilities.yml file, then use
        AbilityManagerX.getInstance().loadAllAbilitiesConfig();
    }

    public void registerAbility(Ability ability) {
        AbilityManagerX.appendedAbilities.put(ability.getName().toLowerCase(), ability);
    }

    // This method is temporily not used.
    public void loadAbilityConfig(Ability ability) {
        ability.load(ConfigManagerX.GetConfig("abilities.yml"));
    }

    // Load all ability configs.
    public void loadAllAbilitiesConfig() {
        FileConfiguration fileConfiguration = ConfigManagerX.GetConfig("abilities.yml");
        for (Ability ability : AbilityManagerX.appendedAbilities.values()) {
            ability.load(fileConfiguration);
        }
    }

}
