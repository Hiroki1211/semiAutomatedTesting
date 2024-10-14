package evosuiteAnalyzer;

import java.util.ArrayList;

public class EvoSuiteClass {

	private String className;
	private String packageName;
	private ArrayList<EvoSuiteImport> importLists = new ArrayList<EvoSuiteImport>();
	private ArrayList<EvoSuiteTest> testLists = new ArrayList<EvoSuiteTest>();
	
	public EvoSuiteClass(String cN, String pN, ArrayList<EvoSuiteImport> iL) {
		className = cN;
		packageName = pN;
		importLists = new ArrayList<EvoSuiteImport>(iL);
	}
	
	public void display() {
		System.out.println("className:" + className);
		System.out.println("packageName:" + packageName);
		System.out.print("importList:");
		for(int i = 0; i < importLists.size(); i++) {
			System.out.print(importLists.get(i).getImportClass() + ", ");
		}
		System.out.println();
	}
	
	public void addTestLists(EvoSuiteTest input) {
		testLists.add(input);
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public ArrayList<EvoSuiteImport> getImportLists(){
		return importLists;
	}
	
	public ArrayList<EvoSuiteTest> getTestLists(){
		return testLists;
	}
}
