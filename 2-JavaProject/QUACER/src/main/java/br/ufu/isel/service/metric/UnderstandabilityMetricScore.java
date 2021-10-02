package br.ufu.isel.service.metric;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.sonar.java.ast.visitors.CognitiveComplexityVisitor;
import org.sonar.java.checks.verifier.CheckVerifier;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufu.isel.util.JdtLibraries;

@Service
@Transactional
public class UnderstandabilityMetricScore {
	
	@Autowired
	private JdtLibraries libraries;
	
	@Value("${QUACER_TEMP_FOLDER}")
	public String QUACER_TEMP_FOLDER;

	public Double getScore(Integer id,String code) throws Exception {
		PrintWriter pw = null;
		try {
			code = libraries.getRevisedCode(code);
			String filePath = QUACER_TEMP_FOLDER+"/"+id+".java";
			File file = new File(filePath);
			if (!file.exists()) {
				pw = new PrintWriter(file);
				pw.println(code);	
			}			
			CheckUnderstandability check = new CheckUnderstandability();
			
			CheckVerifier.newVerifier()		
			  .onFile(filePath)
		      .withCheck(check)
		      .withoutSemantic()
		      .verifyNoIssues();
							
			return check.getTotal().doubleValue();
	
		} catch(Exception e) {			
			e.printStackTrace();
			return -1d;
		} catch(AssertionError ae) {
			return -1d;
		}
		finally {
			if (pw != null) {
				pw.close();	
			}			
		}
	}
}

class CheckUnderstandability extends IssuableSubscriptionVisitor {

	Integer total = 0;

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return Arrays.asList(Tree.Kind.METHOD, Tree.Kind.CONSTRUCTOR);
	}

	@Override
	public void visitNode(Tree tree) {
		MethodTree method = (MethodTree) tree;
		CognitiveComplexityVisitor.Result result = CognitiveComplexityVisitor.methodComplexity(method);
		total += result.complexity;
	}

	public Integer getTotal() {
		return total;
	}
	
	
}