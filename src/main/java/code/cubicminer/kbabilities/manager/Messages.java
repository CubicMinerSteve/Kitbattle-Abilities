package code.cubicminer.kbabilities.manager;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

	public static Messages msgInstance = new Messages();

	public static FileConfiguration msgConfig = FileReader.getConfigurationFile("messages.yml");

	public static String builtInPrefix = "&3[&bKitBattle Abilities&3]&7 ";
	public static String loadedPrefix = msgConfig.getString("prefix");

	public static HashMap<String, String> builtInMsgs = new HashMap<>();
	public static HashMap<String, String> loadedMsgs = new HashMap<>();

	public static boolean hasMsgsInited = true;

	public static void builtInMessages() {
		// Built in messages, used to report critical info when file system is broken.
		builtInMsgs.put("File-Create-Failed", fullBuiltInPrefix("&cFailed to create file "));
		builtInMsgs.put("File-Save-Failed", fullBuiltInPrefix("&cFailed to save file "));
		builtInMsgs.put("File-Write-Failed", fullBuiltInPrefix("&cFailed to write into file "));
		builtInMsgs.put("Plugin-Enabled-Success", fullBuiltInPrefix("&aSuccessfully enabled &rKitBattle Abilities Plugin!"));
	}

	public static void loadMessages() {
		loadedMsgs.put("Abilities.Blindness-Activate", fullLoadedPrefix("Messages.Abilities.Blindness-Activate"));
		loadedMsgs.put("Abilities.Conduit-Effect-Apply", fullLoadedPrefix("Messages.Abilities.Conduit-Effect-Apply"));
		loadedMsgs.put("Abilities.Enchanting-Activate", fullLoadedPrefix("Messages.Abilities.Enchanting-Activate"));
		loadedMsgs.put("Abilities.Enchanting-Apply", fullLoadedPrefix("Messages.Abilities.Enchanting-Apply"));
		loadedMsgs.put("Abilities.Enchanting-Deny", fullLoadedPrefix("Messages.Abilities.Enchanting-Deny"));
		loadedMsgs.put("Abilities.Freeze-No-Space", fullLoadedPrefix("Messages.Abilities.Freeze-No-Space"));
		loadedMsgs.put("Abilities.Visualization-Activate", fullLoadedPrefix("Messages.Abilities.Visualization-Activate"));
		loadedMsgs.put("Abilities.Visualization-Deactivate", fullLoadedPrefix("Messages.Abilities.Visualization-Deactivate"));
		loadedMsgs.put("Abilities.Whirlwind-Strike", fullLoadedPrefix("Messages.Abilities.Whirlwind-Strike"));

		loadedMsgs.put("Settings.Plugin-Reloaded", ChatColor.translateAlternateColorCodes('&', builtInPrefix + msgConfig.getString("Messages.Settings.Plugin-Reloaded")));
		loadedMsgs.put("Settings.No-Permission", ChatColor.translateAlternateColorCodes('&', builtInPrefix + msgConfig.getString("Messages.Settings.No-Permission")));
		for (String Key : loadedMsgs.keySet()) {
			if (loadedMsgs.get(Key) == null )  hasMsgsInited = false;
		}
	}

	public static String fullBuiltInPrefix(String str) {
		return ChatColor.translateAlternateColorCodes('&', builtInPrefix + str);
	}

	public static String fullLoadedPrefix(String str) {
		return ChatColor.translateAlternateColorCodes('&', loadedPrefix + msgConfig.getString(str)); 
	}

	@Deprecated // No longer uses this method.
	public static void writeMessages() {
		// The following code is for setting messages.
		msgConfig.set("Messages.Settings.Plugin-Reloaded", "&rConfig successfully reloaded!");
		msgConfig.set("Messages.Settings.No-Permission", "&cYou don't have permission to do this!");

		msgConfig.set("Messages.Abilities.Blindness-Activate", "&cYou are blinded by &b%player%'s &cink splash!");
		msgConfig.set("Messages.Abilities.Conduit-Effect-Apply", "You have killed a player in the &bwater&7, so you received &abonus effects&7!");
		msgConfig.set("Messages.Abilities.Enchanting-Activate", "Successfully randomized the enchantments on &b%player%'s &7item!");
		msgConfig.set("Messages.Abilities.Enchanting-Apply", "&cThe enchantments on your mainhand item has been randomized by &b%player%&7!");
		msgConfig.set("Messages.Abilities.Enchanting-Deny", "&cYour target player is holding nothing in mainhand!");
		msgConfig.set("Messages.Abilities.Freeze-No-Space", "&cThere is no enough space above the target!");
		msgConfig.set("Messages.Abilities.Visualization-Activate", "&cA visualization spell has been cast on you by &b%player%&c!");
		msgConfig.set("Messages.Abilities.Visualization-Deactivate", "Spell deactivated, you are now &dinvisible &7again.");
		msgConfig.set("Messages.Abilities.Whirlwind-Strike", "&cYou are taken off from the ground by &b%player%'s &cwhirlwind!");
		// Save configuration file.
		FileReader.saveConfigurationFile("messages.yml");
	}
}

/*
	
*/