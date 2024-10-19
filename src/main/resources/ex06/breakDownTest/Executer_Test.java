package breakDownTest;

import static org.junit.Assert.*;
import org.junit.Test;

import ex06.Executer;

public class Executer_Test {

  @Test
  public void test0(){
      // execute Target Method
      Executer executer0 = new Executer();
  }

  @Test
  public void test1(){
      Executer executer0 = new Executer();
      // execute Target Method
      int result = executer0.run("Osaka", "Taro", 20, 175.0f, 90.0f);
  }

  @Test
  public void test2(){
      // execute Target Method
      Executer executer1 = new Executer();
  }

  @Test
  public void test3(){
      Executer executer1 = new Executer();
      // execute Target Method
      int result = executer1.run("Osaka", "Taro", 20, 175.0f, 55.0f);
  }

}