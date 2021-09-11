package com.will.threads;


/**
 * 队列满了的处理策略
 *
 * @author AnonymousCodeMaker
 * @create 2021:08:26 21:00
 **/
public interface WKPolicyHander {
  void handler(WKQueue queue, WKTask task);
}
