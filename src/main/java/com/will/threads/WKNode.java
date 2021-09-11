package com.will.threads;

import lombok.SneakyThrows;

/**
 * 线程
 *
 * @author AnonymousCodeMaker
 * @create 2021:08:28 9:43
 **/
public class WKNode extends Thread{

  private String threadName;
  private WKTask task;
  private WKThreadPool threadPool;
  public WKNode(String name,WKTask task,WKThreadPool threadPool) {
    this.threadName = name;
    this.task = task;
    this.threadPool = threadPool;
  }

  public String getThreadName() {
    return threadName;
  }

  @SneakyThrows
  @Override
  public void run() {
    //核心线程池的执行逻辑 核心线程不应该销毁 所以这里应该要阻塞 最简单的阻塞方式就是自旋
    //while(true){
    //  /**
    //   * 这里有两种情况：
    //   *    第一情况：核心线程池未满 任务直接由核心线程构造方法传入
    //   *    第二种情况：核心线程池满了 等待核心线程池空了后取一个核心线程处理任务 直接从阻塞队列中取就可以了
    //   */
    //  if(null != task || (null != threadPool.wkQueue && null != (task = threadPool.wkQueue.get()))){
    //    task.run();
    //  }
    //}
    //队列会阻塞 所以上面等价于下面：
    while(null != task || (null != threadPool.wkQueue && null != (task =
        threadPool.wkQueue.tryGet()))){
      //注意我这里是直接调用的run方法 而不是start方法 因为task是一个模拟的任务 调用start方法底层会去启动一个os线程 那线程池的意义就不存在了
      System.out.println(threadName+"开始执行"+task.getTaskName());
      task.run();
      task = null;
    }


  }
}
