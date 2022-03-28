package com.will.test;
/**
 * bitmap test
 * 给定10个亿的数据集 要求判断某个数是否存在(假设机器内存大小为1G)
 * 如果使用hashMap 内存消耗：10,0000,0000*4(byte)/1024(kb)/1024(m)=3815m(四舍五入)
 * 如果是使用BitMap 假设使用byte数组来存 内存消耗：10,0000,0000/8(一个byte 8bit)(byte)/1024(kb)/1024(m)=119m(四舍五入)

 * @author clewill
 * @create 2022:03:25 18:31
 **/
public class BitMap {
  byte[] bits;
  /**
   * 最大的数 决定bits数组的长度
   */
  int max;

  public BitMap(int max) {
    this.max = max;
    //可以看到当前初始化的数组大小取决于最大的那个数 这也是为什么bitMap不适合小数据集的原因
    this.bits = new byte[(max >> 3) + 1];
  }

  /**
   * set(5)
   * 0000 0000  0~7
   * @param v
   */
  public void set(int v) {
    if (!checkIsSafe(v)){
      return;
    }
    //通过v/8获取对应bits数组下标
    int index = v >> 3;
    //取余操作 v % 8;
    int loc = v & (8 -1);
    //这一步看懂了 jdk中的源码你也就明白了 eg: set(5) index=0 loc=5 这里相当于将1左移5(千万不要看成将loc向左移动1位!!)
    bits[index] |= 1 << loc;
  }

  public void clear(int v) {
    int index = v >> 3;
    int loc = v & (8 -1);
    if (get(v)){
      //将2^n取反 再做与操作
      bits[index] &= ~(1 << loc);
    }
  }

  /**
   * 归为原始bit数组 清除数组中所有元素
   */
  public void clear(){
    //获取数组长度
    int lenOfBits = (max >> 3) + 1;
    while(lenOfBits > 0){
      bits[--lenOfBits]=0;
    }
  }


  public boolean get(int v) {
    if (!checkIsSafe(v)){
      return false;
    }
    int index = v >> 3;
    int loc = v % 8;
    int flag = bits[index] & (1 << loc);
    return flag != 0;
  }

  public boolean checkIsSafe(int v){
    if (v > max){
      System.out.println("v="+v+",不能大于最大的数(max="+max+")");
      return false;
    }
    if (v < 0){
      System.out.println("v="+v+",不能是负数");
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    BitMap bitMap = new BitMap(65);
    bitMap.set(5);
    bitMap.set(11);
    bitMap.set(60);
    bitMap.set(70);
    System.out.println(bitMap.get(-1));
    System.out.println(bitMap.get(100));
    System.out.println("=========================");
    System.out.println(bitMap.get(5));
    System.out.println(bitMap.get(11));
    System.out.println(bitMap.get(60));
    System.out.println("=========================");
    bitMap.clear(11);
    System.out.println(bitMap.get(5));
    System.out.println(bitMap.get(11));
    System.out.println(bitMap.get(60));
    System.out.println("=========================");
    bitMap.clear();
    System.out.println(bitMap.get(5));
    System.out.println(bitMap.get(11));
    System.out.println(bitMap.get(60));
  }
}
