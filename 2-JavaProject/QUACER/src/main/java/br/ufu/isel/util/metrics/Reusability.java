package br.ufu.isel.util.metrics;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleType;
import org.springframework.stereotype.Component;

import br.ufu.isel.util.JdtLibraries;

@Component
public class Reusability {

	private static final Logger log = Logger.getLogger(JdtLibraries.class);

	private Set<String> declarations;

	public void initialize(File file) throws Exception {
		StringBuilder data = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (br.ready()) {
			data.append(br.readLine() + "\n");
		}
		br.close();
		initialize(data.toString());
	}

	public void initialize(String code) {
		final ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		// parser.setBindingsRecovery(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		// parser.setEnvironment(dependencyJarPaths, null, null, false);

		Map options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, "1.8");
		parser.setCompilerOptions(options);

		parser.setSource(code.toCharArray());

		declarations = new LinkedHashSet<>();

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		IProblem[] problems = cu.getProblems();
		if (problems.length > 0) {
			for (IProblem problem : problems) {
				System.out.println(problem.getMessage());
			}
		} else {
			System.out.println("Sem problemas");
		}
		cu.accept(new TypeFinderVisitor());
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
		public boolean visit(SimpleType node) {
			declarations.add(node.toString());
			return true;
		}

	}

	public void compile(File file) throws Exception {
		String[] args = new String[] { "-classpath", "./snap/vlc/2288/usr/lib/jvm/java-8-openjdk-amd64/lib/",
				"-sourcepath", "/home/carloseduardo/Downloads/java/", "-d", "/home/carloseduardo/Downloads/java/",
				file.getAbsolutePath() };

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		com.sun.tools.javac.Main javac = new com.sun.tools.javac.Main();
		PrintWriter out = new PrintWriter(os);
		int compileStatus = javac.compile(args, out);
		System.out.println(compileStatus);
		
		ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());
		int n = in.available();
		byte[] bytes = new byte[n];
		in.read(bytes, 0, n);
		String s = new String(bytes, StandardCharsets.UTF_8); // Or any encoding.

		
		System.out.println("li " +s);

		System.out.println("teste");

		// set up compiler
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		final List<String> errors = new ArrayList<>();
		try (final StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
			final Iterable<? extends JavaFileObject> compilationUnits = fileManager
					.getJavaFileObjectsFromFiles(Arrays.asList(file));

			// compile generated source
			// (switch off annotation processing: no need to create Log4j2Plugins.dat)
			final List<String> options = Arrays.asList("-proc:none");
			compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits).call();

			// check we don't have any compilation errors
			for (final Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				System.out.println(diagnostic.getKind()+" - "+diagnostic.getMessage(Locale.getDefault()));
				if (diagnostic.getKind() == Diagnostic.Kind.ERROR) {
					//System.out.println(String.format("Compile error: %s%n", diagnostic.getMessage(Locale.getDefault())));
				}
			}
		}
	}

	public static void main(String args[]) throws Exception {
		Reusability r = new Reusability();
		File file = new File("/home/carloseduardo/Downloads/java/Bing.java");
		r.initialize(file);
		r.compile(file);
		System.out.println(r.declarations);
	}

}