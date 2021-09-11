package com.will.test;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * 自定义锁
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:13 10:40
 **/
public class WKLock {

  /**
   *  默认是0 认为是自由状态 可以被加锁
   */
  private volatile int state;
  private static final Unsafe unsafe = getUnsafe();
  /**
   *  这里这个变量的作用在于获取当前变量state在内存中的地址
   */
  private static long stateOffset;

  static{
    try {
      assert unsafe != null;
      stateOffset = unsafe.objectFieldOffset
          (WKLock.class.getDeclaredField("state"));
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  public void lock(){
    assert unsafe != null;
    //this表示当前对象 stateOffset表示state在内存中的地址
    //这里的意思是拿0和state比较 如果相同(state也是0) 表示锁是自由状态 可以加锁
    //于是去试图修改state为1
    while(!unsafe.compareAndSwapInt(this, stateOffset, 0, 1)){
      //加锁失败会进入到这里空转
      //System.out.println(Thread.currentThread().getName()+"begin waiting...");
      //想一想这里有哪些优化方案可以减少while空转 消耗cpu
      //提示sleep一下行不行？如果可以,sleep多久合适呢？
      //yield让出cpu资源行不行?
      //其实无论是sleep还是yield都不是最优方案
      //最优方案是jdk并发大神doug lea提出的aqs锁机制(队列+park) 回头讲源码的时候我们会再好好体会下大神的牛逼逻辑和思想
    }
  }

  public void unlock(){
    state = 0;
  }

  public static Unsafe getUnsafe() {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      return (Unsafe)field.get(null);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
