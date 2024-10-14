package integration;

import static org.junit.Assert.*;
import org.junit.Test;

import ex06.Executer;

public class IntegrationTest {

	@Test
	public void test0() {
		Executer executer = new Executer();
		int result = executer.run("Osaka", "Taro", 20, 175.0f, 90.0f);
		assertEquals(result, -10);
	}
	
	@Test
	public void test1() {
		Executer executer = new Executer();
		int result = executer.run("Osaka", "Taro", 20, 175.0f, 55.0f);
		assertEquals(result, 10);
	}
	
}
