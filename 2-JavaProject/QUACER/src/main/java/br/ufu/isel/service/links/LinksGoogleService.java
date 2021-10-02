package br.ufu.isel.service.links;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.ReadHTML;

@Service
@Transactional
public class LinksGoogleService {

	@Autowired
	private ReadHTML readHTML;

	public String getUrl(String query) {
		query = query.replace(" ", "+");
		query = query.replace("	", "%09");
		query = query.replace(",", "%2C");
		query = query.replace("’", "%E2%80%99");
		query = query + "+example+in+java";
		return "https://www.google.com/search?q=" + query;
	}

	public List<String> getSites(String query) throws Exception {
		List<String> sites = new ArrayList<>();
		List<String> html = readHTML.getHtml(getUrl(query));
		for (String htmlLine : html) {
			if (htmlLine.contains("<div class=\"kCrYT\">")) {
				String[] dados = htmlLine.split("<div class=\"kCrYT\">");
				for (String dado : dados) {
					if (dado.startsWith("<a href=\"/url?q=") && dado.contains("http")) {
						String urlFinal = dado.substring(dado.indexOf("http"),
								dado.indexOf("\">", dado.indexOf("http")));
						if (urlFinal.contains("&amp")) {
							urlFinal = urlFinal.substring(0,urlFinal.indexOf("&amp"));
						}
						if (!sites.contains(urlFinal)) {
							sites.add(urlFinal);	
						}						
					}
				}
			}
		}
		if (sites.size() > 10 || sites.size() < 5) {
			throw new Exception("quantidade incorreta de sites para a URL " + getUrl(query) + " - " + sites.size());
		}
		return sites;
	}	

}
