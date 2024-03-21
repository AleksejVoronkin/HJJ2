package ru.gb.lesson2.tests;


public class TestRunnerDemo {
  public static void main(String[] args) {
    TestRunner.run(TestRunnerDemo.class);
  }

  @Test(order = 2)
  private void test1() {
    System.out.println("test1");
  }

  @Test(order = 1)
  void test2() {
    System.out.println("test2");
  }

  @Test(order = 3)
  void test3() {
    System.out.println("test3");
  }
}
