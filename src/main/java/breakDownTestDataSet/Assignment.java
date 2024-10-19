package breakDownTestDataSet;

import analyzer.AnalyzerVariable;

public class Assignment {

	private AnalyzerVariable analyzerVariable;
	private String executeStatement;
	
	public Assignment(AnalyzerVariable a, String s) {
		analyzerVariable = a;
		executeStatement = s;
	}
	
	public AnalyzerVariable getAnalyzerVariable() {
		return analyzerVariable;
	}
	
	public String getExecuteStatement() {
		return executeStatement;
	}
}
