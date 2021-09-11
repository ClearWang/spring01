package com.will.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * persion
 *
 * @author AnonymousCodeMaker
 * @create 2021:06:16 19:58
 **/
@Service
public class Person {
  @Autowired
  private Student student;

  public Student getStudent() {
    return student;
  }
}
