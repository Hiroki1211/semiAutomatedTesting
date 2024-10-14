package executionPathExtracter;

import java.io.File;
import java.util.ArrayList;

import analyzer.Analyzer;
import evosuiteAnalyzer.EvoSuiteAnalyzer;
import evosuiteAnalyzer.EvoSuiteAssertion;
import evosuiteAnalyzer.EvoSuiteClass;
import evosuiteAnalyzer.EvoSuiteTest;
import tracer.Lexer;
import tracer.Trace;

public class PathExtracter {

	public static void main(String[] args) {
		PathExtracter pathExtracter = new PathExtracter();
		
		ArrayList<String> inputFileNameLists = pathExtracter.getInputFileNameLists();
		ArrayList<String> inputEvoSuiteTestFileNameLists = pathExtracter.getInputEvoSuiteTestFileNameLists();
		ArrayList<String> inputEvoSuiteTestTraceFileNameLists = pathExtracter.getInputEvoSuiteTraceFileNameLists();
		
		// product code analyze
		Analyzer analyzer = new Analyzer();
		analyzer.run(inputFileNameLists);
		
		// EvoSuite code analyze
		EvoSuiteAnalyzer evoSuiteAnalyzer = new EvoSuiteAnalyzer();
		ArrayList<EvoSuiteClass> evoSuiteClassLists = evoSuiteAnalyzer.run(inputEvoSuiteTestFileNameLists, analyzer.getMethodLists());
		
		// Extract EvoSuite Path
		pathExtracter.evoSuiteTestPathExtract(inputEvoSuiteTestTraceFileNameLists, evoSuiteClassLists);
	}
	
	private ArrayList<String> getInputFileNameLists(){
		ArrayList<String> result = new ArrayList<String>();
		result.add("src/main/resources/ex06/code/Human.java");
		result.add("src/main/resources/ex06/code/Executer.java");
		
		return result;
	}
	
	private ArrayList<String> getInputEvoSuiteTestFileNameLists(){
		ArrayList<String> result = new ArrayList<String>();
		result.add("src/main/resources/ex06/evoSuiteTest/Human_ESTest.java");
		result.add("src/main/resources/ex06/evoSuiteTest/Executer_ESTest.java");
		
		return result;
	}
	
	private ArrayList<String> getInputEvoSuiteTraceFileNameLists(){
		ArrayList<String> result = new ArrayList<String>();
		result.add("src/main/resources/ex06/evoSuiteTrace/Human_ESTest.json");
		result.add("src/main/resources/ex06/evoSuiteTrace/Executer_ESTest.json");
		
		return result;
	}
	
	private ArrayList<TraceMethodBlock> getTraceMethodBlockLists(ArrayList<Trace> traceLists){
		ArrayList<TraceMethodBlock> traceMethodBlockLists = new ArrayList<TraceMethodBlock>();
		TraceMethodBlock traceMethodBlock = null;
		
		for(int traceNum = 0; traceNum < traceLists.size(); traceNum++) {
			Trace trace = traceLists.get(traceNum);
			if(trace.getEvent().equals("METHOD_ENTRY")) {
				traceMethodBlock = new TraceMethodBlock();
				traceMethodBlockLists.add(traceMethodBlock);
			}
			
			traceMethodBlock.addTraceLists(trace);
		}
		
//		for(int i = 0; i < traceMethodBlockLists.size(); i++) {
//			traceMethodBlock = traceMethodBlockLists.get(i);
//			Trace trace = traceMethodBlock.getTraceLists().get(0);
//			System.out.println(trace.getCname() + ":" + trace.getMname() + ":" + trace.getSeqNum() + ":" + trace.getLine());
//		}
		
		return traceMethodBlockLists;
	}
	
