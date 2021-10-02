package br.ufu.isel.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import br.ufu.isel.domain.QueryCrokage;
import br.ufu.isel.repository.QueryCrokageRepository;

@Service
@Transactional
public class QueryCrokageService {

	@Autowired
	private QueryCrokageRepository repository;

	private String crokageURI = "http://isel.ufu.br:8080/crokage/query/getsolutions";

	@Transactional(readOnly = true)
	public List<QueryCrokage> getQueries() {
		return repository.getQueries();
	}

	public String getCrokageCode(String query) throws Exception {
		// create request body
		JSONObject request = new JSONObject();
		request.put("queryText", query);		
		request.put("numberOfComposedAnswers", 1);
		request.put("reduceSentences", true);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(crokageURI, HttpMethod.POST, entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			JSONObject userJson = new JSONObject(response.getBody());
			JSONArray allAnswers = userJson.getJSONArray("posts");			
			for (int i = 0; i < allAnswers.length();i++) {
				String code = allAnswers.getJSONObject(i).getString("code");
				if (code != null && code.trim().length() > 0) {
					return code;
				}
			}			
		} 
		throw new Exception(response.getBody());
	}

}
