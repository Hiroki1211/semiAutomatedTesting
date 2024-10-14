package ex06;

public class Executer {

	public int run(String firstName, String lastName, int age, float height, float weight) {
		Human human = new Human(firstName, lastName, age, height, weight);
		float bmi = human.getBmi();
		if(bmi > 25) {
			return this.exercise();
		}else {
			return this.eat();

		}
	}
	
	private int exercise() {
		return -10;
	}
	
	private int eat() {
		return 10;
	}
}
