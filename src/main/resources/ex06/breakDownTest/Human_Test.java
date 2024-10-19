package breakDownTest;

import static org.junit.Assert.*;
import org.junit.Test;

import ex06.Human;

public class Human_Test {

  @Test
  public void test0(){
      // execute Target Method
      Human human2 = new Human("Osaka", "Taro", 20, 175.0f, 90.0f);
  }

  @Test
  public void test1(){
      Human human2 = new Human("Osaka", "Taro", 20, 175.0f, 90.0f);
      // execute Target Method
      float result = human2.getBmi();
  }

  @Test
  public void test2(){
      // execute Target Method
      Human human3 = new Human("Osaka", "Taro", 20, 175.0f, 55.0f);
  }

  @Test
  public void test3(){
      Human human3 = new Human("Osaka", "Taro", 20, 175.0f, 55.0f);
      // execute Target Method
      float result = human3.getBmi();
  }

}