package br.ufu.isel.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.ufu.isel.domain.QueryCrokage;
@Repository
public class QueryCrokageRepositoryImpl implements QueryCrokageRepository {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryCrokage> getQueries() {
		/*Query q = em.createNativeQuery(
				"select id,querytext as querytext  "
				+ "from query where java_related is null and id in (select distinct queryid from querysite) "
				+ "and id not in (select distinct queryid from querysite where site = 'Bing') "
				+ "order by querytext",QueryCrokage.class);*/
		Query q = em.createNativeQuery(
		"select id,querytext as querytext  "
		+ "from query where java_related is null and id not in (select distinct queryid from querysite) "	
		+ "order by id",QueryCrokage.class);
		return (List<QueryCrokage>) q.getResultList();
	}

}
