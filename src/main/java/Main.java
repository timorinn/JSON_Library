import LibraryJSON.JsonObject;
import LibraryJSON.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

	public static void main(String[] args) {
//		String s = "0123456789";
//		System.out.println(s.substring(1, 4)); // 123
//		System.out.println(s.substring(2, s.length()));
//		try {
//			String url = "https://test.wikipedia.org/w/api.php?action=query&format=json&prop=extracts&titles=Stack%20Overflow&redirects=1&formatversion=2&exlimit=1&exintro=1&explaintext=1";
//
//			URL obj = new URL(url);
//			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
//
//			connection.setRequestMethod("GET");
//
//			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//			in.close();
//
//			System.out.println(response.toString());
//
////			JsonObject jsonObject = new JsonParser(response.toString()).parseBody();
//
//		} catch (Exception e) {
//			System.out.println("EXCEPTION ! ! ! " + e.toString());
//		}
	}
}
