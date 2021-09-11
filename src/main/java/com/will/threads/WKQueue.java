package com.will.threads;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 存放空闲线程的队列
 *
 * @author AnonymousCodeMaker
 * @create 2021:08:26 20:55
 **/
public class WKQueue {

  /**
   * 队列最大存放任务数量
   */
  int maxSize;
  /**
   * 队列使用双向链表
   */
  LinkedList<WKTask> list;
  /**
   * 队列满的时候的处理策略
   */
  WKPolicyHander policyHander;
  /**
   * put的时候可能存在多线程并发 需要加锁保证线程安全
   */
  ReentrantLock lock = new ReentrantLock();
  /**
   * put时队列满了 阻塞直到队列中有元素被取出
   */
  Condition putCondition = lock.newCondition();
  /**
   * get时 如果队列中没有元素 阻塞 直到队列中有元素加入
   */
  Condition getCondition = lock.newCondition();


  public WKQueue(int size,WKPolicyHander policyHander){
    this.maxSize = size;
    this.policyHander = policyHander;
    list = new LinkedList();
  }
  /**
   * 往队列里面存放任务
   * @param task
   */
  public void put(WKTask task) throws InterruptedException {
      lock.lock();
      try{
        while(list.size() == maxSize){
          //队列满 添加到阻塞队列等待被唤醒--队列中有一个元素出队就应该被唤醒
          System.out.println("put::队列已满 "+task.getTaskName()+"被阻塞...等待进入队列");
          putCondition.await();
        }
        System.out.printf("put::队列未满[总长度=%d,当前=%d] %s开始入队\n",maxSize,list.size(),
            task.getTaskName());
        //添加到最后
        list.addLast(task);
        //有元素进来了 叫醒getCondition中的阻塞队列
        getCondition.signal();
      }finally {
        lock.unlock();
      }

  }

  /**
   * 往队列里面存放任务 非阻塞式
   * @param task
   */
  public void tryPut(WKTask task) throws InterruptedException {
    lock.lock();
    try{
      if(list.size() == maxSize){
        //队列满 添加到阻塞队列等待被唤醒--队列中有一个元素出队就应该被唤醒
        System.out.println("tryPut::队列已满 "+task.getTaskName()+"被阻塞...等待进入队列");
        if(!putCondition.await(1,TimeUnit.SECONDS)){
          System.out.println("tryPut::队列已满 "+task.getTaskName()+"被阻塞...等待进入队列 等待超时");
          policyHander.handler(this,task);
          return;
        }
      }
      System.out.printf("tryPut::队列未满[总长度=%d,当前=%d] %s开始入队\n",maxSize,list.size(),
          task.getTaskName());
      //添加到最后
      list.addLast(task);
      //有元素进来了 叫醒getCondition中的阻塞队列
      getCondition.signal();
    }finally {
      lock.unlock();
    }

  }

  /**
   * 从队列里面取一个任务
   * @return
   */
  public WKTask get() throws InterruptedException {
    lock.lock();
    try{
      while(list.isEmpty()){
        System.out.println("get::队列为空,获取不到任务,开始阻塞...等待新的任务加入队列");
        //队列为空 阻塞直到队列中有元素可以取出
        getCondition.await();
      }
      //不为空 从队列获取第一个元素 先进先出 并且唤醒put中的阻塞队列
      WKTask task = list.pollFirst();
      putCondition.signal();
      System.out.printf("get::队列不为空，开始取出一个任务%s 取出后当前大小：%d 总大小：%d \n",task.getTaskName(),
          list.size(),maxSize);
      return task;
    }finally {
      lock.unlock();
    }
  }

  /**
   * 从队列里面取一个任务 非阻塞式
   * @return
   */
  public WKTask tryGet() throws InterruptedException {
    lock.lock();
    try{
      if(list.isEmpty()){
        System.out.println("tryGet::队列为空,获取不到任务,开始阻塞...等待新的任务加入队列");
        //队列为空 阻塞直到队列中有元素可以取出 等待5秒如果没返回说明超时了 返回false 如果5s之内等到了 就返回true
        if(!getCondition.await(1, TimeUnit.SECONDS)){
          System.out.println("tryGet::队列为空,获取不到任务,开始阻塞...等待新的任务加入队列 等待超时");
          return null;
        }
      }
      //不为空 从队列获取第一个元素 先进先出 并且唤醒put中的阻塞队列
      WKTask task = list.pollFirst();
      //到这里task一定存在
      assert task != null;
      putCondition.signal();
      System.out.printf("tryGet::队列不为空，开始取出一个任务%s 取出后当前大小：%d 总大小：%d \n",task.getTaskName(),
          list.size(),maxSize);
      return task;
    }finally {
      lock.unlock();
    }
  }

}
