package br.ufu.isel.service.extract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.ReadHTML;

@Service("TutorialsPoint")
@Transactional
public class ExtractTutorialsPointService implements ExtractCodeSnippetService {

	@Autowired
	private ReadHTML readHTML;
	
	@Override	
	public String getCode(String link) throws Exception {
		String code = "";
		List<String> html = readHTML.getHtml(link);
        boolean insideCode = false;
		for (String htmlLine : html) {
			if (htmlLine.contains("<pre class=\"prettyprint notranslate\">")) {
				
				insideCode = true;
        		code += htmlLine.substring(htmlLine.indexOf("prettyprint notranslate")+25)+"\n";
        	} else if (insideCode) {
        		if (htmlLine.contains("</pre>")) {
        			code += htmlLine.substring(0,htmlLine.indexOf("</pre>"));
        			code = code.replaceAll("&nbsp;"," ");
        			code = code.replaceAll("&quot;","\"");
        			
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
