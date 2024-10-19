package breakDownTestDataSet;

import java.util.ArrayList;

public class UnitTestGroup {

	private ArrayList<UnitTest> unitTestLists = new ArrayList<UnitTest>();
	private String packageName;
	private String className;
	private String owner;
	
	public void addUnitTestLists(UnitTest input) {
		unitTestLists.add(input);
	}
	
	public void setpackageName(String input) {
		packageName = input;
	}
	
	public void setClassName(String input) {
		className = input;
	}
	
	public void setOwner(String input) {
		owner = input;
	}
	
	public ArrayList<UnitTest> getUnitTestLists(){
		return unitTestLists;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getOwner() {
		return owner;
	}
}
