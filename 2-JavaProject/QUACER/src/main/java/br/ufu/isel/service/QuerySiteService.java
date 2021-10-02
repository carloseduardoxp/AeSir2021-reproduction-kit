package br.ufu.isel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.domain.QuerySite;
import br.ufu.isel.repository.QuerySiteRepository;

@Service
@Transactional
public class QuerySiteService {
	
	@Autowired
	private QuerySiteRepository repository;
	
	public void salvar(List<QuerySite> queries) {
		repository.saveAll(queries);
	}
	
	@Transactional(readOnly = true)
	public List<QuerySite> findByDomain(String domain) {
		return repository.findByDomainAndCodeIsNullOrderByRankingDesc(domain);
	}
	
	@Transactional(readOnly = true)
	public Optional<QuerySite> findById(Integer code) {
		return repository.findById(code);
	}
	
	@Transactional(readOnly = true)
	public List<QuerySite> findCodeIsNotNull() {
		return repository.findByUnderstandabilityIsNotNullAndCodeIsNotNull();
	}

	public void update(QuerySite querySite) {
		repository.save(querySite);		
	}
	
	@Transactional(readOnly = true)
	public List<QuerySite> findByDomainCode(String domain) {
		return repository.findByDomainAndCodeIsNotNull(domain);
	}

}
