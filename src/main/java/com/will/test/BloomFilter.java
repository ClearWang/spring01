package com.will.test;

import java.util.BitSet;

/**
 * 布隆过滤器
 * 自己实现版本
 * @author clewill
 * @create 2022:03:25 18:31
 **/
public class BloomFilter {

  BitSet bitSet;
  /**
   * 数组长度
   */
  int size;

  public BloomFilter(int size){
    this.size = size;
    bitSet = new BitSet(size);
  }

  public void put(String value){
      int hash01Value = hash01(value);
      int hash02Value = hash02(value);
      int hash03Value = hash03(value);

      bitSet.set(hash01Value);
      bitSet.set(hash02Value);
      bitSet.set(hash03Value);
  }

  public boolean get(String value){
    int hash01Value = hash01(value);
    int hash02Value = hash02(value);
    int hash03Value = hash03(value);

    return bitSet.get(hash01Value) && bitSet.get(hash02Value) && bitSet.get(hash03Value);
  }

  protected int hash01(String key) {
    int hash = 0;
    int i;
    for (i = 0; i < key.length(); ++i) {
      hash = 33 * hash + key.charAt(i);
    }
    return Math.abs(hash) % size;
  }

  protected int hash02(String key) {
    final int p = 16777619;
    int hash = (int) 2166136261L;
    for (int i = 0; i < key.length(); i++) {
      hash = (hash ^ key.charAt(i)) * p;
    }
    hash += hash << 13;
    hash ^= hash >> 7;
    hash += hash << 3;
    hash ^= hash >> 17;
    hash += hash << 5;
    return Math.abs(hash) % size;
  }

  protected int hash03(String key) {
    int hash, i;
    for (hash = 0, i = 0; i < key.length(); ++i) {
      hash += key.charAt(i);
      hash += (hash << 10);
      hash ^= (hash >> 6);
    }
    hash += (hash << 3);
    hash ^= (hash >> 11);
    hash += (hash << 15);
    return Math.abs(hash) % size;
  }

  public static void main(String[] args) {
    //int最大值为21亿 可以算一下即使是21亿 这里的内存也不会很大  2100000000*8/64/1024kb/1024m=250m的内存消耗
    BloomFilter bloomFilter = new BloomFilter(Integer.MAX_VALUE);
    bloomFilter.put("中国");
    bloomFilter.put("美国");
    bloomFilter.put("战斗名族");

    System.out.println(bloomFilter.get("中国"));
    System.out.println(bloomFilter.get("小日本"));

    System.out.println("=====================hash=====================");
    //这几个hash函数 属于比较靠谱的 原因是散列出来的数据比较分散
    System.out.println(bloomFilter.hash01("1"));
    System.out.println(bloomFilter.hash01("50000000"));
    System.out.println(bloomFilter.hash01("100000000"));
  }


}
