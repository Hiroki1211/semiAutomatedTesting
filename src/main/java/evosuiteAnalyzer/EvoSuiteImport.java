package evosuiteAnalyzer;

public class EvoSuiteImport {

	private String statement;
	private String importClass;
	
	public EvoSuiteImport(String s, String iC) {
		statement = s;
		importClass = iC;
	}
	
	public String getStatement() {
		return statement;
	}
	
	public String getImportClass() {
		return importClass;
	}
}
