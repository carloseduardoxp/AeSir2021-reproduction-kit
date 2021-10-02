package br.ufu.isel.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.springframework.stereotype.Component;

@Component
public class JdtLibraries {

    private static final Logger log = Logger.getLogger(JdtLibraries.class);
    private boolean classe;
    private boolean metodo;     

    public void initialize(String code) {                       
        this.classe = false;
        this.metodo = false;
        ASTParser parser = getAST(code);
        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        cu.accept(new TypeFinderVisitor());
    }
    
    public ASTParser getAST(String code) {
    	final ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setResolveBindings(true);
        //parser.setBindingsRecovery(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        //parser.setEnvironment(dependencyJarPaths, null, null, false);

        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_SOURCE, "1.8");
        parser.setCompilerOptions(options);
        
        parser.setSource(code.toCharArray());

        return parser;
    }
    
    public String getRevisedCode(String code) { 
    	initialize(code);
		if (!isClasse()) {
			String code1 = "public class Test { \n "+ code + "\n }";
			initialize(code1);					
			if (!isMetodo()) {
				code = "public class Test { \n public static void main(String args[]) { \n "+ code + "\n } \n }";	
			} else {
				code = "public class Test { \n "+ code + "\n }";	
			}					
		}
		return code;
    }

    class TypeFinderVisitor extends ASTVisitor {
        protected boolean isAnyNeededElementNull(MethodInvocation node) {
            IMethodBinding binding = node.resolveMethodBinding();
            if (binding == null) {
                return true;
            }

            Expression expression = node.getExpression();
            if (expression == null) {
                return true;
            }

            ITypeBinding typeBinding = expression.resolveTypeBinding();
            if (typeBinding == null) {
                return true;
            }

            if (typeBinding.getPackage() == null) {
                return true;
            }

            return false;
        }
        
        @Override
        public boolean visit(TypeDeclaration node) {
        	classe = true;
        	return true;
        }

        @Override
        public boolean visit(MethodDeclaration node) {
        	metodo = true;
            return true;
        }
        
    }

	public boolean isClasse() {
		return classe;
	}

	public boolean isMetodo() {
		return metodo;
	}

	
    
    
}