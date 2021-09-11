package com.will.test;

/**
 * 垃圾回收及内存状态变化测试
 * @author AnonymousCodeMaker
 * @create 2021:06:28 19:28
 **/
public class TestInMain2 {

  public static void main(String[] args) throws InterruptedException {
      Teacher teacher1 = new Teacher();
      teacher1.setName("king");
      teacher1.setAge(100);
      teacher1.setSex(0);
      //因为teacher1是一个引用类型 所以这里经过15的gc 回收后 teacher1会被放到老年代
      for(int i=0;i<15;i++){
        System.gc();
      }
      Teacher teacher2 = new Teacher();
      teacher2.setName("will");
      teacher2.setAge(18);
      teacher2.setSex(1);
      Thread.sleep(Integer.MAX_VALUE);
  }
}

class Teacher{
    private String name;
    private int age;
    private int sex;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }
}
