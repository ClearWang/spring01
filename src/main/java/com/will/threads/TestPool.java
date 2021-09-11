package com.will.threads;

import java.util.stream.IntStream;

/**
 * @author AnonymousCodeMaker
 * @create 2021:08:22 10:38
 **/
public class TestPool {

  public static void main(String[] args){
      WKThreadPool wkThreadPool = new WKThreadPool(2,1,(queue,task)->{
        //策略1.一直等待-阻塞式调用
        //try {
        //  queue.put(task);
        //} catch (InterruptedException e) {
        //  e.printStackTrace();
        //}
        //策略2.由主线程调用
        //System.out.println(Thread.currentThread().getName()+"开始执行"+task.getTaskName());
        //task.run();
        //策略3.直接丢弃
        //System.out.println("discard "+task.getTaskName());
        //策略4.抛异常
        throw new RuntimeException();
      });
    //创建五个任务
    IntStream.range(0,5).forEach((i)->{
      try {
        wkThreadPool.execute(new WKTask("task"+(i+1)));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }
}
