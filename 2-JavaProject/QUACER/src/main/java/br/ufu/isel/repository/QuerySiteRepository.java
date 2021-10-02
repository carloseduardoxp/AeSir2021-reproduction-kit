package br.ufu.isel.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufu.isel.domain.QuerySite;

public interface QuerySiteRepository extends CrudRepository<QuerySite,Integer> {
	
	List<QuerySite> findByDomainAndCodeIsNullOrderByRankingDesc(String domain);
	List<QuerySite> findByDomainAndCodeIsNotNull(String domain);
	List<QuerySite> findByUnderstandabilityIsNotNullAndCodeIsNotNull();	

}
