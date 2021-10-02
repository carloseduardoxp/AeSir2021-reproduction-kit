package br.ufu.isel.action.extract;

public enum ExtractCodeSnippetsEnum {

	TUTORIALS_POINT("www.tutorialspoint.com","TutorialsPoint"),
	CODE_GREEPER("www.codegrepper.com","CodeGreeper"),
	JAVA_T_POINT("www.javatpoint.com","JavatPoint"),
	STACK_OVERFLOW("stackoverflow.com","StackOverflow"),
	GEEKS_FOR_GEEKS("www.geeksforgeeks.org","GeeksforGeeks");	
	
	
	
	
		
	private String dominio;
	
	private String serviceName;

	
	private ExtractCodeSnippetsEnum(String dominio, String serviceName) {
		this.dominio = dominio;
		this.serviceName = serviceName;
	}

	public String getDominio() {
		return dominio;
	}

	public String getServiceName() {
		return serviceName;
	}


	
	

}
