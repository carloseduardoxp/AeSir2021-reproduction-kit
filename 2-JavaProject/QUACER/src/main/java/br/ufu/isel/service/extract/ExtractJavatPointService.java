package br.ufu.isel.service.extract;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.ReadHTML;

@Service("JavatPoint")
@Transactional
public class ExtractJavatPointService implements ExtractCodeSnippetService {
	
	@Autowired
	private ReadHTML readHTML;
	
	@Override
	public String getCode(String link) throws Exception {
		List<String> html = readHTML.getHtml(link);       
		List<String> codes = getCodeSnippets(html);
		if (codes.isEmpty()) { 
			return null;
		} else {
			for (String code: codes) {
				if (code.toLowerCase().contains("example")) {
					return code;
				}
			}
			return codes.get(0);
		}
	}
	
	private List<String> getCodeSnippets(List<String> html) {
		boolean insideCode = false;
        List<String> codes = new ArrayList<>();
        String code = "";
		for (String htmlLine : html) {
			if (htmlLine.contains("codeblock") && htmlLine.contains("class=\"java\"")) {
				if (htmlLine.contains("<textarea") || htmlLine.contains("<pre")) {
					insideCode = true;
					code += htmlLine.substring(htmlLine.indexOf("java\">")+6)+"\n";
				} 
        	} else if (insideCode) {
        		if (htmlLine.contains("</textarea>") || htmlLine.contains("</pre>")) {
        			codes.add(code);
        			insideCode = false;
        			code = "";
        		} else {
        			code += htmlLine+"\n";
        		}
        	}
		}
		return codes;
	}

}
