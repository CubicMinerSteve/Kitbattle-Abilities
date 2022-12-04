package me.cubicminer.kbabilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.cubicminer.kbabilities.manager.ExtendedAbilityManager;
import me.cubicminer.kbabilities.manager.ExtendedMessageManager;

public class KitBattleAbilities extends JavaPlugin{

	@Override
	public void onEnable()
	{
		try {
			ExtendedAbilityManager.loadAbilities();
			ExtendedMessageManager.loadMessages();
			if (ExtendedMessageManager.hasMessageInitialized == false) {
				Exception onLoadException = new Exception();
				throw onLoadException;
			}
		} catch (Exception exception) {
			ExtendedAbilityManager.initializeAbilities();
			ExtendedMessageManager.initializeMessages();
			ExtendedAbilityManager.loadAbilities();
			ExtendedMessageManager.loadMessages();
		}
		Bukkit.getConsoleSender().sendMessage("[KitbattleAbilities] Successfully enabled KitBattle Abilities Plugin!");
	}

}