package br.ufu.isel.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Writer;

public class ScriptGenerateCSV {

	public static void main(String[] args) throws Exception {
		File file = new File("/home/carloseduardo/saida.csv");
		File readability = new File("/home/carloseduardo/readability.csv");
		File understandability = new File("/home/carloseduardo/understandability.csv");
		PrintWriter read = new PrintWriter(readability);
		PrintWriter und = new PrintWriter(understandability);
		read.println("site;dominio;ranking;readability");
		und.println("site;dominio;ranking;understandability");
		BufferedReader br = new BufferedReader(new FileReader(file));
		int i = 1;
		while (br.ready()) {
			String[] linha = br.readLine().split(";");
			if (i == 1) {
				i++;
				continue;
			}			
			
			if (!linha[4].equals("NA")) {
				read.println(linha[0]+";"+replace(linha[1])+";"+"Top"+linha[2]+";"+linha[4]);
			}
			if (!linha[6].equals("NA")) {
				und.println(linha[0]+";"+replace(linha[1])+";"+"Top"+linha[2]+";"+linha[6]);
			}			
			i++;			
		}
		und.close();
		read.close();
		

	}

	private static String replace(String string) {
		if (string.equals("stackoverflow.com")) {
			return "stack";
		}
		if (string.equals("www.tutorialspoint.com")) {
			return "tuto";
		}
		if (string.equals("www.geeksforgeeks.org")) {
			return "geeks";
		}
		if (string.equals("www.codegrepper.com")) {
			return "code";
		}
		if (string.equals("www.javatpoint.com")) {
			return "javat";
		}
		return null;
	}

}
