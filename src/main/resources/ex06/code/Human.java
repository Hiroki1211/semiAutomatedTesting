package ex06;

public class Human {

	private String firstName;
	private String lastName;
	private int age;
	private float height;	//cm
	private float weight; //kg
	
	// BMI
	private float bmi;
	private float appropriateWeight; 
	
	public Human(String fN, String lN, int a, float h, float w) {
		firstName = fN;
		lastName = lN;
		age = a;
		height = h;
		weight = w;
		bmi = this.calculateBMI();
		appropriateWeight = this.calculateAppropriateWeight();
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getAge() {
		return age;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public float getBmi() {
		return bmi;
	}
	
	public float getAppropriateWeight() {
		return appropriateWeight;
	}
	
	private float calculateBMI() {
		return weight / height / height * 10000;
	}
	
	private float calculateAppropriateWeight() {
		return height * height * 22 / 10000; 
	}
	
}

