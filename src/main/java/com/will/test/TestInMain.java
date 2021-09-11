package com.will.test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author AnonymousCodeMaker
 * @create 2021:06:28 17:25
 **/
public class TestInMain {
  public static void main(String[] args) {
    Object obj = new Object();
    WeakReference weakReference = new WeakReference(obj);
    SoftReference softReference = new SoftReference(obj);
    PhantomReference phantomReference = new PhantomReference(obj, new ReferenceQueue());
    obj = null;
    //自动装箱 等价于Integer.valueOf(1)
    Integer a = 1;
    //a.intValue()
    Integer b = 2;
    //swap(a,b);
    ////本质这个a的值打印的是a.inValue()
    //System.out.printf("swap a=%d,b=%d\n",a,b);
    //swap2(a,b);
    //System.out.printf("swap2 a=%d,b=%d\n",a,b);
    swap3(a,b);
    System.out.printf("swap3 a=%d ,b=%d",a,b);
  }

  public static void swap(Integer a,Integer b){
    Integer t = a;
    a = b;
    b = t;
  }

  public static void swap2(Integer a,Integer b){
    try {
      Field value = Integer.class.getDeclaredField("value");
      value.setAccessible(true);
      int t = a;
      value.set(a,b);//Integer(2)
      //value.set(b,a);//Integer.valueOf(1)==>自动装箱 变成数组129的位置的值 拿到的是已经修改为2的数值
      //传入的是两个Object 会触发Integer的自动装箱操作
      value.set(b,new Integer(t));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void swap3(Integer a,Integer b){
    System.out.printf("swap3 a=%d,b=%d",b,a);
    System.exit(1);
  }
}
