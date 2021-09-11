package com.will.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 这个例子就是想让大家先记住几个结论：
 * 1.synchronize是非公平锁
 * 2.notify在hotspot中的各个jdk版本下是有序唤醒 FIFO-先进先出
 * 3.notify不是及时唤醒
 * 4.notifyAll在hotspot中的各个jdk版本中也是有序唤醒 只不过是逆序 FILO-现进后出
 */
public class TestNotify{

  /**
   * 存放进入等待状态的线程
   */
  private static final List<String> WAIT_LIST = new ArrayList<>();
  /**
   * 存放已经唤醒的线程
   */
  private static final List<String> NOTIFY_LIST = new ArrayList<>();

  public static void main(String[] args) throws InterruptedException {
    //开启10个线程
    IntStream.range(1,11).forEach((i)->{
      String currentThreadName = "thread"+i;
      new Thread(()->{
        synchronized (TestLock.class){
          try {
            WAIT_LIST.add(currentThreadName);
            System.out.println(currentThreadName+" is waiting....");

            TestLock.class.wait();

            NOTIFY_LIST.add(currentThreadName);
            System.out.println(currentThreadName+" is notified!");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      },currentThreadName).start();
    });

    //这里睡眠的目的是让分配的线程都先进入wait状态
    TimeUnit.SECONDS.sleep(1);

    //唤醒上面的10个线程
    //IntStream.range(0,10).forEach((i)->{
    //  synchronized (TestLock.class){
    //    //问题1: 为什么notify需要synchronize？
    //    //因为notify的底层实现是通过关联ObjectMonitor对象实现的 而ObjectMonitor对象是Synchronize发生重量锁膨胀后产生的
    //    TestLock.class.notify();
    //  }
    //  //问题2: 为什么这里加上sleep后唤醒的顺序就变成有序的 而去掉就变成无序的了
    //  //这里大家要记住两个结论：
    //  //   1、notify在hotspot源码中是有序唤醒的
    //  //   2、notify不是及时唤醒(不是唤醒了立马就可以得到执行)
    //  //至于这里为什么屏蔽掉sleep代码就会变成无序
    //  //   是因为去掉sleep上面的synchronized方法就会产生锁竞争(synchronize是非公平锁-所谓的公平就是遵守先入先出规则)
    //  //   假设这里唤醒的是A,那么A就会和主线程争夺这个锁资源
    //  //   如果是主线程先拿到这个锁资源 那么主线程就会优先得到执行 就会唤醒B 这时候竞争的对象就变成了A,B,当前主线程
    //  //   悲观情况下 最终的竞争会变成A,B,....J总共10个线程竞争锁资源 谁先拿到锁 谁先执行
    //  //那么加上sleep怎么就变成有序的呢？
    //  //   因为加上sleep之后就能保证到当前被主线程唤醒的线程-假设是A,A会立马获取到锁资源(主线程被sleep了不会去和A去抢夺锁资源)
    //  //   同理后面的第二个被唤醒的线程也会立马获取到锁资源.
    //  //try {
    //  //  TimeUnit.MILLISECONDS.sleep(100);
    //  //} catch (InterruptedException e) {
    //  //  e.printStackTrace();
    //  //}
    //});

    synchronized (TestLock.class){
      //问题3: notifyAll在hotspot中也是顺序唤醒吗？如果是,是FIFO-先进先出吗?
      TestLock.class.notifyAll();
    }
    //这里睡眠的目的是让所有的线程都先得到唤醒
    TimeUnit.SECONDS.sleep(1);
    System.out.println("waitList:"+WAIT_LIST.toString());
    System.out.println("notifyList:"+NOTIFY_LIST.toString());
  }
}
