package evosuiteAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import analyzer.Analyzer;
import analyzer.AnalyzerMethod;

public class EvoSuiteAnalyzer {

	public static void main(String[] args){
		EvoSuiteAnalyzer evoSuiteAnalyzer = new EvoSuiteAnalyzer();
		ArrayList<String> inputFileNameLists = evoSuiteAnalyzer.getInputFileNameLists();
		ArrayList<String> inputTestFileNameLists = evoSuiteAnalyzer.getInputTestFileNameLists();
		Analyzer analyzer = new Analyzer();
		analyzer.run(inputFileNameLists);
		evoSuiteAnalyzer.run(inputTestFileNameLists, analyzer.getMethodLists());
	}
	
	private ArrayList<String> getInputFileNameLists(){
		ArrayList<String> result = new ArrayList<String>();
		result.add("src/main/resources/ex03/Example.java");
		result.add("src/main/resources/ex03/Executer.java");
		
		return result;
	}
	
	private ArrayList<String> getInputTestFileNameLists(){
		ArrayList<String> result = new ArrayList<String>();
		result.add("src/main/resources/ex03/Example_ESTest.java");
		result.add("src/main/resources/ex03/Executer_ESTest.java");
		
		return result;
	}
	
	public ArrayList<EvoSuiteClass> run(ArrayList<String> inputTestFileNameLists, ArrayList<AnalyzerMethod> analyzerMethodLists) {
		ArrayList<EvoSuiteClass> evoSuiteClassLists = this.createEvoSuiteClassLists(inputTestFileNameLists);
		this.analyzeEvoSuiteClass(evoSuiteClassLists);
		// assert attach method
		this.attachAssertionAndMethod(evoSuiteClassLists);
		// detect Analyzer Method
		this.detectAnalzerMethod(evoSuiteClassLists, analyzerMethodLists);
		
		return evoSuiteClassLists;
	}
	
	private void detectAnalzerMethod(ArrayList<EvoSuiteClass> evoSuiteClassLists, ArrayList<AnalyzerMethod> analyzerMethodLists) {
		for(int classNum = 0; classNum < evoSuiteClassLists.size(); classNum++) {
			EvoSuiteClass evoSuiteClass = evoSuiteClassLists.get(classNum);
			
			for(int testNum = 0; testNum < evoSuiteClass.getTestLists().size(); testNum++){
				EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(testNum);
				
				for(int methodNum = 0; methodNum < evoSuiteTest.getMethodLists().size(); methodNum++) {
					EvoSuiteMethod evoSuiteMethod = evoSuiteTest.getMethodLists().get(methodNum);
					
					ArrayList<String> argumentTypeLists = new ArrayList<String>();
					for(int argNum = 0; argNum < evoSuiteMethod.getArgumentLists().size(); argNum++) {
						argumentTypeLists.add(this.gudgeVariableType(evoSuiteMethod.getArgumentLists().get(argNum), evoSuiteTest.getMethodLists()));
					}
					
					ArrayList<AnalyzerMethod> sameMethodNameLists = new ArrayList<AnalyzerMethod>();
					for(int anaNum = 0; anaNum < analyzerMethodLists.size(); anaNum++) {
						if(analyzerMethodLists.get(anaNum).getName().equals(evoSuiteMethod.getMethodName())) {
							sameMethodNameLists.add(analyzerMethodLists.get(anaNum));
						}
					}
					
					if(sameMethodNameLists.size() == 1) {
						if(sameMethodNameLists.get(0).getName().equals("<init>")) {
							if(sameMethodNameLists.get(0).getOwnerClass().getName().equals(evoSuiteMethod.getConstructorClass())) {
								evoSuiteMethod.setAnalyzerMethod(sameMethodNameLists.get(0));
							}
						}else {
							evoSuiteMethod.setAnalyzerMethod(sameMethodNameLists.get(0));
						}
						
					}else {
						for(int sameNum = 0; sameNum < sameMethodNameLists.size(); sameNum++) {
							AnalyzerMethod tmpAnalyzerMethod = sameMethodNameLists.get(sameNum);
							
							if(tmpAnalyzerMethod.getTypeArgumentLists().size() == argumentTypeLists.size()) {
								boolean sameMethodFlag = true;
								for(int argNum = 0; argNum < argumentTypeLists.size(); argNum++) {
									if(!tmpAnalyzerMethod.getTypeArgumentLists().get(argNum).equals(argumentTypeLists.get(argNum))) {
										sameMethodFlag = false;
										break;
									}
								}
								
								if(sameMethodFlag ) {
									evoSuiteMethod.setAnalyzerMethod(tmpAnalyzerMethod);
									break;
								}
							}
						}
					}
					
//					System.out.println(evoSuiteMethod.getStatement());
//					if(evoSuiteMethod.getAnalyzerMethod() != null) {
//						evoSuiteMethod.getAnalyzerMethod().display();
//					}
//					System.out.println("********");
				}
			}
		}
	}
	
