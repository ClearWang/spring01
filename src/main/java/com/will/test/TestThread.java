package com.will.test;

import com.will.entity.A;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.SneakyThrows;

/**
 * test
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:11 11:19
 **/
public class TestThread {

  static AtomicInteger index = new AtomicInteger();
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //
    /**
     * 核心点:
     *  1.理解常见的阻塞队列(对应的为非阻塞队列,比如：ConcurrentLinkedQueue)
     *      有界阻塞队列：
     *        ArrayBlockingQueue
     *        LinkedBlockingQueue
     *        LinkedBlockingDeque
     *        SynchronousQueue
     *      无界阻塞队列：
     *        PriorityBlockingQueue-适合优先级不同的任务
     *        DelayQueue
     *        LinkedTransferQueue
     *  2.理解jdk提供的线程池队列满了的处理策略
     *      CallerRunsPolicy-交给调用着线程处理
     *      AbortPolicy-默认策略-终止执行-直接抛出异常
     *      DiscardPolicy-丢弃不处理
     *      DiscardOldestPolicy-poll一个task(最先进来队列的)丢弃掉,接受新来的(不一定立马执行,可能会入队)
     *  3.线程池常见的使用方式
     *      非工厂方式：
     *        ThreadPoolExecutor
     *      工厂模式：
     *        newFixedThreadPool:
     *            无空闲线程
     *            核心线程数为指定参数(构造函数传入)
     *            LinkedBlockingQueue--Integer.MAX_VALUE(20亿+)
     *        newSingleThreadExecutor:  优点是可以保证任务的顺序执行
     *            无空闲线程
     *            核心线程数为1
     *            LinkedBlockingQueue-Integer.MAX_VALUE(20亿+)
     *        newCachedThreadPool: 适合短小精悍的任务
     *            无核心线程
     *            空闲线程数-Integer.MAX_VALUE(20亿+)  keepAliveTime: 60s
     *            SynchronousQueue(不存储元素的阻塞队列)
     *        newScheduledThreadPool:
     *            核心线程数为指定参数(构造函数传入)
     *            空闲线程数-Integer.MAX_VALUE keepAliveTime: 0
     *            DelayedWorkQueue
     *   4.execute和submit
     *   5.shutdown和shutdownNow()
     *      shutdown: 不再接受新的任务，不会打断当前正在执行的线程
     *      shutdownNow:  打断所有线程
     *   6.拓展线程池功能 beforeExecute、afterExecute、terminated
     *   7.如何配置线程池参数-实际经验：
     *      cpu密集型：多CPU消耗的任务，一般和内存打交道比较多 比如大数计算, 数据统计任务等
     * 	      为了减少cpu上下文切换
     * 	      线程池大小最大不超过cpu核数+1
     * 	      可以通过Runtime.getRuntime().availableProcessors()确认当前cpu核数
     *      IO密集型：多磁盘读写和网络读写的任务
     * 	      机器核心数*2(比如netty中就是这样配置的-后面有时间会谢谢netty源码篇)
     *      混合型：
     * 	      cpu*1.5倍
     *  生产环境尽量使用有界队列，防止OOM
     *  有界队列一旦满了会执行拒绝策略，我们可以在这个策略中推送告警信息给运维和开发
     */

    //System.out.println("当前os cpu核数:"+Runtime.getRuntime().availableProcessors()+"核");
    ThreadPoolExecutor threadPoolExecutor = createThreadPoolExecutor();
    //测试execute
    IntStream.range(0,5).forEach((i)->{
      threadPoolExecutor.execute(new Mytask("Task"+i));
    });
    //可以结束阻塞状态
    //threadPoolExecutor.allowCoreThreadTimeOut(true);

