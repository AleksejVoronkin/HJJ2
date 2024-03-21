package ru.gb.lesson2.tests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
  public static void run(Class<?> testClass) {
    Object testObj = initTestObj(testClass);

    System.out.println("\nBeforeAll start");
    AnnotatedMethods(testObj, testClass, BeforeAll.class);
    System.out.println("BeforeAll finish");

    List<Method> testMethods = Arrays.stream(testClass.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(Test.class))
            .sorted(Comparator.comparingInt(m -> m.getAnnotation(Test.class).order()))
            .toList();

    for (Method testMethod : testMethods) {
      System.out.println("\nBeforeEach");
      AnnotatedMethods(testObj, testClass, BeforeEach.class);

      Method(testObj, testMethod);
      System.out.println("AfterEach");
      AnnotatedMethods(testObj, testClass, AfterEach.class);
    }

    System.out.println("\nAfterAll start");
    AnnotatedMethods(testObj, testClass, AfterAll.class);
    System.out.println("AfterAll finish");
  }

  private static void AnnotatedMethods(Object obj, Class<?> testClass, Class<? extends Annotation> annotation) {
    Arrays.stream(testClass.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(annotation))
            .forEach(method -> Method(obj, method));
  }

  private static void Method(Object obj, Method method) {
    try {
      method.setAccessible(true);
      method.invoke(obj);
    } catch (Exception e) {
      throw new RuntimeException("Не удалось выполнить метод " + method.getName(), e);
    }
  }

  private static Object initTestObj(Class<?> testClass) {
    try {
      return testClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Не удалось создать объект тест класса", e);
    }
  }
}


