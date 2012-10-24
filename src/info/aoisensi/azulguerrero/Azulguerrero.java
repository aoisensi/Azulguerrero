package info.aoisensi.azulguerrero;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Azulguerrero extends JavaPlugin implements Listener {
	Logger log;
	public void onEnable(){
		
		getCommand("backup").setExecutor(new AzulguerreroBackupCommandExecutor(this));
		
		new AzulguerreroPlayerListener(this);
		
		log = this.getLogger();
		log.info("Azulguerrero has been enabled!!");
	}
	public void onDisable(){
		log = this.getLogger();
		log.info("Azulgerrero has been disabled...");
	}
	

	
	
	
	
}