    //测试submit
    IntStream.range(0,5).forEach((i)->{
      //提交一个Callable任务如果成功 返回Callable中call方法返回值
      Future<String> submit = threadPoolExecutor.submit(new Mytask2("Task" + i));
      //提交一个Runnable任务如果成功 返回nil
      //Future submit = threadPoolExecutor.submit(new Mytask("Task" + i));
      //提交一个Runnable任务如果成功 返回yes
      //Future<String> submit = threadPoolExecutor.submit(new Mytask("Task" + i),"yes");
      try {
        //这个方法会阻塞
        System.out.println(submit.get());
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    });

    //测试invokeAll
    List<Callable<String>> objects = Arrays.asList(
        new Mytask2("Task1"),
        new Mytask2("Task2"),
        new Mytask2("Task3")
    );
    try {
      List<Future<String>> futures = threadPoolExecutor.invokeAll(objects);
      futures.forEach(f->{
        try {
          System.out.println(f.get());
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
        }
      });
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //测试invokeAny   这里不管执行多少遍都是只会执行Task1 因为task1执行时间最短-2s task2和task3都是5s
    List<Callable<String>> objects2 = Arrays.asList(
        new Mytask2("Task1"),
        new Mytask3("Task2"),
        new Mytask3("Task3")
    );
    String result = threadPoolExecutor.invokeAny(objects2);
    System.out.println(result);
    //try {
    //  TimeUnit.SECONDS.sleep(5);
    //} catch (InterruptedException e) {
    //  e.printStackTrace();
    //}
    //threadPoolExecutor.shutdown();
  }

  public static ThreadPoolExecutor createThreadPoolExecutor(){
    return new ThreadPoolExecutor(1,2,5,
        TimeUnit.SECONDS,new ArrayBlockingQueue<>(1),
        (r)->{
          Thread t = new Thread(r,"ThreadPoolExecutor-"+index.getAndAdd(1));
          t.setDaemon(true);
          return t;
        },
        new ThreadPoolExecutor.DiscardOldestPolicy()){
      @Override
      protected void beforeExecute(Thread t, Runnable r) {
        if (r instanceof Mytask){
          System.out.println(Thread.currentThread().getName()+"...begin execute..."+((Mytask)r).getTaskName());
        }else if (r instanceof Mytask2){
          System.out.println(Thread.currentThread().getName()+"...begin execute..."+((Mytask2)r).getTaskName());
        }
      }

      @Override
      protected void afterExecute(Runnable r, Throwable t) {
        if (r instanceof Mytask){
          System.out.println(Thread.currentThread().getName()+"...end   execute..."+((Mytask)r).getTaskName());
        }else if (r instanceof Mytask2){
          System.out.println(Thread.currentThread().getName()+"...end   execute..."+((Mytask2)r).getTaskName());
        }
      }

      @Override
      protected void terminated() {
        System.out.println("ThreadPool is end!");
      }
    };
  }

  static class Mytask implements Runnable{
    String taskName;
    public Mytask(String taskName) {
      this.taskName = taskName;
    }

    @SneakyThrows
    @Override
    public void run() {
      //System.out.println(Thread.currentThread().getName()+"...begin execute..."+taskName);
      TimeUnit.SECONDS.sleep(2);
      //System.out.println(Thread.currentThread().getName()+"...begin..."+taskName);
    }

    public String getTaskName() {
      return taskName;
    }
  }

  static class Mytask2 implements Callable<String>{
    String taskName;
    public Mytask2(String taskName) {
      this.taskName = taskName;
    }

    @Override
    public String call() throws Exception {
      TimeUnit.SECONDS.sleep(2);
      return Thread.currentThread().getName()+" call "+taskName+" success!";
    }

    public String getTaskName() {
      return taskName;
    }
  }

  static class Mytask3 implements Callable<String>{
    String taskName;
    public Mytask3(String taskName) {
      this.taskName = taskName;
    }

    @Override
    public String call() throws Exception {
      TimeUnit.SECONDS.sleep(5);
      return Thread.currentThread().getName()+" call "+taskName+" success!";
    }

    public String getTaskName() {
      return taskName;
    }
  }
}
