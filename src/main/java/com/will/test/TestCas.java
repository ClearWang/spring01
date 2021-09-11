package com.will.test;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.stream.IntStream;

/**
 * 测试类
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:09 19:05
 **/
public class TestCas {
  public static void main(String[] args) throws InterruptedException {
    LongAdder longAdder = new LongAdder();
    IntStream.range(0,10).forEach(t->{
      IntStream.range(0,100).forEach(count->{
        new Thread(()->{
          IntStream.range(0,10).forEach(count2->{
            longAdder.increment();
          });
        },"thread-"+count).start();
      });
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.printf("执行第%d次 number=%d\n",t+1,longAdder.intValue());
      longAdder.reset();
    });

    Thread.sleep(1000);
  }
}
