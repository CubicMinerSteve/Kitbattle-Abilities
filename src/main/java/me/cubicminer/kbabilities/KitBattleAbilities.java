package me.cubicminer.kbabilities;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.cubicminer.kbabilities.manager.PluginManager;

public class KitBattleAbilities extends JavaPlugin implements Listener{

    @Override
    public void onEnable()
    {
        try {
            PluginManager.loadAbilities();
        } catch (Exception e) {
            PluginManager.initializeAbilities();
            PluginManager.initializeMessages();
            PluginManager.loadAbilities();
        }
        System.out.println("Successfully enabled KitBattle Abilities Plugin!");
    }

}