package br.ufu.isel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "querysite")
public class QuerySite {
	
	@Id
    @SequenceGenerator(name="querysite_id_seq", sequenceName="querysite_id_seq",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="querysite_id_seq")
	private Integer id;
	
	@Column(name="link")
	private String link;
	
	@Column(name="ranking")
	private Integer ranking;
	
	@Column(name="url")
	private String url;
	
	@Column(name="site")
	private String site;
	
	@Column(name="dominio")
	private String domain;
	
	@Column(name="code")
	private String code;
	
	@Column(name="readability")
	private Double readability;
	
	@Column(name="understandability")
	private Double understandability;

	
	@ManyToOne
	@JoinColumn(name = "queryid",referencedColumnName = "id")
	private QueryCrokage queryCrokageUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public QueryCrokage getQueryCrokageUser() {
		return queryCrokageUser;
	}

	public void setQueryCrokageUser(QueryCrokage queryCrokageUser) {
		this.queryCrokageUser = queryCrokageUser;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getReadability() {
		return readability;
	}

	public void setReadability(Double readability) {
		this.readability = readability;
	}

	public Double getUnderstandability() {
		return understandability;
	}

	public void setUnderstandability(Double understandability) {
		this.understandability = understandability;
	}
	
	
	

}