	private String gudgeVariableType(String input, ArrayList<EvoSuiteMethod> evoSuiteMethodLists) {
		String result = "";
		
		if(input.equals("true") || input.equals("false")) {
			result = "boolean";
		}else if(isNumber(input)) {
			result = "int";
		}else if(input.contains("\"")) {
			result = "String";
		}else if(input.contains("'")) {
			result = "char";
		}else {
			for(int methodNum = 0; methodNum < evoSuiteMethodLists.size(); methodNum++) {
				String variable = evoSuiteMethodLists.get(methodNum).getReturnVariable();
				if(input.equals(variable)) {
					result = evoSuiteMethodLists.get(methodNum).getReturnType();
					break;
				}
			}
			
			if(result.equals("")) {
				// long, float, double
				if(input.contains("l") || input.contains("L")) {
					result = "long";
				}else if(input.contains("f") || input.contains("F")) {
					result = "float";
				}else {
					result = "double";
				}
			}
		}
		
		return result;
	}
	
	private boolean isNumber(String val) {
		String regex = "\\A[-]?[0-9]+\\z";
		Pattern p = Pattern.compile(regex);
		Matcher m1 = p.matcher(val);
		return m1.find();
	}
	
	private void attachAssertionAndMethod(ArrayList<EvoSuiteClass> evoSuiteClassLists) {
		for(int classNum = 0; classNum < evoSuiteClassLists.size(); classNum++) {
			EvoSuiteClass evoSuiteClass = evoSuiteClassLists.get(classNum);
			
			for(int testNum = 0; testNum < evoSuiteClass.getTestLists().size(); testNum++) {
				EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(testNum);
				
				for(int assertionNum = 0; assertionNum < evoSuiteTest.getAssertionLists().size(); assertionNum++) {
					EvoSuiteAssertion evoSuiteAssertion = evoSuiteTest.getAssertionLists().get(assertionNum);
					
					if(!evoSuiteAssertion.getVariable().equals("")) {
						String variable = evoSuiteAssertion.getVariable();
						
						for(int methodNum = 0; methodNum < evoSuiteTest.getMethodLists().size(); methodNum++) {
							if(evoSuiteTest.getMethodLists().get(methodNum).getReturnVariable().equals(variable)) {
								evoSuiteAssertion.setAssertionTargetMethod(evoSuiteTest.getMethodLists().get(methodNum));
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void analyzeEvoSuiteClass(ArrayList<EvoSuiteClass> evoSuiteClassLists) {
		for(int classNum = 0; classNum < evoSuiteClassLists.size(); classNum++) {
			EvoSuiteClass evoSuiteClass = evoSuiteClassLists.get(classNum);
			
			for(int testNum = 0; testNum < evoSuiteClass.getTestLists().size(); testNum++) {
				EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(testNum);
				ArrayList<String> body = evoSuiteTest.getBody();
				
				for(int bodyNum = 0; bodyNum < body.size(); bodyNum++) {
					String[] splitBracket = body.get(bodyNum).split("[()]");
					if(splitBracket.length > 0) {
						String[] splitSpace = splitBracket[0].split(" +");
						
						if(splitSpace[1].equals("assertEquals")) {
							String assertion = body.get(bodyNum);
							assertion = assertion.replace(" ", "");
							assertion = assertion.replace("assertEquals(", "");
							assertion = assertion.replace(");", "");
							
							String[] splitAssertionArgument = assertion.split(",");
							splitAssertionArgument[0] = splitAssertionArgument[0].replace("(", "");
							splitAssertionArgument[0] = splitAssertionArgument[0].replace(")", "");
							EvoSuiteAssertion evoSuiteAssertion;
							if(splitAssertionArgument[1].contains(".")) {
								evoSuiteAssertion = new EvoSuiteAssertion(splitAssertionArgument[0], "", splitAssertionArgument[1], body.get(bodyNum));
							}else {
								evoSuiteAssertion = new EvoSuiteAssertion(splitAssertionArgument[0], splitAssertionArgument[1], "", body.get(bodyNum));
							}
							
							evoSuiteTest.addAssertionLists(evoSuiteAssertion);
						
						}else if(!splitSpace[1].equals("}") && !splitSpace[1].equals("try") && !splitSpace[1].equals("//")){
							EvoSuiteMethod evoSuiteMethod = new EvoSuiteMethod(body.get(bodyNum));
							evoSuiteTest.addMethodLists(evoSuiteMethod);
							
						}else if(splitSpace[1].equals("try")) {
							EvoSuiteMethod evoSuiteMethod = new EvoSuiteMethod(body.get(bodyNum), "try");
							evoSuiteTest.addMethodLists(evoSuiteMethod);
							
						}else if(splitSpace.length > 2 && splitSpace[2].equals("catch")){
							EvoSuiteMethod evoSuiteMethod = new EvoSuiteMethod(body.get(bodyNum), "catch");
							evoSuiteTest.addMethodLists(evoSuiteMethod);
							
						}
					}
				}
			}
		}
	}
	
	private ArrayList<EvoSuiteClass> createEvoSuiteClassLists(ArrayList<String> inputFileNameLists){
		ArrayList<EvoSuiteClass> evoSuiteClassLists = new ArrayList<EvoSuiteClass>();
		
		for(int fileNum = 0; fileNum < inputFileNameLists.size(); fileNum ++) {
			File inputFile = new File(inputFileNameLists.get(fileNum));
			
			try {
				FileReader fr = new FileReader(inputFile);
				BufferedReader br = new BufferedReader(fr);
				
				String readLine;
				
				String packageName = "";
				ArrayList<EvoSuiteImport> importLists = new ArrayList<EvoSuiteImport>();
				EvoSuiteClass evoSuiteClass = null;
				EvoSuiteTest evoSuiteTest = null;
				
				while((readLine = br.readLine()) != null) {
					if(readLine.length() != 0) {
						String[] splitBracket = readLine.split("[()]");
						String[] splitSpace = splitBracket[0].split(" +");
						
						if(splitSpace.length > 0) {
							if(splitSpace[0].equals("package")) {
								packageName = splitSpace[1].replace(";", "");
								
							}else if(splitSpace[0].equals("import")) {
								EvoSuiteImport evoSuiteImport;
								if(splitSpace[1].equals("static")) {
									evoSuiteImport = new EvoSuiteImport(readLine, splitSpace[2].replace(";", ""));
								}else {
									evoSuiteImport = new EvoSuiteImport(readLine, splitSpace[1].replace(";", ""));
								}
								
								importLists.add(evoSuiteImport);
								
							}else if(splitSpace[0].equals("public")) {
								evoSuiteClass = new EvoSuiteClass(splitSpace[2], packageName, importLists);
								evoSuiteClassLists.add(evoSuiteClass);
								
							}else {
								if(splitSpace.length > 1) {
									if(splitSpace[1].equals("@Test")) {
										evoSuiteTest = new EvoSuiteTest();
										evoSuiteClass.addTestLists(evoSuiteTest);
										
									}else if(splitSpace[1].equals("public")) {
										evoSuiteTest.setMethodName(splitSpace[3]);
										
									}else {
										if(evoSuiteTest != null ) {
											evoSuiteTest.addBody(readLine);
										}
									}
									
									if(evoSuiteTest != null) {
										evoSuiteTest.addContents(readLine);
									}
								}
							}
						
						}
					}
				}
				
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return evoSuiteClassLists;
	}
}
