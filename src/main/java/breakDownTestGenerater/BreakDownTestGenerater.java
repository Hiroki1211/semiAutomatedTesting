package breakDownTestGenerater;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import analyzer.*;
import breakDownTestDataSet.*;
import tracer.Lexer;
import tracer.Trace;
import tracer.ValueOption;

public class BreakDownTestGenerater {
	
	private ArrayList<Trace> traceLists;
	private static String inputTraceFileName = "trace.json";
	private static String packageName = "breakDownTest";
	private static String evoSuiteTracePath = "src/test/resources/EvoSuite/";
	private static String breakDownTracePath = "src/test/resources/breakDown/";
	
	private ArrayList<String> excludeOwner = this.getExcludeOwner();

	public BreakDownTestGenerater(ArrayList<Trace> t) {
		traceLists = t;
	}
	
	public static void main(String[] argv) {
		File inputTraceFile = new File(inputTraceFileName);
		Lexer lexer = new Lexer(inputTraceFile);
		ArrayList<Trace> traceLists = lexer.getTraceLists();
		Analyzer analyzer = new Analyzer();
		BreakDownTestGenerater executer = new BreakDownTestGenerater(traceLists);
		ArrayList<String> analyzeTargetLists = executer.getAnalyzeFile();
		if(analyzeTargetLists.size() != 0) {
			analyzer.run(analyzeTargetLists);
			ArrayList<AnalyzerMethod> analyzerMethodLists = analyzer.getMethodLists();
			ArrayList<AnalyzerVariable> analyzerVariableLists = analyzer.getVariableLists();
			executer.run(analyzerMethodLists, analyzerVariableLists);
			executer.createTraceFolder();
		}else {
			System.out.println("code does not exist");
		}
	}
	
	private void createTraceFolder() {
		File dir = new File(evoSuiteTracePath);
		dir.mkdir();
		dir = new File(breakDownTracePath);
		dir.mkdir();
	}
	
	public ArrayList<String> getAnalyzeFile(){
		String path = "src/main/java/";
		ArrayList<String> result = new ArrayList<String>();
		
		File dir = new File(path);
		File[] files = dir.listFiles();
		
		ArrayList<String> filePathLists = new ArrayList<String>();
		
		for(int i = 0; i < files.length; i++) {
			String filePath = files[i].getPath();
			
			if(!filePath.contains("trace.json")) {
				if(!filePath.contains(".java") && !filePath.contains(".class")) {
					filePathLists.add(filePath);
				}
			}
		}
		
		while(filePathLists.size() > 0) {
			File pathDir = new File(filePathLists.get(0));
			filePathLists.remove(0);
			
			File[] pathDirFiles = pathDir.listFiles();
			
			for(int i = 0; i < pathDirFiles.length; i++) {
				String pathFilePath = pathDirFiles[i].getPath();
				
				if(pathFilePath.contains(".java")) {
					result.add(pathFilePath);
				}else if(!pathFilePath.contains(".class")){
					filePathLists.add(pathFilePath);
				}
			}
			
		}

		return result;
	}
	
	public ArrayList<String> getExcludeOwner(){
		ArrayList<String> excludeOwner = new ArrayList<String>();
		excludeOwner.add("void");
		excludeOwner.add("boolean");
		excludeOwner.add("char");
		excludeOwner.add("double");
		excludeOwner.add("float");
		excludeOwner.add("int");
		excludeOwner.add("java.lang.String");
		
		return excludeOwner;
	}
	
	public void run(ArrayList<AnalyzerMethod> analyzerMethodLists, ArrayList<AnalyzerVariable> analyzerVariableLists) {
		ArrayList<UnitTest> unitTestLists = createUnitTestLists(analyzerMethodLists, analyzerVariableLists);
		ArrayList<UnitTestGroup> unitTestGroupLists = createUnitTestGroupLists(unitTestLists);
		createExternalFile(unitTestGroupLists);
	}
	
