package info.aoisensi.azulguerrero;

import java.io.*;
import java.net.*;

public class AzulguerreroLingr {
	public static void say(String message){
		try{
			URL url = new URL("http://lingr.com/api/room/say");
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			OutputStreamWriter ow = new OutputStreamWriter(con.getOutputStream());
			BufferedWriter bw = new BufferedWriter(ow);
			String post = "room=mcass";
			post += "\nbot_id=mcsmcserver_bot";
			post += ("\ntext="+message);
			post += "\nbot_verifier=xxxxxxxxx";
			bw.write(post);
			bw.close();
			ow.close();
			
			InputStreamReader ir = new InputStreamReader(con.getInputStream());
			BufferedReader br = new BufferedReader(ir);
			
			
			br.close();
			ir.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
