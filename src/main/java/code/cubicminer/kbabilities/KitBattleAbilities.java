package code.cubicminer.kbabilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import code.cubicminer.kbabilities.manager.Abilities;
import code.cubicminer.kbabilities.manager.Configurations;
import code.cubicminer.kbabilities.manager.Messages;

public class KitBattleAbilities extends JavaPlugin {

	@Override
	public void onEnable()
	{
		Configurations.checkDataFolder();
		try {
			Abilities.loadAbilities();
			Messages.loadMessages();
			if (Messages.hasMessageInitialized == false) {
				Exception onLoadException = new Exception();
				throw onLoadException;
			}
		} catch (Exception exception) {
			Configurations.releaseResourceFiles();
			// Initialize new messages.
			// Abilities.initializeAbilities();
			// Messages.initializeMessages();
			// Load new messages.
			Abilities.loadAbilities();
			Messages.loadMessages();
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "KitBattle Abilities" + ChatColor.DARK_AQUA + "] " + ChatColor.GREEN + "Successfully enabled" + ChatColor.RESET + " KitBattle Abilities Plugin!");
	}

}