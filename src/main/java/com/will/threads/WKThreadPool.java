package com.will.threads;

import java.util.HashSet;

/**
 * @author AnonymousCodeMaker
 * @create 2021:08:22 10:39
 **/
public class WKThreadPool {

  /**
   * 核心线程数量
   */
  int coreThreadNum;
  /**
   * 队列长度
   */
  int queueSize;
  /**
   * 存放核心线程容器  怎么体现核心线程与空闲线程的区别？TODO
   */
  HashSet<WKNode> coreThreadSet;
  WKQueue wkQueue;

  public WKThreadPool(int coreThreadNum,int queueSize,WKPolicyHander policyHander){
    this.coreThreadNum=coreThreadNum;
    this.queueSize=queueSize;
    coreThreadSet = new HashSet<>(coreThreadNum);
    wkQueue = new WKQueue(queueSize,policyHander);
  }

  /**
   * 提交一个新任务到WKThreadPool线程池
   * @param newTask
   */
  public void execute(WKTask newTask) throws InterruptedException {
      if (coreThreadSet.size() < coreThreadNum){
        //核心线程池没有满的情况下 优先存放核心线程池
        WKNode wkNode = new WKNode("Thread"+(coreThreadSet.size()+1),newTask,this);
        coreThreadSet.add(wkNode);
        wkNode.start();
      }else{
        /**
         *  核心线程池满了 存放队列 这里有两种实现方式：
         *  1.阻塞式--put方法--如果满了就阻塞直到队列放的下
         *  2.非阻塞式--tryput方法--等待一定时间如果还没有放进去 就调用自定义的WKPolicyHander处理 并返回结束阻塞
         *  说明：这里模拟的是核心线程 不考虑空闲线程
         *  如果要考虑空闲线程 这里需要先判断队列是否满了或者最大核心线程数是否为0
         *  1.如果队列满了：那么创建一个空闲线程处理
         *  2.如果核心线程数为0即不存在核心线程池的情况下，创建一个空闲线程处理
         */
        wkQueue.tryPut(newTask);
      }
  }

}
