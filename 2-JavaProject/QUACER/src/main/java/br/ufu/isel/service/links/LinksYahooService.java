package br.ufu.isel.service.links;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.ReadHTML;

@Service
@Transactional
public class LinksYahooService {

	@Autowired
	private ReadHTML readHTML;

	public String getUrl(String query) {
		query = query.replace(" ", "+");
		query = query.replace("	", "%09");
		query = query.replace(",", "%2C");
		query = query.replace(":", "%3A");
		query = query.replace("’", "%E2%80%99");
		query = query + "+example+in+java";
		return "https://br.search.yahoo.com/search?p=" + query;
	}

	public List<String> getSites(String query) throws Exception {
		List<String> sites = new ArrayList<>();
		List<String> html = readHTML.getHtml(getUrl(query));
		for (String htmlLine : html) {
			if (htmlLine.contains("class=\" ac-algo fz-l ac-21th lh-24\"")) {
				String[] dados = htmlLine.split("class=\" ac-algo fz-l ac-21th lh-24\"");
				for (String dado : dados) {
					if (dado.startsWith(" href")) {
						String urlFinal = dado.substring(dado.indexOf("http"),
								dado.indexOf("\" ", dado.indexOf("http")));
						sites.add(urlFinal);
					}
				}
			}
		}
		if (sites.size() > 10 || sites.size() < 5) {
			throw new Exception("quantidade incorreta de sites para a URL " + getUrl(query) + " - " + sites.size());
		}
		return convert(sites);
	}	

	private List<String> convert(List<String> sites) {
		List<String> saida = new ArrayList<>();
		for (String site : sites) {
			String split = site.split("RU=")[1];
			split = split.replaceAll("%3a", ":");
			split = split.replaceAll("%2f", "/");
			split = split.substring(0, split.indexOf("RK"));
			saida.add(split);
		}
		return saida;
	}

}
