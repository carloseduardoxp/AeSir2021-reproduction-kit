package br.ufu.isel.service.metric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

@Service
public class ReadabilityMetricService {
	
	@Value("${QUACER_TEMP_FOLDER}")
	public String QUACER_TEMP_FOLDER;
	
	//@Autowired
	//private JdtLibraries libraries;
	
	//File classifierFile = 
	//		new File("/home/carloseduardo/Documents/workspace-spring-tool-suite-4-4.10.0.RELEASE/QUACER/lib/"
	//		+ "readability.classifier");
	//final UnifiedMetricClassifier classifier = UnifiedMetricClassifier.loadClassifier(classifierFile);

	
	public Double getScore(Integer id,String code) throws Exception {	
		try {
			//code = libraries.getRevisedCode(code);
			//final double classReadability = classifier.classifyClass(code);
	        //return classReadability;
			
			String filePath = QUACER_TEMP_FOLDER+"/"+id+".java";
			Path currentRelativePath = Paths.get("");
			String homeDir = currentRelativePath.toAbsolutePath().toString();
			File outputFile = new File(QUACER_TEMP_FOLDER+"/outputReadability.txt");
			
			runProcess(homeDir,filePath,outputFile.getAbsolutePath());
			return readScore(outputFile);		
		} catch(Exception e) {
			e.printStackTrace();
			return -1d;
		} catch(Error e1) {
			return -1d;
		}		
	}
	
	private void runProcess(String homeDir,String snippetDir, String outputFile) throws Exception {
		List<String> command = new ArrayList<String>();
		command = ImmutableList.of("/bin/bash", "-c","cd "+homeDir + "/lib && java -jar rsm.jar "+snippetDir+" > "+outputFile);

		Process p = new ProcessBuilder().inheritIO().command(command).start();
		p.waitFor();	
	}
	
	private Double readScore(File outputFile) throws Exception {
		BufferedReader bw = new BufferedReader(new FileReader(outputFile));
		while (bw.ready()) {
			String line = bw.readLine();
			String[] data = line.split("	");
			if (data.length > 1 && isDouble(data[1])) {				
				return Double.parseDouble(data[1]);				
			}	
		}		
		bw.close();
		throw new Exception("cant find score ");
	}
	
	public static boolean isDouble(String string) {
		try {
	        Double.parseDouble(string);
	        return true;
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	}

}
