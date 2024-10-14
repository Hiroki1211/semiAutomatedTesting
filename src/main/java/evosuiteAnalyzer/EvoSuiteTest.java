package evosuiteAnalyzer;

import java.util.ArrayList;

import executionPathExtracter.TraceMethodBlock;

public class EvoSuiteTest {

	private String methodName;
	private ArrayList<String> contents = new ArrayList<String>();
	private ArrayList<String> body = new ArrayList<String>();
	private ArrayList<EvoSuiteMethod> methodLists = new ArrayList<EvoSuiteMethod>();
	private ArrayList<EvoSuiteAssertion> assertionLists = new ArrayList<EvoSuiteAssertion>();
	private TraceMethodBlock traceMethodBlock = null;
	private ArrayList<TraceMethodBlock> traceMethodBlockLists = new ArrayList<TraceMethodBlock>();
	
	public void setTraceMethodBlock(TraceMethodBlock input) {
		traceMethodBlock = input;
	}
	
	public void addTraceMethodBlockLists(TraceMethodBlock input) {
		traceMethodBlockLists.add(input);
	}
	
	public TraceMethodBlock getTraceMethodBlock() {
		return traceMethodBlock;
	}
	
	public ArrayList<TraceMethodBlock> getTraceMethodBlockLists(){
		return traceMethodBlockLists;
	}
	
	public void display() {
		System.out.println("methodName:" + methodName);
		
		for(int i = 0; i < contents.size(); i++) {
			System.out.println(contents.get(i));
		}
		
		System.out.println("******");
		
		for(int i = 0; i < methodLists.size(); i++) {
			methodLists.get(i).display();
		}
		
		System.out.println("******");
		
		for(int i = 0; i < assertionLists.size(); i++) {
			assertionLists.get(i).display();
		}
		
		System.out.println("******");
	}
	
	public void setMethodName(String input) {
		methodName = input;
	}
	
	public void addContents(String input) {
		contents.add(input);
	}
	
	public void addBody(String input) {
		body.add(input);
	}
	
	public void addMethodLists(EvoSuiteMethod input) {
		methodLists.add(input);
	}
	
	public void addAssertionLists(EvoSuiteAssertion input) {
		assertionLists.add(input);
	}

	public String getMethodName() {
		return methodName;
	}
	
	public ArrayList<String> getContents(){
		return contents;
	}
	
	public ArrayList<String> getBody(){
		return body;
	}
	
	public ArrayList<EvoSuiteMethod> getMethodLists(){
		return methodLists;
	}
	
	public ArrayList<EvoSuiteAssertion> getAssertionLists(){
		return assertionLists;
	}
}
