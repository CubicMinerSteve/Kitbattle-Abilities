package code.cubicminer.kbabilities;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import code.cubicminer.kbabilities.manager.Abilities;
import code.cubicminer.kbabilities.manager.FileReader;
import code.cubicminer.kbabilities.manager.Messages;

public class KitBattleAbilities extends JavaPlugin {

	@Override
	public void onEnable()
	{
		Messages.builtInMessages();
		FileReader.checkDataFolder();
		try {
			Abilities.loadAbilities();
			Messages.loadMessages();
			if (Messages.hasMsgsInited == false) {
				Exception onLoadException = new Exception();
				throw onLoadException;
			}
		} catch (Exception exception) {
			FileReader.releaseResourceFile();
			Abilities.loadAbilities();
			Messages.loadMessages();
		}
		Bukkit.getConsoleSender().sendMessage(Messages.builtInMsgs.get("Plugin-Enabled-Success"));
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kbareload")) {
			if (sender.hasPermission("kbabilities.reload")) {
				try {
					Abilities.loadAbilities();
					Messages.loadMessages();
					if (Messages.hasMsgsInited == false) {
						Exception onLoadException = new Exception();
						throw onLoadException;
					}
				} catch (Exception exception) {
					FileReader.releaseResourceFile();
					Abilities.loadAbilities();
					Messages.loadMessages();
				}
				sender.sendMessage(Messages.loadedMsgs.get("Settings.Plugin-Reloaded"));
				return true;
	  		}
			sender.sendMessage(Messages.loadedMsgs.get("Settings.No-Permission"));
			return true;
		}
	    return false;
  	}
}