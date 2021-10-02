package br.ufu.isel.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.ufu.isel.domain.QueryCrokage;
import br.ufu.isel.service.QueryCrokageService;
import br.ufu.isel.util.JdtLibraries;

@Component("populateSourceCodeQuery")
public class PopulateSourceCodeQueryAction implements Action {
	
	@Autowired
	private QueryCrokageService crokageService;
	
	@Value("${QUACER_CROKAGE_JAVA_CLASSES}")
	private String crokageJavaClassesDir;
	
	@Autowired
	private JdtLibraries libraries;
	
	
	@Override
	public void execute() {
		try {						
			List<QueryCrokage> queries = crokageService.getQueries();			
			int quantos = 1;
			for (QueryCrokage query: queries) {				
				File file = new File(crokageJavaClassesDir+query.getId()+" - "+query.getQuery());
				if (file.exists()) {
					continue;
				}
				if (quantos % 10 == 0) {
					System.out.println("Done "+quantos);				
				}
				quantos++;
				String code = crokageService.getCrokageCode(query.getQuery());
				libraries.initialize(code);
				if (!libraries.isClasse()) {
					String code1 = "public class Test { \n "+ code + "\n }";
					libraries.initialize(code1);					
					if (!libraries.isMetodo()) {
						code = "public class Test { \n public static void main(String args[]) { \n "+ code + "\n } \n }";	
					} else {
						code = "public class Test { \n "+ code + "\n }";	
					}					
				} 
				
				PrintWriter pw = new PrintWriter(file);
				pw.write(code);
				pw.close();
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		

	}



}
