package code.cubicminer.kbabilities.manager;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

	public static Messages instance = new Messages();

	public static FileConfiguration msgConfiguration = Configurations.getConfigurationFile("messages.yml");

	public static String prefix = msgConfiguration.getString("prefix");

	public static HashMap<String, String> loadedMessages = new HashMap<>();

	public static boolean hasMessageInitialized = true;

	public static void initializeMessages() {
		// The following code is for setting messages.
		msgConfiguration.set("Messages.Blindness-Activate", "&cYou are blinded by &b%player%'s &cink splash!");
		msgConfiguration.set("Messages.Conduit-Effect-Apply", "You have killed a player in the &bwater&7, so you received &abonus effects&7!");
		msgConfiguration.set("Messages.Enchanting-Activate", "Successfully randomized the enchantments on &b%player%'s &7item!");
		msgConfiguration.set("Messages.Enchanting-Apply", "&cThe enchantments on your mainhand item has been randomized by &b%player%&7!");
		msgConfiguration.set("Messages.Enchanting-Deny", "&cYour target player is holding nothing in mainhand!");
		msgConfiguration.set("Messages.Freeze-No-Space", "&cThere is no enough space above the target!");
		msgConfiguration.set("Messages.Visualization-Activate", "&cA visualization spell has been cast on you by &b%player%&c!");
		msgConfiguration.set("Messages.Visualization-Deactivate", "Spell deactivated, you are now &dinvisible &7again.");
		msgConfiguration.set("Messages.Whirlwind-Strike", "&cYou are taken off from the ground by &b%player%'s &cwhirlwind!");
		// Save configuration file.
		Configurations.saveConfigurationFile("messages.yml");
	}

	public static void loadMessages() {
		loadedMessages.put("Blindness-Activate", fullPrefixString("Messages.Blindness-Activate"));
		loadedMessages.put("Conduit-Effect-Apply", fullPrefixString("Messages.Conduit-Effect-Apply"));
		loadedMessages.put("Enchanting-Activate", fullPrefixString("Enchanting-Activate"));
		loadedMessages.put("Enchanting-Apply", fullPrefixString("Messages.Enchanting-Apply"));
		loadedMessages.put("Enchanting-Deny", fullPrefixString("Messages.Enchanting-Deny"));
		loadedMessages.put("Freeze-No-Space", fullPrefixString("Messages.Freeze-No-Space"));
		loadedMessages.put("Visualization-Activate", fullPrefixString("Messages.Visualization-Activate"));
		loadedMessages.put("Visualization-Deactivate", fullPrefixString("Messages.Visualization-Activate"));
		loadedMessages.put("Whirlwind-Strike", fullPrefixString("Messages.Whirlwind-Strike"));
		for (String Key : loadedMessages.keySet()) {
			if (loadedMessages.get(Key) == null )  hasMessageInitialized = false;
		}
	}

	public static String fullPrefixString(String str) {
		return ChatColor.translateAlternateColorCodes('&', prefix + msgConfiguration.getString(str)); 
	}

}
