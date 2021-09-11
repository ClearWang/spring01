package com.will.test;

import com.will.entity.A;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:-UseBiasedLocking  禁用jvm偏向锁
 * -XX:BiasedLockingStartupDelay=0 取消jvm偏向延迟
 * -XX:-UseCompressedOops 禁用jvm指针压缩
 *
 * TimeUnit.SECONDS.sleep(5);
 * System.out.println(Integer.toHexString(obj.hashCode()));
 */

public class TestObjectStructure {
    private volatile static int number;
    private static final MyObject obj = new MyObject();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        Thread t1 = new Thread(()->{
            IntStream.range(0,2).forEach((i)->{
                synCode();
            });
        },"thread1");
        //Thread t2 = new Thread(TestObjectStructure::synCode,"thread2");
        t1.start();
        //t1.join();
        //t2.start();
        //t1.start();

        TimeUnit.SECONDS.sleep(1);
    }

    public static void synCode(){
        synchronized (obj){
            System.out.println("currentThread:"+Thread.currentThread().getName());
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            ++number;
        }
    }

}
