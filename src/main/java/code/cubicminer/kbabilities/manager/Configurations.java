package code.cubicminer.kbabilities.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteStreams;

public class Configurations {

    public static Plugin thisPlugin = Bukkit.getPluginManager().getPlugin("KitBattleAbilities");

    public static void checkDataFolder() {
        if (!thisPlugin.getDataFolder().exists()) {
            thisPlugin.getDataFolder().mkdir();
        }
    }

    public static FileConfiguration getConfigurationFile(String configName) {
		File configFile = new File(thisPlugin.getDataFolder(), configName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                configFile = new File(thisPlugin.getDataFolder(), configName);
            } catch (Exception exception) {
                exception.printStackTrace();
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "KitBattle Abilities" + ChatColor.DARK_AQUA + "] " + ChatColor.RED + "Unable to create file " + configName + "!");
            }
        }
        FileConfiguration fConfiguration = YamlConfiguration.loadConfiguration(configFile);
        return fConfiguration;
	}

    public static void saveConfigurationFile(String configName) {
		File configFile = new File(thisPlugin.getDataFolder(), configName);
        FileConfiguration fConfiguration = YamlConfiguration.loadConfiguration(configFile);
        try {
            fConfiguration.save(configFile);
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "KitBattle Abilities" + ChatColor.DARK_AQUA + "] " + ChatColor.RED + "Couldn't save " + configName + "!");
        } 
    }

    public static void releaseResourceFiles() {
        List<String> configList = new ArrayList<String>();
        configList.add("abilities.yml");
        configList.add("messages.yml");
        if (!thisPlugin.getDataFolder().exists()) {
            // Ensure the directory exists.
            thisPlugin.getDataFolder().mkdir();
        }
        for (String str : configList) {
            File configFile = new File(thisPlugin.getDataFolder(), str);
            if (!configFile.exists()) {
                try {
                    configFile.createNewFile();     
                } catch (Exception exception) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "KitBattle Abilities" + ChatColor.DARK_AQUA + "] " + ChatColor.RED + "Unable to create file " + str + "!");
                }
            }
            try {
                InputStream iStream = thisPlugin.getResource(str);
                OutputStream oStream = new FileOutputStream(configFile);
                // Copies file from plugin jar into newly created file.
                ByteStreams.copy(iStream, oStream);
                oStream.flush();
                oStream.close();
            } catch (Exception exception) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "KitBattle Abilities" + ChatColor.DARK_AQUA + "] " + ChatColor.RED + "Couldn't write into file " + configFile.getName() + "!");
            } 
        }      
    }

}
