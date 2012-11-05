package info.aoisensi.azulguerrero;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.xml.sax.SAXException;

public class AzulguerreroListener implements Listener {
	
	public AzulguerreroListener(Azulguerrero plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]自作Bukkitプラグイン[Azulguerrero]を導入しました！");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]現在の機能は/backupコマンドのみです(op権所持者しか使えません)");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]op所持者は定期的に(数時間に一回)backupコマンドを");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]実行してくれると嬉しいです！(save-allコマンドは必要ありません)");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]こんな機能あればいいなというのがあったら主に言ってください！");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS](実は他にも機能があるかも…)");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]サーバーwikiを開きました！全く編集してません！URLはこちら！");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS] " + ChatColor.GOLD + "http://mcs.aoisensi.info/");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]チェストロック機能を実装しました！");
		player.sendMessage(ChatColor.DARK_AQUA + "[NEWS]チェストに看板を貼り付けることでロックをかけることができます！");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) throws IOException, SAXException{
		if(e.getMessage().charAt(0) == '$'){
			e.setMessage(e.getMessage() + "\0n" + AzulguerreroIME.toJapanese(e.getMessage().substring(1)));
		}else{
			e.setMessage(AzulguerreroSlang.Replace(e.getMessage()));
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		switch(e.getAction()) {
		case RIGHT_CLICK_BLOCK:
			switch(e.getClickedBlock().getType()){
			case CHEST:
				//チェストロック機能
				e.setCancelled(AzulguerreroChestLock.TryOpenChest(e));
				break;
			default:
				break;
			}
			break;
		case LEFT_CLICK_BLOCK:
			//e.getPlayer().sendMessage(e.getClickedBlock().getType().toString().replace('_', ' '));
			break;
		default:
			break;
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		switch(e.getBlock().getType()) {
		case CHEST:
			e.setCancelled(AzulguerreroChestLock.TryBreakChest(e));
			break;
		case WALL_SIGN:
			e.setCancelled(AzulguerreroChestLock.TryBreakSign(e));
			break;
		case SIGN_POST:
			e.setCancelled(AzulguerreroChestLock.TryBreakSign(e));
		default:
			break;
		}
	}
	
	//@EventHandler
	//public void onEntityExplode(EntityExplodeEvent e){
	//	for(Block block:e.blockList()){
	//		
	//	}
	//}
	
	
	
	
	
	
}
