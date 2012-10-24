package info.aoisensi.azulguerrero;

import java.util.Calendar;
import java.io.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AzulguerreroMethod {
	public static void sendMessageAllPlayer(String Message){
		for(Player player:Bukkit.getOnlinePlayers()){
			player.sendMessage(Message);
		}
	}
	public static String nowString(){
		Calendar now = Calendar.getInstance();
		String result = "";
		result += String.valueOf(now.get(Calendar.YEAR));
		result += "-";
		result += String.valueOf(now.get(Calendar.MONTH)+1);
		result += "-";
		result += String.valueOf(now.get(Calendar.DATE));
		result += "-";
		result += String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		result += "-";
		result += String.valueOf(now.get(Calendar.MINUTE));
		result += "-";
		result += String.valueOf(now.get(Calendar.SECOND));
		return result;
	}
	
	static synchronized public boolean copyFileBinaly(File sFile,File tFile) throws IOException{
		// ファイルの存在を確認
		if(!sFile.exists())return false;
		byte[] buf = new byte[1024];
		int iSize;
		BufferedInputStream inputStream = 
			new BufferedInputStream(new FileInputStream(sFile));
		BufferedOutputStream outputStream = 
			new BufferedOutputStream(new FileOutputStream(tFile));
		while((iSize=inputStream.read(buf,0,buf.length)) != -1){
			outputStream.write(buf, 0, iSize);
		}
		outputStream.close();
		inputStream.close();
		return true;
	}
	
	/**
	 * ディレクトリをコピーする.
	 * Method copyDirectry.
	 * @param sourceDirectry	コピー元のディレクトリ
	 * @param targetDirectry	コピー先のディレクトリ
	 * @return boolean 指定されたコピー元ディレクトリがディレクトリでなかったり存在しないときはfalseを返す。
	 */
	
	//http://capsulecorp.studio-web.net/tora9/java/java_programming_tips/DirCopy.html
	static synchronized public boolean copyDirectry(File sDirectry, File tDirectry) throws IOException{
		// コピー元がディレクトリでない場合はfalseを返す
		if(!sDirectry.exists() || !sDirectry.isDirectory())return false;
		// ディレクトリを作成する
		tDirectry.mkdirs();
		// ディレクトリ内のファイルをすべて取得する
		File[] files = sDirectry.listFiles();

		// ディレクトリ内のファイルに対しコピー処理を行う
		for(int i = 0; files.length>i; i++){
			if(files[i].isDirectory()){
				// ディレクトリだった場合は再帰呼び出しを行う
				copyDirectry(
				new File(sDirectry.toString(), files[i].getName()), 
				new File(tDirectry.toString(), files[i].getName()));
			}else{
				// ファイルだった場合はファイルコピー処理を行う
				copyFileBinaly(
				new File(sDirectry.toString(), files[i].getName()), 
				new File(tDirectry.toString(), files[i].getName()));			
			}			
		}
		return true;
	}
}
