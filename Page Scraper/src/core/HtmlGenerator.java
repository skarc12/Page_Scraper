package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlGenerator {
	public String websiteUrl;

	public HtmlGenerator(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getHtmlFile() {
		URL website;
		BufferedReader r = null;
		StringBuilder st = new StringBuilder();
		try {
			website = new URL(websiteUrl);
			HttpURLConnection connection = (HttpURLConnection) website.openConnection();
			InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
			r = new BufferedReader(reader);
			String line = "";
			while ((line = r.readLine()) != null) {
				st.append(line);
				st.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return st.toString();
	}

}
