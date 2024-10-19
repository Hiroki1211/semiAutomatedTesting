package breakDownTestDataSet;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Array {

	private String id;
	private String name;
	private String type;
	private ArrayList<String> value = new ArrayList<String>();
	
	private String owner;
	private String index;
	private ArrayList<Method> methodLists = new ArrayList<Method>();
	
	public void createConstructor(int seqnum) {
		Method method = new Method();
		String executeStatement = type + " " + name + "[] = new " + type + "[" + index + "];";
		method.setOwner(owner);
		method.setExecuteStatement(executeStatement);
		method.setReturnValueType(owner);
		method.setSeqnum(seqnum);
		
		methodLists.add(method);
	}
	
	public void createStoreMethod(int seqnum, String targetIndex, String value) {
		Method method = new Method();
		String executeStatement = name + "[" + targetIndex + "] = " + value + ";";
		method.setExecuteStatement(executeStatement);
		method.setOwner(owner);
		method.setReturnValueType(owner);
		method.setSeqnum(seqnum);
		
		methodLists.add(method);
	}
	
	public String getDeclareStatement() {
		String declareStatement = type + " " + name + " = {";
		for(int i = 0; i < value.size(); i++) {
			if(i == value.size() - 1) {
				declareStatement += value.get(i) + "};";
			}else {
				declareStatement += value.get(i) + ", ";
			}
		}
		return declareStatement;
	}
	
	public Array clone() {
		Array array = new Array();
		array.setId(this.getId());
		array.setName(this.getName());
		array.setType(this.getType());
		array.setValue(this.getValue());
		array.setId(index);
		array.setOwner(owner);
		array.setMethodLists(methodLists);
		
		return array;
	}
	
	public void setId(String input) {
		id = input;
	}

	public void setName(String input) {
		name = input;
	}
	
	public void setType(String input) {
		String[] split = input.split(Pattern.quote("."));
		type = split[split.length-1];
		owner = input.replace("[]", "");
	}
	
	private void setOwner(String input) {
		owner = input;
	}
	
	public void setIndex(String input) {
		index = input;
	}
	
	private void setMethodLists(ArrayList<Method> input) {
		methodLists = new ArrayList<Method>(input);
	}
	
	public void addMethodLists(Method method) {
		methodLists.add(method);
	}
	
	public void addValue(String input, int index) {
		if(value.size() == index) {
			value.add(input);
		}else {
			value.add(index, input);
		}
	}
	
	public void setValue(ArrayList<String> input) {
		value = input;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public ArrayList<Method> getMethodLists(){
		return methodLists;
	}
	
	public ArrayList<String> getValue(){
		return value;
	}
}
