package br.ufu.isel.action.links;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufu.isel.action.Action;
import br.ufu.isel.domain.QueryCrokage;
import br.ufu.isel.domain.QuerySite;
import br.ufu.isel.service.QueryCrokageService;
import br.ufu.isel.service.QuerySiteService;
import br.ufu.isel.service.links.LinksBingService;
import br.ufu.isel.service.links.LinksGoogleService;
import br.ufu.isel.service.links.LinksYahooService;

@Component("sitesQueries")
public class LinksAction implements Action {

	@Autowired
	private QueryCrokageService crokageService;

	@Autowired
	private QuerySiteService querySiteService;

	@Autowired
	private LinksGoogleService googleService;

	@Autowired
	private LinksBingService bingService;

	@Autowired
	private LinksYahooService yahooService;

	@Override
	public void execute() {
		Integer queryAtual = 0;
		Integer errorLimit = 3000;
		Integer errors = 0;
		List<QueryCrokage> queries = crokageService.getQueries();
		System.out.println("Quantidade de queries " + queries.size());
		int quantos = 1;
		for (QueryCrokage q : queries) {
			queryAtual = q.getId();
			if (quantos % 10 == 0) {
				System.out.println("Saved " + quantos);
			}
			quantos++;
			List<QuerySite> queriesSave = new ArrayList<>();
			try {
				Thread.sleep(1000);
				queriesSave.addAll(montaQuerySite(bingService.getSites(q.getQuery()), "Bing", q,
						bingService.getUrl(q.getQuery())));
				queriesSave.addAll(montaQuerySite(yahooService.getSites(q.getQuery()), "Yahoo", q,
						yahooService.getUrl(q.getQuery())));
				queriesSave.addAll(montaQuerySite(googleService.getSites(q.getQuery()), "Google", q,
						googleService.getUrl(q.getQuery())));
				querySiteService.salvar(queriesSave);				
			} catch (Exception e) {
				System.out.println("Error on QueryID: " + queryAtual + " -> " + e);
				e.printStackTrace();
				errors++;
				if (errors >= errorLimit) {
					System.out.println("Ending application because reach error limit " + errorLimit);
					break;
				}
			}
		}

	}

	public List<QuerySite> montaQuerySite(List<String> links, String site, QueryCrokage q, String url)
			throws Exception {
		if (links.size() < 5) {
			throw new Exception("Erro: era esperado que a consulta retornasse pelo menos 5 links, e retornou apenas "
					+ links.size() + " para a url " + url);
		}
		List<QuerySite> queries = new ArrayList<>();
		int top = 1;
		for (String link : links) {
			QuerySite qs = new QuerySite();
			qs.setLink(link);
			qs.setRanking(top);
			qs.setQueryCrokageUser(q);
			qs.setSite(site);
			qs.setUrl(url);
			queries.add(qs);
			top++;
		}
		return queries;
	}

}
