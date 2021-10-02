package br.ufu.isel.service.extract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.ReadHTML;

@Service("GeeksforGeeks")
@Transactional
public class ExtractGeeksforGeeksService implements ExtractCodeSnippetService {
	
	@Autowired
	private ReadHTML readHTML;
	
	@Override
	public String getCode(String link) throws Exception {
		String code = "";
		List<String> html = readHTML.getHtml(link);
		for (String htmlLine : html) {
			if (htmlLine.contains("<td class=code>")) {
        		String[] linhas = htmlLine.split("div class=\"line number");
        		for (int i = 1; i < linhas.length;i++) {
        			String[] codeClasses = linhas[i].split("<code class=");
        			for (int j = 1; j < codeClasses.length;j++) {
        				code += codeClasses[j].substring(codeClasses[j].indexOf(">")+1,codeClasses[j].indexOf("<"))+" ";
        			}
        			code += "\n";
        		}	
        		code = code.replaceAll("&nbsp;"," ");
        		return code;
        	}
		}
		return null;
		//throw new Exception("Can't find code for link "+link);
	}

}
