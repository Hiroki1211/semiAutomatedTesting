package breakDownTestDataSet;

import java.util.ArrayList;
import java.util.Collections;

import analyzer.AnalyzerVariable;

public class UnitTest {
	
	private String owner;
	
	// for create unitTest
	private ArrayList<Method> constructorLists = new ArrayList<Method>();
	private ArrayList<Method> constructorArgumentLists = new ArrayList<Method>();
	private ArrayList<Method> methodLists = new ArrayList<Method>();
	private ArrayList<Method> argumentMethodLists = new ArrayList<Method>();
	private Method method = null;
	private String assertion;
	private ArrayList<Assignment> assignmentLists = new ArrayList<Assignment>();
	private ArrayList<Method> constructorArrayLists = new ArrayList<Method>();
	
	// for export unitTest
	private ArrayList<String> unitTestStatement = new ArrayList<String>();
	
	// for assignment
	private ArrayList<AnalyzerVariable> analyzerVariableLists = new ArrayList<AnalyzerVariable>();
	
	public void addTestDeclarationUnitTestStatement(String input) {
		unitTestStatement.add(1, input);
	}
	
	public void createUnitTest() {
		unitTestStatement = new ArrayList<String>();
		unitTestStatement.add("@Test");
		
		ArrayList<Method> forCreateMethodLists = new ArrayList<Method>();
		int methodSeqNum = method.getSeqNum();
		
		for(int i = 0; i < constructorLists.size(); i++) {
			forCreateMethodLists.add(constructorLists.get(i));
		}
		
		for(int i = 0; i < constructorArgumentLists.size(); i++) {
			forCreateMethodLists.add(constructorArgumentLists.get(i));
		}
		
		for(int i = 0; i < methodLists.size(); i++) {
			// if(methodLists.get(i).getHasAssignment()) {
				if(methodSeqNum > methodLists.get(i).getSeqNum()) {
					forCreateMethodLists.add(methodLists.get(i));
				}
			//}
		}
		
		for(int i = 0; i < argumentMethodLists.size(); i++) {
			// if(argumentMethodLists.get(i).getHasAssignment()) {
				if(methodSeqNum > argumentMethodLists.get(i).getSeqNum()) {
					forCreateMethodLists.add(argumentMethodLists.get(i));
				}
			// }
		}
		
		for(int i = 0; i < constructorArrayLists.size(); i++) {
			forCreateMethodLists.add(constructorArrayLists.get(i));
		}
		
		for(int i = 0; i < forCreateMethodLists.size(); i++) {
			int minSeqnum = forCreateMethodLists.get(i).getSeqNum();
			int minSeqnumMethodIndex = i;
			
			for(int j = i; j < forCreateMethodLists.size(); j++) {
				if(minSeqnum > forCreateMethodLists.get(j).getSeqNum()) {
					minSeqnum = forCreateMethodLists.get(j).getSeqNum();
					minSeqnumMethodIndex = j;
				}
			}
			
			if(minSeqnumMethodIndex != i) {
				Collections.swap(forCreateMethodLists, i, minSeqnumMethodIndex);
			}
		}
		
		for(int i = 0; i < forCreateMethodLists.size(); i++) {
			unitTestStatement.add("    " + forCreateMethodLists.get(i).getExecuteStatement());
		}
		
		unitTestStatement.add("    " + "// execute Target Method");
		unitTestStatement.add("    " + method.getExecuteStatement());
		
//		unitTestStatement.add("\t" + "// assertion");
//		if(assertion != null) {
//			unitTestStatement.add("\t" + assertion);
//		}
//		
//		unitTestStatement.add("\t" + "// assignment assertion");
//		for(int i = 0; i < assignmentLists.size(); i++){
//			unitTestStatement.add("\t" + assignmentLists.get(i).getExecuteStatement());
//		}
		
		unitTestStatement.add("}");
	}
	
	public void setConstructorLists(ArrayList<Method> input) {
		constructorLists = input;
	}
	
	public void addConstructorArgumentLists(Method input) {
		constructorArgumentLists.add(input);
	}
	
	public void setMethodLists(ArrayList<Method> input) {
		methodLists = input;
	}
	
	public void addArgumentMethodLists(Method input) {
		argumentMethodLists.add(input);
	}
	
	public void setMethod(Method input) {
		method = input;
	}
	
	public void setAssertion(String input) {
		assertion = input;
	}
	
	public void addAssignmentLists(Assignment input) {
		if(!analyzerVariableLists.contains(input.getAnalyzerVariable())) {
			addAnalyzerVariableLists(input.getAnalyzerVariable());
			assignmentLists.add(input);
		}else {
			for(int i = 0; i < analyzerVariableLists.size(); i++) {
				if(input.getAnalyzerVariable() == analyzerVariableLists.get(i)) {
					analyzerVariableLists.remove(i);
					assignmentLists.add(input);
				}
			}
		}
	}
	
	private void addAnalyzerVariableLists(AnalyzerVariable input) {
		analyzerVariableLists.add(input);
	}
	
	public void addConstructorArrayLists(Method input) {
		constructorArrayLists.add(input);
	}
	
	public void setOwner(String input) {
		owner = input;
	}
	
	public ArrayList<Method> getConstructorLists() {
		return constructorLists;
	}
	
	public ArrayList<Method> getConstructorArgumentLists(){
		return constructorArgumentLists;
	}
	
	public ArrayList<Method> getMethodLists(){
		return methodLists;
	}
	
	public ArrayList<Method> getArgumentMethodLists(){
		return argumentMethodLists;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public String getAssertion(){
		return assertion;
	}
	
	public ArrayList<Assignment> getAssinmentLists(){
		return assignmentLists;
	}
	
	public ArrayList<Method> getConstructorArrayLists(){
		return constructorArrayLists;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public ArrayList<String> getUnitTestStatement(){
		return unitTestStatement;
	}
}
