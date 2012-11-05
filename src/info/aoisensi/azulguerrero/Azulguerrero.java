package info.aoisensi.azulguerrero;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Azulguerrero extends JavaPlugin implements Listener {
	Logger log;
	public static File dataFolder;
	@Override
	public void onEnable(){
		
		getCommand("backup").setExecutor(new AzulguerreroBackupCommandExecutor(this));
		
		dataFolder = this.getDataFolder();
		
		new AzulguerreroListener(this);
		
		log = this.getLogger();
		log.info("Azulguerrero has been enabled!!");
	}
	@Override
	public void onDisable(){
		log = this.getLogger();
		log.info("Azulgerrero has been disabled...");
	}
	

	
	
	
	
}
