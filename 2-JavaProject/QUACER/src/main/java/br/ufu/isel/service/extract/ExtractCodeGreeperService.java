package br.ufu.isel.service.extract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.ReadHTML;

@Service("CodeGreeper")
@Transactional
public class ExtractCodeGreeperService implements ExtractCodeSnippetService {

	@Autowired
	private ReadHTML readHTML;
	
	@Override
	public String getCode(String link) throws Exception {
		String code = "";
		List<String> html = readHTML.getHtml(link);
        boolean insideCode = false;
		for (String htmlLine : html) {
			if (htmlLine.contains("class=\"code_mirror_code\"")) {
				code += htmlLine.substring(htmlLine.indexOf("code_mirror_code")+18)+"\n";
				insideCode = true;
        	} else if (insideCode) {
        		if (htmlLine.contains("</textarea>")) {
        			code += htmlLine.substring(0,htmlLine.indexOf("</textarea>"))+"\n";
        			code = code.replaceAll("&nbsp;"," ");
        			code = code.replaceAll("&quot;","\"");
        			code = code.replaceAll("&lt;","<");
        			code = code.replaceAll("&gt;",">");
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
