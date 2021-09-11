package com.will.test;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * test lock
 * 这个例子的目的：
 * 1、通过这个我自己实现的这个自定义锁 体会下锁的含义 体会下究竟什么是锁
 *      这里锁的实现可以理解为维护一个状态位 例如这里的state=0表示可以加锁 state=1表示不可以加锁
 *      那么synchronize中的锁是不是这样呢？synchronize中是不是也有通过维护一个类似这样的state状态位来实现锁的呢？
 *      结论是：不全是。当synchronize是轻量锁的时候是通过维护一个对象头中markword中的一个状态为标识实现的。
 *      但是当synchronize是重量锁的时候就不是了 ===> 锁实现的第二种方式：指针标识。
 * 2、抛砖引玉,引出AQS的设计思想
 **/
public class TestLock {

  private static int number;
  /**
   * 这个是我自己实现的锁-内部逻辑很简单
   */
  private static final WKLock wkLock = new WKLock();

  public static void main(String[] args) throws InterruptedException {
    //分配10个线程 去做number+5的操作 并发安全的正确结果是50
    IntStream.range(0,10).forEach((i)->{
      new Thread(()->{
        IntStream.range(0,5).forEach(count2->{
          wkLock.lock();
          ++number;
          wkLock.unlock();
        });
      }).start();
    });

    TimeUnit.SECONDS.sleep(1);
    System.out.println(number);
  }

}