	private void createExternalFile(ArrayList<UnitTestGroup> unitTestGroupLists) {
//		LocalDateTime nowDate = LocalDateTime.now();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String formatNowDate = dtf.format(nowDate);
        
        // create directory
//        String directoryPathName = "src/test/java/breakDownTest" + formatNowDate;
        String directoryPathName = "src/test/java/breakDownTest";
        File dir = new File(directoryPathName);
        dir.mkdir();
        
//        String packageName = "breakDownTest" + formatNowDate;
        String packageName = "breakDownTest";
		
        // create file
		for(int i = 0; i < unitTestGroupLists.size(); i++) {
			UnitTestGroup unitTestGroup = unitTestGroupLists.get(i);
			if(!unitTestGroup.getOwner().equals("java.lang.String") && !unitTestGroup.getOwner().equals("java.lang.Integer")) {
				String fileName = unitTestGroup.getClassName() + "_Test";
				File file = new File(directoryPathName + "/" + fileName + ".java");
				file.setExecutable(true);
				file.setReadable(true);
				file.setWritable(true);
	
				try {
					FileWriter fw = new FileWriter(file);
					fw.write("package " + packageName + ";\n");
					fw.write("\n");
					fw.write("import static org.junit.Assert.*;\n");
					fw.write("import org.junit.Test;\n");
					fw.write("\n");
					
					ArrayList<String> ownerLists = new ArrayList<String>();
					ArrayList<Method> unitTestMethodLists = new ArrayList<Method>();
					ArrayList<UnitTest> unitTestLists = unitTestGroup.getUnitTestLists();
					
					for(int j = 0; j < unitTestLists.size(); j++) {
						UnitTest unitTest = unitTestLists.get(j);
						ArrayList<Method> constructorLists = unitTest.getConstructorLists();
						ArrayList<Method> constructorArgumentLists = unitTest.getConstructorArgumentLists();
						ArrayList<Method> methodLists = unitTest.getMethodLists();
						ArrayList<Method> argumentMethodLists = unitTest.getArgumentMethodLists();
						ArrayList<Method> arrayMethodLists = unitTest.getConstructorArrayLists();
						
						for(int k = 0; k < constructorLists.size(); k++) {
							unitTestMethodLists.add(constructorLists.get(k));
						}
						
						for(int k = 0; k < constructorArgumentLists.size(); k++) {
							unitTestMethodLists.add(constructorArgumentLists.get(k));
						}
						
						for(int k = 0; k < methodLists.size(); k++) {
							unitTestMethodLists.add(methodLists.get(k));
						}
						
						for(int k = 0; k < argumentMethodLists.size(); k++) {
							unitTestMethodLists.add(argumentMethodLists.get(k));
						}
						
						for(int k = 0; k < arrayMethodLists.size(); k++) {
							unitTestMethodLists.add(arrayMethodLists.get(k));
						}
						
						unitTestMethodLists.add(unitTest.getMethod());
					}
					
					for(int j = 0; j < unitTestMethodLists.size(); j++) {
						Method unitTestMethod = unitTestMethodLists.get(j);
						String forOwner = unitTestMethod.getOwner().replace("[]", "");
						
						if(ownerLists.size() == 0) {
							if(!excludeOwner.contains(forOwner)) {
								ownerLists.add(forOwner);
							}
						}else {
							if(!ownerLists.contains(unitTestMethod.getOwner())) {
								if(!excludeOwner.contains(forOwner)) {
									ownerLists.add(forOwner);
								}
							}
						}
						
						if(!ownerLists.contains(unitTestMethod.getReturnValueOwner()) && !unitTestMethod.getReturnValueOwner().equals("")) {
							String forOwnerList = unitTestMethod.getReturnValueOwner().replace("[]", "");
							if(!excludeOwner.contains(forOwnerList)) {
								ownerLists.add(forOwnerList);
							}
						}
					}
					
					for(int j = 0; j < ownerLists.size(); j++) {
						fw.write("import " + ownerLists.get(j) + ";\n");
					}
					
					fw.write("\n");
					fw.write("public class " + fileName + " {\n");
					fw.write("\n");
					for(int j = 0; j < unitTestGroup.getUnitTestLists().size(); j++) {
						UnitTest unitTest = unitTestGroup.getUnitTestLists().get(j);
						ArrayList<String> unitTestStatementLists = unitTest.getUnitTestStatement();
						for(int k = 0; k < unitTestStatementLists.size(); k++) {
							fw.write("  " + unitTestStatementLists.get(k) + "\n");
						}
						fw.write("\n");
					}
					
					fw.write("}");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private ArrayList<UnitTestGroup> createUnitTestGroupLists(ArrayList<UnitTest> unitTestLists) {
		for(int unitTestNum = 0; unitTestNum < unitTestLists.size(); unitTestNum++) {
			unitTestLists.get(unitTestNum).createUnitTest();
		}
		
		ArrayList<UnitTestGroup> unitTestGroupLists = new ArrayList<UnitTestGroup>();
		
		for(int i = 0; i < unitTestLists.size(); i++) {
			UnitTest unitTest = unitTestLists.get(i);
			
			if(unitTestGroupLists.size() == 0) {
				unitTest.addTestDeclarationUnitTestStatement("public void test0(){");
				
				UnitTestGroup unitTestGroup = new UnitTestGroup();
				unitTestGroup.addUnitTestLists(unitTest);
				unitTestGroup.setOwner(unitTest.getOwner());
				String[] split = unitTest.getOwner().split(Pattern.quote("."));
				String className = split[split.length - 1];
				unitTestGroup.setClassName(className);
				unitTestGroup.setpackageName(packageName);
				unitTestGroupLists.add(unitTestGroup);
			}else {
				boolean unitTestGroupRegisterFlag = false;
				
				for(int j = 0; j < unitTestGroupLists.size(); j++) {
					if(unitTest.getOwner().equals(unitTestGroupLists.get(j).getOwner())){
						unitTestGroupRegisterFlag = true;
						UnitTestGroup targetUnitTestGroup = unitTestGroupLists.get(j);
						
						unitTest.addTestDeclarationUnitTestStatement("public void test" + String.valueOf(targetUnitTestGroup.getUnitTestLists().size()) + "(){");
						
						targetUnitTestGroup.addUnitTestLists(unitTest);
						break;
					}
				}
				
				if(!unitTestGroupRegisterFlag) {
					unitTest.addTestDeclarationUnitTestStatement("public void test0(){");
					
					UnitTestGroup unitTestGroup = new UnitTestGroup();
					unitTestGroup.addUnitTestLists(unitTest);
					unitTestGroup.setOwner(unitTest.getOwner());
					String[] split = unitTest.getOwner().split(Pattern.quote("."));
					String className = split[split.length - 1];
					unitTestGroup.setClassName(className);
					unitTestGroup.setpackageName(packageName);
					unitTestGroupLists.add(unitTestGroup);
				}
			}
		}
		
		return unitTestGroupLists;
	}
	
	private ArrayList<UnitTest> createUnitTestLists(ArrayList<AnalyzerMethod> analyzerMethodLists, ArrayList<AnalyzerVariable> analyzerVariableLists) {
		// from dataSet ArrayLists
		ArrayList<Array> arrayLists = new ArrayList<Array>();
		ArrayList<Instance> instanceLists = new ArrayList<Instance>();
		ArrayList<UnitTest> unitTestLists = new ArrayList<UnitTest>();
		
		// for Instance
		boolean createInstanceFlag = false;
		
		// for method
		String methodName = "";
		String methodOwner = "";
		String methodCalledFrom = "";
		String methodType = "";
		ValueOption methodValueOptionForId = new ValueOption();
		ArrayList<ValueOption> params = new ArrayList<ValueOption>();	
		int seqNum = 0;
		
		// for array
		String arrayId = "";
		String arrayIndex = "";
		String arrayType = "";
		String index = "";
		
		// for assignment statement
		ValueOption targetInstance = null;
		ArrayList<Method> methodLists = new ArrayList<Method>();
	
		boolean belongSameClassFlag = false;
		
		for(int i = 0; i < traceLists.size(); i++) {
			Trace trace = traceLists.get(i);
			
			switch(trace.getEvent()) {
				case "CALL":
					methodName = trace.getAttr().getName();
					methodOwner = trace.getAttr().getOwner();
					methodCalledFrom = trace.getFilename();
					methodType = trace.getAttr().getMethodtype();
					methodValueOptionForId = trace.getValueOption();
					seqNum = trace.getSeqNum();
					if(trace.getAttr().getOwner().equals(trace.getCname())) {
						belongSameClassFlag = true;
					}
					
					break;
					
				case "CALL_PARAM":
					ValueOption paramValueOption = trace.getValueOption();
					if(trace.getValuetype().equals("float")) {
						String tmpValue = paramValueOption.getValue();
						tmpValue = tmpValue + "f";
						paramValueOption.setValue(tmpValue);
					}
					params.add(paramValueOption);
					break;
					
				case "CALL_RETURN":
					// コンストラクタ以外
					if(!createInstanceFlag && !belongSameClassFlag) {
						
						AnalyzerMethod AnalyzerMethod = null;
						for(int analyzeNum = 0; analyzeNum < analyzerMethodLists.size(); analyzeNum++) {
							AnalyzerMethod targetAnalyzerMethod = analyzerMethodLists.get(analyzeNum);
							String[] splitAnalyzer = methodOwner.split(Pattern.quote("."));
							String analyzerClassName = splitAnalyzer[splitAnalyzer.length -1];
							
							if(targetAnalyzerMethod.getName().equals(methodName) && targetAnalyzerMethod.getOwnerClass().getName().equals(analyzerClassName)) {
								AnalyzerMethod = targetAnalyzerMethod;
								break;
							}
						}
					
						if(AnalyzerMethod != null && AnalyzerMethod.getAccessModifier().equals("public")) {
							if(methodType.equals("instance")){
								
								// createMethod
							 	Method method = this.createMethod(methodValueOptionForId, methodName, methodOwner, params, trace, seqNum);
								methodLists.add(method);
							 	
								// create Method executeStatement
								String executeStatement = this.createMethodExecuteStatement(method, instanceLists, arrayLists, params);
								method.setExecuteStatement(executeStatement);
								
								// createUnitTest
								Instance methodInstance = this.createUnitTest(methodOwner, method, instanceLists, arrayLists, unitTestLists);
								
								// add Method to Instance
								this.addMethodToInstance(method, methodCalledFrom, methodInstance, methodLists);
								
								// create Instance 
								if(trace.getValuetype().equals("java.lang.Object")) {
									this.createInstance(trace, method, instanceLists, methodInstance, arrayLists);
								}
								
							}
						}
						
						// 初期化
						params = new ArrayList<ValueOption>();
					}
					
					belongSameClassFlag = false;
					
					break;
					
				case "NEW_OBJECT":
					createInstanceFlag = true;
					
					break;
					
				case "NEW_OBJECT_CREATED":
					createInstanceFlag = false;
					
					// create Instance
					Method instanceMethod = this.createObjectInstance(trace, instanceLists, params, arrayLists, methodLists);
					
					instanceMethod.setId(trace.getValueOption().getId());
					instanceMethod.setOwnerValueOption(trace.getValueOption());
					ArrayList<ValueOption> executeConstructorParams = instanceMethod.getParams();
					this.createObjectUnitTest(methodOwner, executeConstructorParams, instanceLists, instanceMethod, unitTestLists);
					
					
//					// create UnitTest
//					for(int recordNum = 0; recordNum < record; recordNum++) {
//						// 1. create Method
//						Method instanceMethod = this.createObjectInstanceMethod(params, recordNum, trace, methodOwner, methodCalledFrom);
//						methodLists.add(instanceMethod);
//						
//						ArrayList<ValueOption> executeConstructorParams = instanceMethod.getParams();
//						String executeStatement = this.createObjectInstanceMethodExecuteStatement(instanceMethod, executeConstructorParams, instanceLists);
//						instanceMethod.setExecuteStatement(executeStatement);
//						
//						// 2. create UnitTest
//						this.createObjectUnitTest(methodOwner, executeConstructorParams, instanceLists, instanceMethod, unitTestLists);
//					}
					
					// 初期化
					params = new ArrayList<ValueOption>();
					
					break;
				
				case "NEW_ARRAY":
					
					index = trace.getValueOption().getValue();
					
					
					break;
				
				case "NEW_ARRAY_RESULT":					
					
					Array array = new Array();
					ValueOption arrayValueOption = trace.getValueOption();
					array.setId(arrayValueOption.getId());
					array.setType(arrayValueOption.getType());
					array.setIndex(index);
					array.setName("array" + arrayLists.size());
					array.createConstructor(trace.getSeqNum());
					
					arrayLists.add(array);
					
					break;
					
				case "ARRAY_STORE":
					
					ValueOption arrayStoreValueOption = trace.getValueOption();
					arrayId = arrayStoreValueOption.getId();
					arrayType = arrayStoreValueOption.getType();
					
					
					break;
					
				case "ARRAY_STORE_INDEX":
					
					ValueOption arrayStoreValueOptionIndex = trace.getValueOption();
					arrayIndex = arrayStoreValueOptionIndex.getValue();		
					
					break;
					
				case "ARRAY_STORE_VALUE":
					
					Array storeArray = this.getArrayFromId(arrayId, arrayLists);
					storeArray.setId(arrayId);
					storeArray.setType(arrayType);
					storeArray.addValue(trace.getValueOption().getValue(), Integer.valueOf(arrayIndex));
					storeArray.createStoreMethod(trace.getSeqNum(), arrayIndex, trace.getValueOption().getValue());
					
					break;
					
				case "PUT_INSTANCE_FIELD":
					// インスタンスid・メソッドの名前・変数名
					targetInstance= trace.getValueOption();
					
					break;
					
				case "PUT_INSTANCE_FIELD_VALUE":
					// メソッドの名前・変数名・変数の値
					String targetMethodName = trace.getMname();
					String variableName = trace.getAttr().getName();
					ValueOption targetVariable = trace.getValueOption();
					if(!targetVariable.getValue().equals("")) {
						
						String targetVariableValue = targetVariable.getValue();
						String targetOwner = trace.getAttr().getOwner();
						String[] targetTmpSplit = targetOwner.split(Pattern.quote("."));
						targetOwner = targetTmpSplit[targetTmpSplit.length - 1];
						
						// 変数の特定
						AnalyzerVariable analyzerVariable = null;
						for(int targetAnalyzerVarNum = 0; targetAnalyzerVarNum < analyzerVariableLists.size(); targetAnalyzerVarNum++) {
							String targetAnalyzerVariable = analyzerVariableLists.get(targetAnalyzerVarNum).getName();
							String targetAnalyzerVariableOwner = analyzerVariableLists.get(targetAnalyzerVarNum).getOwnerClass().getName();
							if(targetAnalyzerVariableOwner.equals(targetOwner) && targetAnalyzerVariable.equals(variableName)) {
								analyzerVariable = analyzerVariableLists.get(targetAnalyzerVarNum);
								break;
							}
						}
							
						// UnitTestListsから対象のUnitTestを特定・assertion文の追加
						if(analyzerVariable != null) {
							for(int targetUnitTestNum = 0; targetUnitTestNum < unitTestLists.size(); targetUnitTestNum++) {
								UnitTest targetUnitTest = unitTestLists.get(targetUnitTestNum);
								Method targetUnitTestMethod = targetUnitTest.getMethod();

								if(targetUnitTestMethod.getId().equals(targetInstance.getId()) && targetUnitTestMethod.getName().equals(targetMethodName) && (analyzerVariable.getGetterMethod() != null || analyzerVariable.getAccessModifier().equals("public"))) {
									// assertion文の作成
									String targetInstanceName = this.getInstanceFromId(targetUnitTest.getMethod().getId(), instanceLists, targetUnitTest.getOwner()).getName();
									String assertionStatement = "";
									if(analyzerVariable.getAccessModifier().equals("public")) {
										assertionStatement = "assertEquals(" + targetVariableValue + ", " + targetInstanceName + "." + analyzerVariable.getName() + "());";
									}else {
										assertionStatement = "assertEquals(" + targetVariableValue + ", " + targetInstanceName + "." + analyzerVariable.getGetterMethod().getName() + "());";
									}
									Assignment assignment = new Assignment(analyzerVariable, assertionStatement);
									targetUnitTest.addAssignmentLists(assignment);
									for(int methodNum = 0; methodNum < methodLists.size(); methodNum++) {
										Method targetAssertionMethod = methodLists.get(methodNum);
										if(targetAssertionMethod.getName().equals(targetUnitTestMethod.getName()) && targetAssertionMethod.getOwner().equals(targetUnitTestMethod.getOwner())) {
											targetAssertionMethod.setHasAssignment(true);
										}
									}
									
									break;
								}
							}
						}
					}
					
				break;
			}
		}
		
		
		for(int unitTestNum = 0; unitTestNum < unitTestLists.size(); unitTestNum++) {
			UnitTest unitTest = unitTestLists.get(unitTestNum);
			Method method = unitTest.getMethod();
			Instance methodInstance = this.getInstanceFromId(method.getId(), instanceLists);
			// 4. メソッドで使用するインスタンスのメソッドを実行
			if(methodInstance != null) {
				ArrayList<Method> methodInstanceMethodLists = new ArrayList<Method>(methodInstance.getMethodLists());
				unitTest.setMethodLists(methodInstanceMethodLists);
			}
			// 5. メソッドの引数で使用するインスタンスのメソッドを実行
			for(int paramNum = 0; paramNum < method.getParams().size(); paramNum++) {
				ValueOption valueOption = method.getParams().get(paramNum);
				if(!valueOption.getId().equals("")) {
					Instance argumentInstance = this.getInstanceFromId(valueOption.getId(), instanceLists);
					if(argumentInstance != null) {
						ArrayList<Method> argumentInstanceMethodLists = argumentInstance.getMethodLists();
						for(int argumentInstanceMethodNum = 0; argumentInstanceMethodNum < argumentInstanceMethodLists.size(); argumentInstanceMethodNum++) {
							Method argumentMethod = argumentInstanceMethodLists.get(argumentInstanceMethodNum);
							unitTest.addArgumentMethodLists(argumentMethod);
						}
					}
				}
			}
		}
		
		return unitTestLists;
	}
	
	private Method createMethod(ValueOption methodValueOptionForId, String methodName, String methodOwner, ArrayList<ValueOption> params, Trace trace, int seqNum) {
		Method method = new Method();
		method.setId(methodValueOptionForId.getId());
		method.setName(methodName);
		method.setOwner(methodOwner);
		method.setOwnerValueOption(methodValueOptionForId);
		method.setSeqnum(seqNum);
		for(int j = 0; j < params.size(); j++) {
			method.addParams(params.get(j));
		}
		method.setReturnValueType(trace.getAttr().getType());
		if(!trace.getAttr().getType().equals("void")) {
			method.setReturnValue(trace.getValueOption());
		}
		
		return method;
	}
	
	private String createMethodExecuteStatement(Method method, ArrayList<Instance> instanceLists, ArrayList<Array> arrayLists, ArrayList<ValueOption> params) {
		String executeStatement = "";
		if(!method.getReturnValueType().equals("void")) {
			executeStatement = method.getReturnValueType() + " result = ";
		}
		
		Instance executeMethodInstance = this.getInstanceFromId(method.getId(), instanceLists);
		if(executeMethodInstance != null) {
			executeStatement += executeMethodInstance.getName() + "." + method.getName() + "(";
			if(method.getParams().size() == 0) {
				executeStatement += ");";
			}else {
				for(int executeParamNum = 0; executeParamNum < method.getParams().size(); executeParamNum++) {
					String param = "";
					if(!method.getParams().get(executeParamNum).getValue().equals("")) {
						param = method.getParams().get(executeParamNum).getValue();
					}else {
						Instance instance = this.getInstanceFromId(method.getParams().get(executeParamNum).getId(), instanceLists);
						if(instance != null) {
							param = instance.getName();
						}
						
						Array array = this.getArrayFromId(method.getParams().get(executeParamNum).getId(), arrayLists);
						if(array != null) {
							param = array.getName();
						}
					}
					
					if(executeParamNum == params.size() - 1) {
						executeStatement += param + ");";
					}else {
						executeStatement += param + ", ";
					}
				}
			}
		}
		
		return executeStatement;
	}
	
	private Instance createUnitTest(String methodOwner, Method method, ArrayList<Instance> instanceLists, ArrayList<Array> arrayLists, ArrayList<UnitTest> unitTestLists) {
		UnitTest unitTest = new UnitTest();
		unitTest.setOwner(methodOwner);
		// 1. メソッドで使用するインスタンスのコンストラクタを宣言
		Instance methodInstance = this.getInstanceFromId(method.getId(), instanceLists);
		if(methodInstance != null) {
			unitTest.setConstructorLists(methodInstance.getConstructorLists());
		}
		// 2. メソッドの引数で使用するインスタンスの宣言
		for(int paramNum = 0; paramNum < method.getParams().size(); paramNum++) {
			ValueOption valueOption = method.getParams().get(paramNum);
			if(!valueOption.getId().equals("")) {
				Instance argumentInstance = this.getInstanceFromId(valueOption.getId(), instanceLists);
				if(argumentInstance != null) {
					ArrayList<Method> constructorArgumentConstructorLists = argumentInstance.getConstructorLists();
					for(int j = 0; j < constructorArgumentConstructorLists.size(); j++) {
						unitTest.addConstructorArgumentLists(constructorArgumentConstructorLists.get(j));
					}
				}
			}
		}
		// 3. メソッドの引数で使用する配列の宣言
		for(int paramNum = 0; paramNum < method.getParams().size(); paramNum++) {
			ValueOption valueOption = method.getParams().get(paramNum);
			if(!valueOption.getId().equals("")) {
				Array argumentArray = this.getArrayFromId(valueOption.getId(), arrayLists);
				if(argumentArray != null) {
					ArrayList<Method> argumentArrayMethodLists = argumentArray.getMethodLists();
					for(int arrayNum = 0; arrayNum < argumentArrayMethodLists.size(); arrayNum++) {
						unitTest.addConstructorArrayLists(argumentArrayMethodLists.get(arrayNum));
					}
				}
			}
		}
//		// 4. メソッドで使用するインスタンスのメソッドを実行
//		if(methodInstance != null) {
//			ArrayList<Method> methodInstanceMethodLists = new ArrayList<Method>(methodInstance.getMethodLists());
//			unitTest.setMethodLists(methodInstanceMethodLists);
//		}
//		// 5. メソッドの引数で使用するインスタンスのメソッドを実行
//		for(int paramNum = 0; paramNum < method.getParams().size(); paramNum++) {
//			ValueOption valueOption = method.getParams().get(paramNum);
//			if(!valueOption.getId().equals("")) {
//				Instance argumentInstance = this.getInstanceFromId(valueOption.getId(), instanceLists);
//				if(argumentInstance != null) {
//					ArrayList<Method> argumentInstanceMethodLists = argumentInstance.getMethodLists();
//					for(int argumentInstanceMethodNum = 0; argumentInstanceMethodNum < argumentInstanceMethodLists.size(); argumentInstanceMethodNum++) {
//						Method argumentMethod = argumentInstanceMethodLists.get(argumentInstanceMethodNum);
//						unitTest.addArgumentMethodLists(argumentMethod);
//					}
//				}
//			}
//		}
		// 6. メソッドを実行
		unitTest.setMethod(method);
		// 7. アサーションを追加
		String assertionStatement;
		if(method.getReturnValue() != null && method.getReturnValue().getValue() != null) {
			if(!method.getReturnValue().getValue().equals("")) {
				assertionStatement = this.createAssertion(method.getReturnValue().getValue());
				unitTest.setAssertion(assertionStatement);
			}
		}

		// 8. unitTestListsに追加
		unitTestLists.add(unitTest);
		
		return methodInstance;
	}
	
	private void addMethodToInstance(Method method, String methodCalledFrom, Instance methodInstance, ArrayList<Method> methodLists) {
		
		if(methodInstance != null) {
			Method tmpMethod = methodInstance.addMethodLists(method);
			methodLists.add(tmpMethod);
		}
	
	}
	
	private void createInstance(Trace trace, Method method, ArrayList<Instance> instanceLists, Instance methodInstance, ArrayList<Array> arrayLists) {
		Instance instance = new Instance();
		instance.setId(trace.getValueOption().getId());
		instance.setOwner(method.getOwner(), instanceLists.size());
		instanceLists.add(instance);
		if(methodInstance != null) {
			instance.addConstructorParamInstanceLists(methodInstance);
		}
		instance.setConstructorParams(method.getParams());
		
		String methodExecuteStatement = method.getExecuteStatement();
		methodExecuteStatement = methodExecuteStatement.replace("result", instance.getName());
		
		Method instanceMethod = method.clone();
		instanceMethod.setExecuteStatement(methodExecuteStatement);
		
		instance.createConstructorStatement(instanceLists, arrayLists, instanceMethod);
	}
	
	private Method createObjectInstance(Trace trace, ArrayList<Instance> instanceLists, ArrayList<ValueOption> params, ArrayList<Array> arrayLists, ArrayList<Method> methodLists) {
		Instance instance = new Instance();				
		
		instance = new Instance();
		ValueOption instanceValueOption = trace.getValueOption();
		instance.setOwner(instanceValueOption.getType(), instanceLists.size());
		instance.setId(instanceValueOption.getId());
		for(int constructorParamNum = 0; constructorParamNum < params.size(); constructorParamNum++) {
			instance.addConstructorParams(params.get(constructorParamNum));
		}
		
		Method method = instance.createConstructorStatement(instanceLists, arrayLists, trace.getSeqNum());
		methodLists.add(method);
		instanceLists.add(instance);
		
		return method;
	}
	
//	private Method createObjectInstanceMethod(ArrayList<ArrayList<ValueOption>> params, int recordNum, Trace trace, String methodOwner, String methodCalledFrom) {
//		Method instanceMethod = new Method();
//		instanceMethod.setName("<init>");
//		for(int j = 0; j < params.size(); j++) {
//			instanceMethod.addParams(params.get(j).get(recordNum));
//		}
//		instanceMethod.setOwner(trace.getValue().getValueOptionLists().get(recordNum).getType());
//		instanceMethod.setReturnValueType(methodOwner);
//		instanceMethod.setReturnValue(trace.getValue().getValueOptionLists().get(recordNum));
//		instanceMethod.setId(trace.getValue().getValueOptionLists().get(recordNum).getId());
//		instanceMethod.setCalledFrom(methodCalledFrom);
//		instanceMethod.setOwnerValueOption(trace.getValue().getValueOptionLists().get(recordNum));
//		instanceMethod.setSeqnum(trace.getSeqnum().get(recordNum));
//		
//		return instanceMethod;
//	}
//	
//	private String createObjectInstanceMethodExecuteStatement(Method instanceMethod, ArrayList<ValueOption> executeConstructorParams, ArrayList<Instance> instanceLists) {
//		Instance tmpInstance = this.getInstanceFromId(instanceMethod.getId(), instanceLists, instanceMethod.getOwner());
//		String executeStatement = instanceMethod.getReturnValueType() + " " + tmpInstance.getName() +" = new " + instanceMethod.getReturnValueType() + "(";
//		for(int exConParamNum = 0; exConParamNum < executeConstructorParams.size(); exConParamNum++) {
//			String addParam = "";
//			if(executeConstructorParams.get(exConParamNum).getValue().equals("")) {
//				String paramInstanceId = executeConstructorParams.get(exConParamNum).getId();
//				Instance paramInstance = this.getInstanceFromId(paramInstanceId, instanceLists);
//				addParam = paramInstance.getName();
//			}else {
//				addParam = executeConstructorParams.get(exConParamNum).getValue();
//			}
//			
//			if(exConParamNum == executeConstructorParams.size() - 1) {
//				executeStatement += addParam;
//			}else {
//				executeStatement += addParam + ", ";
//			}
//		}
//		executeStatement += ");";
//		
//		
//		tmpInstance.addConstructorLists(instanceMethod);
//		
//		return executeStatement;
//	}
	
	private void createObjectUnitTest(String methodOwner, ArrayList<ValueOption> executeConstructorParams, ArrayList<Instance> instanceLists, Method instanceMethod, ArrayList<UnitTest> unitTestLists) {
		UnitTest instanceUnitTest = new UnitTest();
		instanceUnitTest.setOwner(methodOwner);
		for(int exConParamNum = 0; exConParamNum < executeConstructorParams.size(); exConParamNum++) {
			// 2a. constructor の引数のオブジェクトのコンストラクタ実行
			ValueOption executeConstructorParam = executeConstructorParams.get(exConParamNum);
			if(executeConstructorParam.getValue().equals("")) {
				Instance executeConstructorParamInstance = this.getInstanceFromId(executeConstructorParam.getId(), instanceLists);
				if(executeConstructorParamInstance != null) {
					for(int j = 0; j < executeConstructorParamInstance.getConstructorLists().size(); j++) {
						instanceUnitTest.addConstructorArgumentLists(executeConstructorParamInstance.getConstructorLists().get(j));
						// 2b. constructor の引数のオブジェクトのインスタンスのメソッドを実行
						instanceUnitTest.addArgumentMethodLists(executeConstructorParamInstance.getMethodLists().get(j));
					}
				}
			}
			
		}
		
		// 2c. methodの実行
		instanceUnitTest.setMethod(instanceMethod);
		
		unitTestLists.add(instanceUnitTest);
	}
	
	private Instance getInstanceFromId(String id, ArrayList<Instance> instanceLists) {
		Instance instance = null;
		for(int i = 0; i < instanceLists.size(); i++) {
			Instance targetInstance = instanceLists.get(i);
			if(targetInstance.getId().equals(id)) {
				instance = targetInstance;
				break;
			}
		}
		
		return instance;
	}
	
	private Instance getInstanceFromId(String id, ArrayList<Instance> instanceLists, String owner) {
		Instance instance = null;
		for(int i = 0; i < instanceLists.size(); i++) {
			Instance targetInstance = instanceLists.get(i);
			if(targetInstance.getId().equals(id) && targetInstance.getOwner().equals(owner)) {
				instance = targetInstance;
				break;
			}
		}
		
		return instance;
	}
	
	public Array getArrayFromId(String id, ArrayList<Array> arrayLists) {
		Array array = null;		
		for(int i = 0; i < arrayLists.size(); i++) {
			Array targetArray = arrayLists.get(i);
			if(targetArray.getId().equals(id)) {
				array = targetArray;
				break;
			}
		}
				
		return array;
	}
	
	private String createAssertion(String returnValue){
		String assertionStatement = "assertEquals(result, " + returnValue + ");";
		return assertionStatement;
	}
	
}
