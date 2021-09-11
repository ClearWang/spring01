package com.will.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * test
 *
 * 场景1：
 * 单线程多次Synchronize加锁场景：
 * 场景2：
 * 多线程Synchronize顺序交替执行场景(这里只模拟交替一次)：
 * 场景3：
 * 多线程Synchronize资源竞争场景：
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:15 19:13
 **/
@Slf4j(topic = "test")
public class TestSynchronize {
  public static void main(String[] args) throws InterruptedException {
    List<MyObject> list = new ArrayList<>();
    int flag = 40;

    Thread t1 = new Thread(()->{
      IntStream.range(0,flag).forEach((i)->{
        MyObject obj = new MyObject();
        synchronized (obj){
          list.add(obj);
          log.debug("i="+i+",currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(obj).toPrintable());
        }
      });
    },"thread1");
    Thread t2 = new Thread(()->{
      IntStream.range(0,flag).forEach((i)->{
        MyObject obj = list.get(i);
        log.debug("i="+i+",before currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj){
          log.debug("i="+i+",currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(obj).toPrintable());
        }
        log.debug("i="+i+",after currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(obj).toPrintable());
      });
    },"thread2");
    Thread t3 = new Thread(()->{
      IntStream.range(0,flag).forEach((i)->{
        MyObject obj = list.get(i);
        log.debug("i="+i+",before currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(obj).toPrintable());
        synchronized (obj){
          log.debug("i="+i+",currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(obj).toPrintable());
        }
        log.debug("i="+i+",after currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(obj).toPrintable());
      });
    },"thread3");
    t1.start();
    //让主线程阻塞 等待t1线程执行完
    t1.join();
    t2.start();
    //让主线程阻塞 等待t2线程执行完
    t2.join();
    t3.start();
    //让主线程阻塞 等待t3线程执行完
    t3.join();
    log.debug("last currentThread:"+Thread.currentThread().getName()+":"+ClassLayout.parseInstance(new MyObject()).toPrintable());
  }
}
