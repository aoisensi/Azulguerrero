package info.aoisensi.azulguerrero;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class AzulguerreroPlayerListener implements Listener {
	public AzulguerreroPlayerListener(Azulguerrero plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		player.sendMessage(ChatColor.YELLOW + "[NEWS]自作Bukkitプラグイン{Azulguerrero}を導入しました！");
		player.sendMessage(ChatColor.YELLOW + "[NEWS]現在の機能は/backupコマンドのみです(op権所持者しか使えません)");
		player.sendMessage(ChatColor.YELLOW + "[NEWS]op所持者は定期的に(数時間に一回)backupコマンドを実行してくれると嬉しいです！(save-allコマンドは必要ありません)");
	}
	
}
