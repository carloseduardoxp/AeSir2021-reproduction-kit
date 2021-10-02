package br.ufu.isel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "query")
public class QueryCrokage {
	
	@Id
    @SequenceGenerator(name="query_id_seq", sequenceName="query_id_seq",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="query_id_seq")
	private Integer id;
	
	@Column(name="querytext")
	private String query;

	
	public QueryCrokage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QueryCrokage(Integer id, String query) {
		super();
		this.id = id;
		this.query = query;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	

}
