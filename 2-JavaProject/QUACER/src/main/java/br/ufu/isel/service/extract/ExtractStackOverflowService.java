package br.ufu.isel.service.extract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.ReadHTML;

@Service("StackOverflow")
@Transactional
public class ExtractStackOverflowService implements ExtractCodeSnippetService {
	
	@Autowired
	private ReadHTML readHTML;
	
	@Override	
	public String getCode(String link) throws Exception {
		String code = "";
		List<String> html = readHTML.getHtml(link);
		boolean respostaAceita = false;
        boolean insideCode = false;
		for (String htmlLine : html) {
			if (htmlLine.contains("js-accepted-answer-indicator")) {
        		respostaAceita = true;
        	} 
        	if (respostaAceita && htmlLine.contains("<pre><code>")) {
        		code = htmlLine.substring(11)+"\n";
        		insideCode = true;
        	} else if (insideCode) {
        		if (htmlLine.equals("</code></pre>")) {
        			return code;
        		} else {
        			code += htmlLine+"\n";
        		}
        	}
		}
		return null;
		//throw new Exception("Can't find code for link "+link);
	}		

}