	private void evoSuiteTestPathExtract(ArrayList<String> inputTraceFileNameLists, ArrayList<EvoSuiteClass> evoSuiteClassLists) {
		for(int inputFileNum = 0; inputFileNum < inputTraceFileNameLists.size(); inputFileNum++) {
			File file = new File(inputTraceFileNameLists.get(inputFileNum));
			Lexer lexer = new Lexer(file);
			ArrayList<Trace> traceLists = lexer.getTraceLists();
			ArrayList<TraceMethodBlock> traceMethodBlockLists = this.getTraceMethodBlockLists(traceLists);
			ArrayList<Integer> borderSeqNumLists = new ArrayList<Integer>();
			
			// detect evoSuiteClass
			EvoSuiteClass evoSuiteClass = null;
			TraceMethodBlock firstTraceMethodBlock = traceMethodBlockLists.get(0);
			Trace firstTrace = firstTraceMethodBlock.getTraceLists().get(0);
			for(int evoSuiteClassNum = 0; evoSuiteClassNum < evoSuiteClassLists.size(); evoSuiteClassNum++) {
				if(firstTrace.getCname().contains(evoSuiteClassLists.get(evoSuiteClassNum).getClassName())) {
					evoSuiteClass = evoSuiteClassLists.get(evoSuiteClassNum);
				}
			}
			
			// 1. EvoSuite のテスト自体の登録
			for(int blockNum = 0; blockNum < traceMethodBlockLists.size(); blockNum++) {
				TraceMethodBlock traceMethodBlock = traceMethodBlockLists.get(blockNum);
				Trace trace = traceMethodBlock.getTraceLists().get(0);
				String methodName = trace.getMname();
				ArrayList<EvoSuiteTest> evoSuiteTestLists = evoSuiteClass.getTestLists();
				
				for(int testNum = 0; testNum < evoSuiteTestLists.size(); testNum++) {
					EvoSuiteTest evoSuiteTest = evoSuiteTestLists.get(testNum);
					if(evoSuiteTest.getMethodName().equals(methodName)) {
						evoSuiteTest.setTraceMethodBlock(traceMethodBlock);
						borderSeqNumLists.add(trace.getSeqNum());
						break;
					}
				}
			}
			
//			System.out.println(evoSuiteClass.getClassName());
//			System.out.println("border:" + borderSeqNumLists);
			
			// 2. EvoSuite のメソッドの振り分け
			for(int blockNum = 0; blockNum < traceMethodBlockLists.size(); blockNum++) {
				TraceMethodBlock traceMethodBlock = traceMethodBlockLists.get(blockNum);
				Trace trace = traceMethodBlock.getTraceLists().get(0);
				if(trace.getFilename().contains(evoSuiteClass.getClassName())) {
					continue;
				}
				
				// System.out.println("a:" + trace.getFilename() + ":" + trace.getMname() + ":" + trace.getSeqNum() + ":" + trace.getLine());
				
				for(int borderNum = 0; borderNum < borderSeqNumLists.size(); borderNum++) {
					if(borderNum == borderSeqNumLists.size() - 1) {
						if(borderSeqNumLists.get(borderNum) >= trace.getSeqNum()) {
							break;
						}
						
						EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(borderNum);
						evoSuiteTest.addTraceMethodBlockLists(traceMethodBlock);
					}else{
						if(trace.getSeqNum() < borderSeqNumLists.get(borderNum + 1) && trace.getSeqNum() > borderSeqNumLists.get(borderNum)) {
							EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(borderNum);
							evoSuiteTest.addTraceMethodBlockLists(traceMethodBlock);
							break;
						}else if(trace.getSeqNum() == borderSeqNumLists.get(borderNum)) {
							break;
						}
					}
				}
			}
			
//			System.out.println("method block:");
//			for(int i = 0; i < evoSuiteClass.getTestLists().size(); i++) {
//				EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(i);
//				System.out.println(evoSuiteTest.getMethodName());
//				
//				for(int j = 0; j < evoSuiteTest.getTraceMethodBlockLists().size(); j++) {
//					TraceMethodBlock traceMethodBlock = evoSuiteTest.getTraceMethodBlockLists().get(j);
//					System.out.println("\t" + traceMethodBlock.getTraceLists().get(0).getMname() + ":" + traceMethodBlock.getTraceLists().get(0).getSeqNum());
//				}
//			}
			
			// 3. not need method delete
			ArrayList<EvoSuiteTest> evoSuiteTestLists = evoSuiteClass.getTestLists();
			for(int testNum = 0; testNum < evoSuiteTestLists.size(); testNum++) {
				int assertSeqNum = 0;
				EvoSuiteTest evoSuiteTest = evoSuiteTestLists.get(testNum);
				ArrayList<Trace> testTraceLists = evoSuiteTest.getTraceMethodBlock().getTraceLists();
				
				for(int testTraceNum = 0; testTraceNum < testTraceLists.size(); testTraceNum++) {
					Trace trace = testTraceLists.get(testTraceNum);
					
					if(trace.getEvent().equals("CALL") && trace.getAttr().getName().equals("assertEquals")) {
						assertSeqNum = trace.getSeqNum();
						break;
					}
				}
				
				if(assertSeqNum != 0) {
					ArrayList<TraceMethodBlock> assertTraceMethodBlockLists = evoSuiteTest.getTraceMethodBlockLists();
					int traceMethodBlockLength = assertTraceMethodBlockLists.size();
					for(int blockNum = 0; blockNum < traceMethodBlockLength; blockNum++) {
						if(assertTraceMethodBlockLists.get(blockNum).getTraceLists().get(0).getSeqNum() > assertSeqNum) {
							assertTraceMethodBlockLists.remove(blockNum);
							blockNum -= 1;
							traceMethodBlockLength -=1;
						}
					}
				}
				
			}
			
			// delete assertion first method
			for(int testNum = 0; testNum < evoSuiteClass.getTestLists().size(); testNum++) {
				EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(testNum);
				
				if(evoSuiteTest.getAssertionLists().size() > 0) {
					EvoSuiteAssertion evoSuiteAssertion = evoSuiteTest.getAssertionLists().get(0);
					if(evoSuiteAssertion.getVariable().equals("")) {
						int lastMethodBlockNum = 0;
						int lastMethodBlockSeqNum = 0;
						for(int blockNum = 0; blockNum < evoSuiteTest.getTraceMethodBlockLists().size(); blockNum++) {
							if(evoSuiteTest.getTraceMethodBlockLists().get(blockNum).getTraceLists().get(0).getSeqNum() > lastMethodBlockSeqNum) {
								lastMethodBlockNum = blockNum;
								lastMethodBlockSeqNum = evoSuiteTest.getTraceMethodBlockLists().get(blockNum).getTraceLists().get(0).getSeqNum();
							}
						}
						
						evoSuiteTest.getTraceMethodBlockLists().remove(lastMethodBlockNum);
					}
				}
			}
			
			// sort Method Block
			for(int testNum = 0; testNum < evoSuiteClass.getTestLists().size(); testNum++) {
				EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(testNum);
				
				ArrayList<TraceMethodBlock> sortTraceMethodBlockLists = evoSuiteTest.getTraceMethodBlockLists();
				for(int i = 0; i < sortTraceMethodBlockLists.size(); i++) {
					for(int j = 0; j < sortTraceMethodBlockLists.size() - 1; j++) {
						TraceMethodBlock frontTraceMethodBlock = sortTraceMethodBlockLists.get(j);
						TraceMethodBlock backTraceMethodBlock = sortTraceMethodBlockLists.get(j+1);
						
						if(frontTraceMethodBlock.getTraceLists().get(0).getSeqNum() > backTraceMethodBlock.getTraceLists().get(0).getSeqNum()) {
							sortTraceMethodBlockLists.set(j, backTraceMethodBlock);
							sortTraceMethodBlockLists.set(j+1, frontTraceMethodBlock);
						}
					}
				}
			}
			
//			System.out.println("method block:");
//			for(int i = 0; i < evoSuiteClass.getTestLists().size(); i++) {
//				EvoSuiteTest evoSuiteTest = evoSuiteClass.getTestLists().get(i);
//				System.out.println(evoSuiteTest.getMethodName());
//				
//				for(int j = 0; j < evoSuiteTest.getTraceMethodBlockLists().size(); j++) {
//					TraceMethodBlock traceMethodBlock = evoSuiteTest.getTraceMethodBlockLists().get(j);
//					System.out.println("\t" + traceMethodBlock.getTraceLists().get(0).getMname() + ":" + traceMethodBlock.getTraceLists().get(0).getSeqNum());
//				}
//			}
		}
	}
}
