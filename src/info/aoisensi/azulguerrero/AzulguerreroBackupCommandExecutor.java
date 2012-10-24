package info.aoisensi.azulguerrero;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AzulguerreroBackupCommandExecutor implements CommandExecutor {
	
	//private Azulguerrero plugin;
	
	public AzulguerreroBackupCommandExecutor(Azulguerrero plugin) {
	//	this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(sender instanceof Player){
			String playername;
			if(sender instanceof Player){
				playername = "サーバー管理者";
			} else {
				playername = ((Player)sender).getName();
			}
			Player player = (Player)sender;
			if(player.isOp()){
				if(player != null){
					playername = player.getName();
				}
				AzulguerreroMethod.sendMessageAllPlayer(ChatColor.GRAY + playername + "がbackupコマンドを実行しました");
				File dir = new File(Bukkit.getWorldContainer(), "backup");
				System.out.println(dir.getPath());
				if(!dir.exists()){
					dir.mkdir();
					player.sendMessage(ChatColor.GRAY + "backupフォルダが見つからなかったので作成しました");
				}
				dir = new File(dir, (AzulguerreroMethod.nowString()));
				dir.mkdir();
				AzulguerreroMethod.sendMessageAllPlayer(ChatColor.GRAY + dir.toString() + "ディレクトリにバックアップを取ります");
				for(World world:Bukkit.getWorlds()){
					world.save();
					System.out.println(world.getName());
					try {
						File dirs = new File(dir, world.getName());
						dirs.mkdir();
						AzulguerreroMethod.copyDirectry(world.getWorldFolder(), dirs);
						AzulguerreroMethod.sendMessageAllPlayer(ChatColor.GRAY + world.getName() + "のバックアップが完了しました");
					} catch (IOException e) {
						AzulguerreroMethod.sendMessageAllPlayer(ChatColor.GRAY + world.getName() + "のバックアップに失敗しました");
						player.sendMessage(ChatColor.RED + e.getMessage());
					}
				}
				return true;
			}
		}
		return false;
	}
}
