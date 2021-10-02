package br.ufu.isel.action.extract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.ufu.isel.action.Action;
import br.ufu.isel.domain.QuerySite;
import br.ufu.isel.service.QuerySiteService;
import br.ufu.isel.service.extract.ExtractCodeSnippetService;

@Component("ExtractCodeSnippet")
public class ExtractCodeSnippetAction implements Action {
	
	@Autowired
	protected ApplicationContext appContext;
	
	@Autowired
	private QuerySiteService querySiteService;

	@Override
	public void execute() {		
		ExtractCodeSnippetsEnum[] sites = ExtractCodeSnippetsEnum.values();
		for (ExtractCodeSnippetsEnum site: sites) {
			String domain = site.getDominio();
			String serviceName = site.getServiceName();
			ExtractCodeSnippetService service = (ExtractCodeSnippetService)appContext.getBean(serviceName);
			System.out.println("Extracting code snippets from domain "+domain);
			extractCodeSnippets(domain,service);			
		}				
	}

	private void extractCodeSnippets(String domain, ExtractCodeSnippetService service) {		
		Map<String,String> codes = new HashMap<String, String>();
		Integer queryAtual = 0;
		try {						
			codes = loadCodes(domain);
			System.out.println("Queries with code: "+codes.size());
			List<QuerySite> queries = querySiteService.findByDomain(domain);
			System.out.println("Queries without code: "+queries.size());
			int quantos = 1;
			for (QuerySite q: queries) {
				queryAtual = q.getId();
				if (quantos % 1 == 0) {
					System.out.println("Saved "+quantos);
				}
				quantos++;
				if (codes.containsKey(q.getLink())) {
					q.setCode(codes.get(q.getLink()));
					querySiteService.update(q);
				} else {
					String code = service.getCode(q.getLink());
					q.setCode(code);
					querySiteService.update(q);
					codes.put(q.getLink(),code);
					Thread.sleep(2000);
				}				
			}
		} catch(Exception e) {
			System.out.println("O erro aconteceu na query "+queryAtual+" -> "+e);
			e.printStackTrace();			
		}				
	}

	private Map<String, String> loadCodes(String domain) {
		Map<String,String> codes = new HashMap<String, String>();
		List<QuerySite> queries = querySiteService.findByDomainCode(domain);
		for (QuerySite q: queries) {
			codes.put(q.getLink(),q.getCode());
		}
		return codes;
	}



}
