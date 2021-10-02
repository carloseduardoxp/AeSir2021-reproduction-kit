package br.ufu.isel;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class QuacerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new QuacerApplication().configure(new SpringApplicationBuilder(QuacerApplication.class)).run(args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QuacerApplication.class);
    }

}
