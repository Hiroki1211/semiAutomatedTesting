/*
 * This file was automatically generated by test
 * Sun Oct 13 15:13:14 GMT 2024
 */

package ex06;

import org.junit.Test;
import static org.junit.Assert.*;
import ex06.Human;

public class Human_ESTest {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      Human human0 = new Human("ex06.Human", "", 0, 0.0F, 0);
      float float0 = human0.getWeight();
      assertEquals(0, human0.getAge());
      assertEquals("", human0.getLastName());
      assertEquals(0.0F, float0, 0.01F);
      assertEquals(0.0F, human0.getAppropriateWeight(), 0.01F);
      assertEquals("ex06.Human", human0.getFirstName());
      assertEquals(0.0F, human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      Human human0 = new Human("mL", "mL", 0, 0, (-2092.876F));
      float float0 = human0.getWeight();
      assertEquals(Float.NEGATIVE_INFINITY, human0.getBmi(), 0.01F);
      assertEquals(0.0F, human0.getHeight(), 0.01F);
      assertEquals(0.0F, human0.getAppropriateWeight(), 0.01F);
      assertEquals((-2092.876F), float0, 0.01F);
      assertEquals(0, human0.getAge());
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      Human human0 = new Human((String) null, (String) null, 0, 0, (-1129.5239F));
      human0.getLastName();
      assertEquals(0.0F, human0.getHeight(), 0.01F);
      assertEquals(0, human0.getAge());
      assertEquals(Float.NEGATIVE_INFINITY, human0.getBmi(), 0.01F);
      assertEquals(0.0F, human0.getAppropriateWeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      Human human0 = new Human("Vk2", "", 0, 0, 0);
      String string0 = human0.getLastName();
      assertEquals(0, human0.getAge());
      assertEquals(0.0F, human0.getWeight(), 0.01F);
      assertEquals("", string0);
      assertEquals(0.0F, human0.getHeight(), 0.01F);
      assertEquals("Vk2", human0.getFirstName());
      assertEquals(0.0F, human0.getAppropriateWeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      Human human0 = new Human("cl^K~B%}/N>+'ZR", "cl^K~B%}/N>+'ZR", 0, 0, 0);
      float float0 = human0.getHeight();
      assertEquals(0.0F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(0.0F, human0.getWeight(), 0.01F);
      assertEquals(0.0F, float0, 0.01F);
      assertEquals(0, human0.getAge());
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      Human human0 = new Human("b&/ {8A", "b&/ {8A", (-3254), (-3254), 0.0F);
      float float0 = human0.getHeight();
      assertEquals((-3254.0F), float0, 0.01F);
      assertEquals((-3254), human0.getAge());
      assertEquals(23294.736F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(0.0F, human0.getBmi(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      Human human0 = new Human((String) null, (String) null, 0, 0, 0);
      human0.getFirstName();
      assertEquals(0, human0.getAge());
      assertEquals(0.0F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(0.0F, human0.getWeight(), 0.01F);
      assertEquals(0.0F, human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      Human human0 = new Human("", "", 25, 25, 25);
      human0.getFirstName();
      assertEquals(25.0F, human0.getHeight(), 0.01F);
      assertEquals(1.375F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(400.0F, human0.getBmi(), 0.01F);
      assertEquals(25, human0.getAge());
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      Human human0 = new Human("", (String) null, (-183), (-183), 0.0F);
      float float0 = human0.getBmi();
      assertEquals((-183), human0.getAge());
      assertEquals(0.0F, float0, 0.01F);
      assertEquals(73.6758F, human0.getAppropriateWeight(), 0.01F);
      assertEquals((-183.0F), human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      Human human0 = new Human("=ySGD#,*rq<9qY*", "HK WN2w!?;q T", (-4068), (-4068), (-4068));
      float float0 = human0.getBmi();
      assertEquals("=ySGD#,*rq<9qY*", human0.getFirstName());
      assertEquals((-2.4582105F), float0, 0.01F);
      assertEquals((-4068.0F), human0.getHeight(), 0.01F);
      assertEquals(36406.973F, human0.getAppropriateWeight(), 0.01F);
      assertEquals("HK WN2w!?;q T", human0.getLastName());
      assertEquals((-4068), human0.getAge());
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      Human human0 = new Human("Y37]\"c", "Y37]\"c", 0, 0, 0);
      float float0 = human0.getAppropriateWeight();
      assertEquals(0.0F, human0.getHeight(), 0.01F);
      assertEquals(0, human0.getAge());
      assertEquals(0.0F, float0, 0.01F);
      assertEquals(0.0F, human0.getWeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      Human human0 = new Human("", "", 0, 0, 0.0F);
      int int0 = human0.getAge();
      assertEquals(0.0F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(0, int0);
      assertEquals(0.0F, human0.getWeight(), 0.01F);
      assertEquals(0.0F, human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      Human human0 = new Human("@mF(q", "y3Me^P%LA,3~", (-1381), (-1.0F), (-1381));
      int int0 = human0.getAge();
      assertEquals(0.0022F, human0.getAppropriateWeight(), 0.01F);
      assertEquals((-1381), int0);
      assertEquals((-1.0F), human0.getHeight(), 0.01F);
      assertEquals("@mF(q", human0.getFirstName());
      assertEquals("y3Me^P%LA,3~", human0.getLastName());
      assertEquals((-1.381E7F), human0.getBmi(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test13()  throws Throwable  {
      Human human0 = new Human("Qfy", "Qfy", 2591, 2591, 2591);
      float float0 = human0.getHeight();
      assertEquals(14769.218F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(2591.0F, float0, 0.01F);
      assertEquals(3.8595138F, human0.getBmi(), 0.01F);
      assertEquals(2591, human0.getAge());
  }

  @Test(timeout = 4000)
  public void test14()  throws Throwable  {
      Human human0 = new Human("Qfy", "Qfy", 2591, 2591, 2591);
      int int0 = human0.getAge();
      assertEquals(3.8595138F, human0.getBmi(), 0.01F);
      assertEquals(2591, int0);
      assertEquals(14769.218F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(2591.0F, human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test15()  throws Throwable  {
      Human human0 = new Human("Qfy", "Qfy", 2591, 2591, 2591);
      human0.getFirstName();
      assertEquals(14769.218F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(3.8595138F, human0.getBmi(), 0.01F);
      assertEquals(2591, human0.getAge());
      assertEquals(2591.0F, human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test16()  throws Throwable  {
      Human human0 = new Human("Qfy", "Qfy", 2591, 2591, 2591);
      float float0 = human0.getAppropriateWeight();
      assertEquals(14769.218F, float0, 0.01F);
      assertEquals(3.8595138F, human0.getBmi(), 0.01F);
      assertEquals(2591, human0.getAge());
      assertEquals(2591.0F, human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test17()  throws Throwable  {
      Human human0 = new Human("Qfy", "Qfy", 2591, 2591, 2591);
      human0.getLastName();
      assertEquals(14769.218F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(3.8595138F, human0.getBmi(), 0.01F);
      assertEquals(2591, human0.getAge());
      assertEquals(2591.0F, human0.getHeight(), 0.01F);
  }

  @Test(timeout = 4000)
  public void test18()  throws Throwable  {
      Human human0 = new Human("Qfy", "Qfy", 2591, 2591, 2591);
      float float0 = human0.getWeight();
      assertEquals(14769.218F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(2591.0F, human0.getHeight(), 0.01F);
      assertEquals(2591.0F, float0, 0.01F);
      assertEquals(3.8595138F, human0.getBmi(), 0.01F);
      assertEquals(2591, human0.getAge());
  }

  @Test(timeout = 4000)
  public void test19()  throws Throwable  {
      Human human0 = new Human("Qfy", "Qfy", 2591, 2591, 2591);
      float float0 = human0.getBmi();
      assertEquals(2591, human0.getAge());
      assertEquals(14769.218F, human0.getAppropriateWeight(), 0.01F);
      assertEquals(3.8595138F, float0, 0.01F);
      assertEquals(2591.0F, human0.getHeight(), 0.01F);
  }
}
