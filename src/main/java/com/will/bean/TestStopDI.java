package com.will.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * test
 * 添加这个类之后 所有的DI都会被阻止
 * @author AnonymousCodeMaker
 * @create 2021:06:16 20:04
 **/
//@Service
public class TestStopDI implements InstantiationAwareBeanPostProcessor {

  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return false;
  }
}
