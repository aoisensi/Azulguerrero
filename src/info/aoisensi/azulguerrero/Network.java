package info.aoisensi.azulguerrero;

import java.io.*;
import java.net.*;
import java.text.ParseException;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class Network {
	public static Document getDocument(String xmlContent) throws IOException, SAXException, ParserConfigurationException {
		StringReader sr = new StringReader(xmlContent);
		InputSource is = new InputSource(sr);
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		return doc;
	}

	// URLからコンテンツ(HTML/XMLページの文字列)を取得
	public static String getContent(URL url, String parameters) throws IOException, ParseException {

		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.connect();

		OutputStream os = con.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(parameters);
		bw.flush();
		bw.close();

		InputStream is = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buf = new StringBuffer();
		String s;
		while ((s = br.readLine()) != null) {
			buf.append(s);
			buf.append("\r\n"); // 改行コードKIMEUCHI
		}
		br.close();
		con.disconnect();

		return buf.toString();
	}
}
