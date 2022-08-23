package me.cubicminer.kbabilities;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.cubicminer.kbabilities.manager.AbilityManagerX;
import me.cubicminer.kbabilities.manager.ConfigManagerX;
import me.cubicminer.kbabilities.manager.MessageManagerX;

public class KitBattleAbilities extends JavaPlugin implements Listener{

    public static KitBattleAbilities instance;

    public static KitBattleAbilities getInstance() {
        return instance;
    }

    public static void setInstance(KitBattleAbilities pInstance) {
        KitBattleAbilities.instance = pInstance;
    }

    @Override
    public void onEnable()
    {
        setInstance(this);
        ConfigManagerX.InitializeConfigManager();
        MessageManagerX.loadAllMessages();
        AbilityManagerX.loadAbilities();
        System.out.println("Successfully enabled KitBattle Abilities Plugin!");
    }

}