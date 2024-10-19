package breakDownTestDataSet;

public class InstanceVariable {

	private String name;
	private String valueType;
	private String getterMethod;
	
	public void setName(String input) {
		name = input;
	}
	
	public void setValueType(String input) {
		valueType = input;
	}
	
	public void setGetterMethod(String input) {
		getterMethod = input;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValueType() {
		return valueType;
	}
	
	public String getGetterMethod() {
		return getterMethod;
	}
}
