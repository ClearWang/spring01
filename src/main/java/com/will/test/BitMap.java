package com.will.test;

import java.util.BitSet;

/**
 * bitmap test
 *
 * @author AnonymousCodeMaker
 * @create 2022:02:21 11:31
 **/
public class BitMap {
  byte[] bits;
  /**
   * 最大的数
   */
  int max;

  public BitMap(int max) {
    this.max = max;
    this.bits = new byte[(max >> 3) + 1];
  }

  /**
   * add(5)
   * 0000 0000  0~7
   * @param v
   */
  public void add(int v) {
    if (v > max){
      System.out.println("v="+v+" add失败！不能大于最大的数(max="+max+")");
      return;
    }
    int index = v >> 3;
    //取余操作 v % 8;
    int loc = v & (8 -1);
    bits[index] |= 1 << loc;
  }

  /**
   * del(5)
   * 0010 0010  0~7
   * @param v
   */
  public void del(int v) {
    int index = v >> 3;
    int loc = v & (8 -1);
    if (find(v)){
      //将2^n取反 再做与操作
      bits[index] &= ~(1 << loc);
    }
  }

  public boolean find(int v) {
    if (v > max){
      System.out.println("v="+v+" find失败！不能大于最大的数(max="+max+")");
      return false;
    }
    int index = v >> 3;
    int loc = v % 8;
    int flag = bits[index] & (1 << loc);
    return flag != 0;
  }

  public static void main(String[] args) {
    BitMap bitMap = new BitMap(65);
    bitMap.add(10);
    bitMap.add(11);
    bitMap.add(12);
    bitMap.add(25);
    bitMap.add(60);
    bitMap.add(70);
    System.out.println(bitMap.find(11));
    bitMap.del(11);
    System.out.println(bitMap.find(11));
    System.out.println(bitMap.find(12));
    bitMap.del(100);
  }
}
