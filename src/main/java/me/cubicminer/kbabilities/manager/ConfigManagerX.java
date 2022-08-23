package me.cubicminer.kbabilities.manager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.cubicminer.kbabilities.KitBattleAbilities;

public class ConfigManagerX {

    private static HashMap<String, FileConfiguration> ConfigurationFiles = new HashMap<>();

    private static KitBattleAbilities Plugin = KitBattleAbilities.getInstance();

    // This method must be invoked on the load of this plugin! Or the plugin WILL NOT BE ABLE to read the configuration files!
    public static void InitializeConfigManager() {
        RegisterConfig("messages.yml");
        RegisterConfig("abilities.yml");
        for (String str : ConfigurationFiles.keySet()) {
            ReloadConfig(str);
            ((FileConfiguration)ConfigurationFiles.get(str)).options().copyDefaults(true);
            SaveConfig(str);
        }
    }

    // Create a connection between a string parameter and a Yaml file then store it in the Hashmap above.
    private static void RegisterConfig(String pString) {
        ConfigurationFiles.put(pString, YamlConfiguration.loadConfiguration(new File(Plugin.getDataFolder(), pString)));
    }

    // Return a Yaml file, to use this method you should create a connection between the string parameter and the target file (register) by using the method above first.
    public static FileConfiguration GetConfig(String pString) {
        return ConfigurationFiles.get(pString);
    }

    // This method is temporarily not used.
    private static void ReloadConfig(String pString) {
        InputStream inputStream = Plugin.getResource(pString);
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(inputStreamReader);
            ((FileConfiguration)ConfigurationFiles.get(pString)).setDefaults((Configuration)yamlConfiguration);
            try {
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    // This method is temporarily not used.
    public static void SaveConfig(String pString) {
        try {
            ((FileConfiguration)ConfigurationFiles.get(pString)).save(new File(Plugin.getDataFolder(), pString));
        } catch (Exception exception) {
            System.out.println("Couldn't save file " + pString + "!");
        }
    }

}
