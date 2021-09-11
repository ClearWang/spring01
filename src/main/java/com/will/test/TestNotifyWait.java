package com.will.test;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 通知唤醒模式
 * 这个例子的目的：
 * 1.明白jdk1.5之前的 等待通知模式的实现原理和实现场景
 * 2.明白synchronized的场景缺陷 从而体会Doug lea在jdk1.5中引入Lock的意义和初衷猜测
 *   意义和初衷猜测：1>synchronized的场景缺陷  2>synchronized早期(jdk1.6之前)有使用性能问题
 **/
public class TestNotifyWait {
  private final static Object ROOM_LOCK = new Object();
  private static Boolean GIVE_TREBLE_MONEY = false;
  private static final HashMap<String,Thread> THREAD_MAP = new HashMap<>();

  public static void main(String[] args) throws InterruptedException {

    ReentrantLock lock = new ReentrantLock();
    //引入condition将不同的大神程序员添加到不同的condition(假设没个大神程序员的内部级别都不一致)
    Condition[] conditions = new Condition[5];
    IntStream.range(0,5).forEach((i)->{
      conditions[i] = lock.newCondition();
      String currentThreadName = "大神级程序员"+i;
      Thread tempThread = new Thread(() -> {
        //synchronized (ROOM_LOCK) {
        lock.lock();
        while (!GIVE_TREBLE_MONEY) {
            System.out.printf("大神级程序员%d:老板不给三倍工资,劳资拒绝加班!\n", i);
            //park本质上底层是通过mutex实现 和sleep一样不会释放锁 wait会释放锁
            //LockSupport.park();
            try {
              conditions[i].await();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            // 所以如果这里要park就需要去掉synchronize
            //try {
            //  //睡觉去 每隔5秒去看下老板有没有同意三倍工资(GIVE_TREBLE_MONEY是不是被设置为true了)
            //  TimeUnit.SECONDS.sleep(2);
            //  //线程进入阻塞态 并释放锁资源
            //  ROOM_LOCK.wait();
            //} catch (InterruptedException e) {
            //  e.printStackTrace();
            //}
        }
        lock.unlock();
        System.out.printf("老板同意三倍工资啦 大神级程序员%d 开始愉快干活...\n", i);
        //}
      }, currentThreadName);
      THREAD_MAP.put(currentThreadName,tempThread);
      tempThread.start();
    });

    System.out.println("=================================================");

    IntStream.range(1,11).forEach((i)->{
      new Thread(()->{
        synchronized (ROOM_LOCK){
          System.out.println("普通程序员"+i+" 开始默默搬砖...");
        }
      },"普通程序员"+i).start();
    });

    TimeUnit.SECONDS.sleep(5);

    //synchronized (ROOM_LOCK){
    //  //这时候老板来了 同意给大神程序员A 3倍工资了
    //  System.out.println("老板我来了,我同意给大神程序员3倍工资的请求了!好好干活吧!");
    //  GIVE_TREBLE_MONEY = true;
    //  // 这里大家思考个问题 如果我想指定只唤醒某一个大神程序员
    //  // 比如说老板只想给大神程序员2出三倍工资 这时候notify就做不到指定叫醒了
    //  // 但是Lock中的condition或者park就可以做到 这也是Lock的优势所在
    //  //ROOM_LOCK.notify();
    //  //LockSupport.unpark(THREAD_MAP.get("大神级程序员2"));
    //}
    new Thread(()->{
      System.out.println("老板我来了,我同意给大神程序员3倍工资的请求了!好好干活吧!");
      GIVE_TREBLE_MONEY = true;
      lock.lock();
      conditions[4].signal();
      lock.unlock();
    }).start();

    TimeUnit.SECONDS.sleep(5);
  }

}
