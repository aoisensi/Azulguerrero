package info.aoisensi.azulguerrero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AzulguerreroIME {
	private static boolean isEnable = true;
	
	

	
	public static String toJapanese(String message) throws IOException, SAXException{
		String appID = getAppID();
		if(isEnable){
			String xml = null;
			String result = null;
			URL url = new URL("http://jlp.yahooapis.jp/JIMService/V1/conversion");
			String getStr = "appid=" + appID + 
							  "&sentence=" + URLEncoder.encode(message , "UTF-8") + 
							  "&format=roman";
			try {
				xml = Network.getContent(url, getStr);
			} catch (ParseException e1) {
				return "変換に失敗しました:ParseException";
			}
			
			Document root;
			try {
				root = Network.getDocument(xml);
			} catch (ParserConfigurationException e) {
				return "変換に失敗しました:ParserConfigurationException";
			}
			System.out.println("待" + root);
			
			
			return result;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("resource")
	private static String getAppID(){
		Reader reader;
		String appID = null;
		try {
			File file = new File(Azulguerrero.dataFolder, "yahoodevnet.txt");
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			try {
				appID = br.readLine();
			} catch (IOException e) {
				isEnable = false;
			}
		} catch (FileNotFoundException e) {
			isEnable = false;
		}
		
		return appID;
	}




}
