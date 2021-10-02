package br.ufu.isel.action.metric;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufu.isel.action.Action;
import br.ufu.isel.domain.QuerySite;
import br.ufu.isel.service.QuerySiteService;
import br.ufu.isel.service.metric.ReadabilityMetricService;
import br.ufu.isel.service.metric.UnderstandabilityMetricScore;

@Component("metricAction")
public class MetricAction implements Action {
	
	@Autowired
	private QuerySiteService querySiteService;
	
	@Autowired
	private ReadabilityMetricService readabilityMetricService;
	
	@Autowired
	private UnderstandabilityMetricScore understandabilityMetricScore;
	
	@Override
	public void execute() {
		Integer queryAtual = 0;
		int quantos = 1;
		List<QuerySite> queries = querySiteService.findCodeIsNotNull();
		System.out.println("Queries with code to get metrics: "+queries.size());
		for (QuerySite querySite: queries) {
			queryAtual = querySite.getId();
			if (quantos % 10 == 0) {
				System.out.println("Saved " + quantos);
			}
			quantos++;
			try {
				Double und = understandabilityMetricScore.getScore(queryAtual,querySite.getCode());
				querySite.setUnderstandability(und);
				Double read = readabilityMetricService.getScore(queryAtual,querySite.getCode());
				querySite.setReadability(read);
				querySiteService.update(querySite);	
			} catch(Exception e) {
				System.out.println("O erro aconteceu na query "+queryAtual+" -> "+e);
				e.printStackTrace();
			}		
						
		}
	}

}
