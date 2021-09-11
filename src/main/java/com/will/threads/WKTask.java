package com.will.threads;

import lombok.SneakyThrows;

/**
 * 模拟一个任务
 * @author clewill
 * @create 2021:08:26 20:46
 **/
public class WKTask extends Thread{

  private String taskName;
  public WKTask(String name) {
    this.taskName = name;
  }

  @SneakyThrows
  @Override
  public void run() {
    //当前任务的执行逻辑
    System.out.println("=============="+taskName+"开始执行");
    Thread.sleep(5000);
  }

  public String getTaskName() {
    return taskName;
  }
}
