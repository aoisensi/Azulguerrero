package info.aoisensi.azulguerrero;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class AzulguerreroPlayerListener implements Listener {
	
	public AzulguerreroPlayerListener(Azulguerrero plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]自作Bukkitプラグイン[Azulguerrero]を導入しました！");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]現在の機能は/backupコマンドのみです(op権所持者しか使えません)");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]op所持者は定期的に(数時間に一回)backupコマンドを");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]実行してくれると嬉しいです！(save-allコマンドは必要ありません)");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]こんな機能あればいいなというのがあったら主に言ってください！");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS](実は他にも機能があるかも…)");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]サーバーwikiを開きました！全く編集してません！URLはこちら！");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS] " + ChatColor.GOLD + "http://mcs.aoisensi.info/");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		event.setMessage(AzulguerreroSlang.Replace(event.getMessage()));
	}
	
}
