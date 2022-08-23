package me.cubicminer.kbabilities.manager;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageManagerX {

    public static HashMap<String, String> messages;

    public static String prefix;

    public static void loadAllMessages() {
        FileConfiguration fileConfiguration = ConfigManagerX.GetConfig("messages.yml");
        prefix = getMessageString(fileConfiguration, "prefix");
        messages = new HashMap<>();
        for (String str : fileConfiguration.getConfigurationSection("Messages").getKeys(false)) {
            messages.put(str, prefix + getMessageString(fileConfiguration, "Messages." + str));
        }
    }

    private static String getMessageString(FileConfiguration pFileConfiguration, String pString) {
        return ChatColor.translateAlternateColorCodes('&', pFileConfiguration.getString(pString));
    }

}
