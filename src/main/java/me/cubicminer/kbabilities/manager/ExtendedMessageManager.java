package me.cubicminer.kbabilities.manager;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

import me.wazup.kitbattle.Kitbattle;

public class ExtendedMessageManager {

	public static ExtendedMessageManager instance = new ExtendedMessageManager();

	static FileConfiguration fileConfiguration = Kitbattle.getInstance().fileManager.getConfig("messages.yml");

	static HashMap<String, String> loadedMessages = new HashMap<>();

	public static boolean hasMessageInitialized = true;

	public static void initializeMessages() {
		// The following code is for setting messages.
		fileConfiguration.set("Messages.Blindness-Activate", "&cYou are blinded by &b%player%'s &cink splash!");
		fileConfiguration.set("Messages.Conduit-Effect-Apply", "You have killed a player in the &bwater&7, so you received &abonus effects&7!");
		fileConfiguration.set("Messages.Enchanting-Activate", "Successfully randomized the enchantments on &b%player%'s &7item!");
		fileConfiguration.set("Messages.Enchanting-Apply", "&cThe enchantments on your mainhand item has been randomized by &b%player%&7!");
		fileConfiguration.set("Messages.Enchanting-Deny", "&cYour target player is holding nothing in mainhand!");
		fileConfiguration.set("Messages.Freeze-No-Space", "&cThere is no enough space above the target!");
		fileConfiguration.set("Messages.Visualization-Activate", "&cA visualization spell has been cast on you by &b%player%&c!");
		fileConfiguration.set("Messages.Visualization-Deactivate", "Spell deactivated, you are now &dinvisible &7again.");
		fileConfiguration.set("Messages.Whirlwind-Strike", "&cYou are taken off from the ground by &b%player%'s &cwhirlwind!");
		// Save configuration file.
		Kitbattle.getInstance().fileManager.saveConfig("messages.yml");
	}

	public static boolean loadMessages() {
		FileConfiguration fileConfiguration = Kitbattle.getInstance().fileManager.getConfig("messages.yml");
		loadedMessages.put("Messages.Blindness-Activate", fileConfiguration.getString("Messages.Blindness-Activate"));
		loadedMessages.put("Messages.Conduit-Effect-Apply", fileConfiguration.getString("Messages.Conduit-Effect-Apply"));
		loadedMessages.put("Messages.Enchanting-Activate", fileConfiguration.getString("Enchanting-Activate"));
		loadedMessages.put("Messages.Enchanting-Apply", fileConfiguration.getString("Messages.Enchanting-Apply"));
		loadedMessages.put("Messages.Enchanting-Deny", fileConfiguration.getString("Messages.Enchanting-Deny"));
		loadedMessages.put("Messages.Freeze-No-Space", fileConfiguration.getString("Messages.Freeze-No-Space"));
		loadedMessages.put("Messages.Visualization-Activate", fileConfiguration.getString("Messages.Visualization-Activate"));
		loadedMessages.put("Messages.Visualization-Deactivate", fileConfiguration.getString("Messages.Visualization-Activate"));
		loadedMessages.put("Messages.Whirlwind-Strike", fileConfiguration.getString("Messages.Whirlwind-Strike"));
		for (String Key : loadedMessages.keySet()) {
			if (loadedMessages.get(Key) == null )  hasMessageInitialized = false;
		}
		return true;
	}

}
