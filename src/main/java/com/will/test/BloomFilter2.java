package com.will.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.nio.charset.StandardCharsets;

/**
 * 布隆过滤器 使用google.guava
 *
 * @author clewill
 * @create 2022:03:25 18:31
 **/
public class BloomFilter2 {

  public static void main(String[] args) {
    //expectedInsertions表示的是元素个数
    int expectedInsertions = 100000000;
    //fpp表示的是误判率
    double fpp = 0.0001;
    //统计一下这个误判率 看下准不准 这个我们使用int类型去测试
    long start = System.currentTimeMillis();
    BloomFilter<Integer> bloomFilterInt = BloomFilter.create(Funnels.integerFunnel(),
        expectedInsertions,fpp);
    for(int i=0;i<=expectedInsertions;i++){
      bloomFilterInt.put(i);
    }
    System.out.println("BloomFilter添加"+expectedInsertions+"数据总耗时："+(System.currentTimeMillis()-start)+"ms");
    //误判的个数
    int wrongNumber = 0;
    int testNumber = 1000000;
    for(int i=expectedInsertions+1;i<=expectedInsertions+testNumber;i++){
        if (bloomFilterInt.mightContain(i)){
          wrongNumber++;
        }
    }
    System.out.println("误判个数:"+wrongNumber+",总个数:"+testNumber);

    //string类型使用也很简单
    BloomFilter<String> bloomFilterString =
        BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8),expectedInsertions,fpp);
    bloomFilterString.put("中国");
    bloomFilterString.put("美国");
    bloomFilterString.put("德国");
    System.out.println(bloomFilterString.mightContain("中国"));
    System.out.println(bloomFilterString.mightContain("美国1"));
  }
}
