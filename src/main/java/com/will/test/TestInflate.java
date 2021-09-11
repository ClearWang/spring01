package com.will.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * test
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:20 11:32
 **/
@Slf4j(topic = "test")
public class TestInflate {
    static  Thread t2;
    static Thread t3;
    static Thread t1;
    static int loopFlag=40;
    public static void main(String[] args) throws InterruptedException {
        //101  可偏向 没有线程偏向
        //a 没有线程偏向---匿名    101偏向锁
        List<MyObject> list = new ArrayList<>();
        t1 = new Thread(){
           @Override
           public void run() {
               for(int i=0;i<loopFlag;i++){
                MyObject a = new MyObject();
                synchronized (a){
                  list.add(a);
                  log.debug(i+" 加锁中 "+ClassLayout.parseInstance(a).toPrintable(a));
                }
               }
               log.debug("============t1 over=============");
               LockSupport.unpark(t2);
           }
       };

        t2 = new Thread(){
            @Override
            public void run() {
                LockSupport.park();
                    for (int i = 0; i < loopFlag ; i++) {
                        MyObject a = list.get(i);
                        log.debug(i+" 加锁前 "+ClassLayout.parseInstance(a).toPrintable(a));
                        synchronized (a){
                            log.debug(i+" 加锁中 "+ClassLayout.parseInstance(a).toPrintable(a));
                        }
                        log.debug(i+" 加锁后 "+ClassLayout.parseInstance(a).toPrintable(a));
                    }
                log.debug("======t2 over=====================================");
                LockSupport.unpark(t3);
            }
        };

        t3 = new Thread(){
            @Override
            public void run() {
                LockSupport.park();
                for (int i = 0; i <loopFlag ; i++) {
                    MyObject a = list.get(i);
                    log.debug(i+" 加锁前 "+ClassLayout.parseInstance(a).toPrintable(a));
                    synchronized (a){
                        log.debug(i+" 加锁中 "+ClassLayout.parseInstance(a).toPrintable(a));
                    }
                    log.debug(i+" 加锁后 "+ClassLayout.parseInstance(a).toPrintable(a));
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t3.join();
      //log.debug("last ======"+ClassLayout.parseInstance(new MyObject()).toPrintable());
        log.debug("last ======"+ClassLayout.parseInstance(new MyObject()).toPrintable());
      //aa();
    }


    public static void aa() throws InterruptedException {
      MyObject a= new MyObject();
      Thread t = new Thread(){
        @Override
        public void run() {
          synchronized (a){
            log.debug(ClassLayout.parseInstance(a).toPrintable());
          }
        }
      };
      t.start();
      t.join();

      Thread t1 = new Thread(){
        @Override
        public void run() {
          synchronized (a){
            log.debug(ClassLayout.parseInstance(a).toPrintable());
          }
        }
      };
      t1.start();
      t1.join();
//        synchronized (a){
//            log.debug(ClassLayout.parseInstance(a).toPrintable());
//        }
    }

}
