package br.ufu.isel;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.ufu.isel.action.Action;

@Component
public class Principal {
	
	//@Value("${action}")
	//public String action1;
	
	@Autowired
	protected ApplicationContext appContext;
	
	@Autowired
	@Qualifier("metricAction")
	//@Qualifier("ExtractCodeSnippet")
	//@Qualifier("sitesQueries")
	private Action action;
	
	@PostConstruct
	public void init() throws Exception {
		action.execute();	
		System.out.println("Now shutting down " + action);
		int exitCode = SpringApplication.exit(appContext, (ExitCodeGenerator) () -> 0);
		System.exit(exitCode);
	}

}
