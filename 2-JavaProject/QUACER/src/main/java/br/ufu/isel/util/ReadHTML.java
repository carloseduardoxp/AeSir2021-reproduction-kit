package br.ufu.isel.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ReadHTML {

	public List<String> getHtml(String link) throws Exception {
		List<String> content = new ArrayList<>();
		InputStream is = null;
		String line;
		try {
			URL url = new URL(link);
			URLConnection c = url.openConnection();
			c.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.168");
			c.connect();
			is = c.getInputStream();
			BufferedReader d = new BufferedReader(new InputStreamReader(is));			
			while ((line = d.readLine()) != null) {
				content.add(line);
			}
		} catch(Exception e) {
			System.out.println("Error on read HTML from "+link);
			throw e;
		} finally {
			is.close();			
		}

		return content;
	}

}
