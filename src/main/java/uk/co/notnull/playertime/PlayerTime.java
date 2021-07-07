package uk.co.notnull.playertime;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerTime extends JavaPlugin implements Listener {
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("ptime").setExecutor(new Commands(this));
   }
}
