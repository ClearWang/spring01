package com.will.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author AnonymousCodeMaker
 * @create 2021:06:28 17:25
 **/
public class TestInMain {
  public static int[] temp = new int[2000000];
  public static void main(String[] args) {
    //Object obj = new Object();
    //WeakReference weakReference = new WeakReference(obj);
    //SoftReference softReference = new SoftReference(obj);
    //PhantomReference phantomReference = new PhantomReference(obj, new ReferenceQueue());
    //obj = null;
    ////自动装箱 等价于Integer.valueOf(1)
    //Integer a = 1;
    ////a.intValue()
    //Integer b = 2;
    ////swap(a,b);
    //////本质这个a的值打印的是a.inValue()
    ////System.out.printf("swap a=%d,b=%d\n",a,b);
    ////swap2(a,b);
    ////System.out.printf("swap2 a=%d,b=%d\n",a,b);
    //swap3(a,b);
    //System.out.printf("swap3 a=%d ,b=%d",a,b);

    ////递归  阶乘问题
    //long begin = System.currentTimeMillis();
    //System.out.println("result="+nMutiply(5));
    //System.out.println("耗时："+(System.currentTimeMillis()-begin)+"ms");
    ////尾递归  阶乘问题
    //begin = System.currentTimeMillis();
    //System.out.println("result2="+nMutiply2(5,1));
    //System.out.println("耗时："+(System.currentTimeMillis()-begin)+"ms");

    ////递归  斐波拉切问题
    //long begin = System.currentTimeMillis();
    //System.out.println("result="+fab(40));
    //System.out.println("耗时："+(System.currentTimeMillis()-begin)+"ms");
    ////尾递归  斐波拉切问题
    //begin = System.currentTimeMillis();
    //System.out.println("result2="+fab2(40,1,1));
    //System.out.println("耗时："+(System.currentTimeMillis()-begin)+"ms");

    //插入排序
    //int[] arr = {22, 80, 90, 88, 83, 86, 21, 54, 94, 25};
    //int[] arr = {22, 80, 90, 88, 25, 86, 21, 54, 94, 83};
    //int[] arr = {10,1};
    //int[] arr = {10,19,8,7,6,5,4,3,2,11};
    //int[] arr = {0,1,1,7,3,2,8,10,9};
    //System.out.println("排序前:"+Arrays.toString(arr));
    //System.out.println("===========================================");
    //insertSortAsc(arr);
    //xiSortAsc(arr);
    //mergeSortAsc(arr,0,arr.length-1);
    //selectSortAsc(arr);
    //bubbleSortAsc(arr);
    //bubbleSortAsc2(arr);
    //quickSortAsc(arr,0,arr.length-1);
    //quickSortAscImprove(arr,0,arr.length-1);
    ////System.out.println("===========================================");
    //System.out.println("排序后:"+Arrays.toString(arr));
    //System.out.println("countLeft="+countLeft+"次;countRight="+countRight+"次");

    //stackSortAsc(arr);

    //topK(100000000,10);
    //try {
    //  createTestAgeFile();
    //} catch (IOException e) {
    //  e.printStackTrace();
    //}

    try {
      countRelation();
    } catch (IOException e) {
      e.printStackTrace();
    }

    //try {
    //  createTestScoreFile();
    //} catch (IOException e) {
    //  e.printStackTrace();
    //}

    //try {
    //  countSortAsc01();
    //  countSortAsc02();
    //  countSortAsc03();
    //  countSortAsc04();
    //} catch (Exception e) {
    //  e.printStackTrace();
    //}

    //dpOfBag();

    //System.out.println(is2N(11));
    //System.out.println(is2N(10));
    //System.out.println(is2N(1024));
    //
    //System.out.println(is2N_2(11));
    //System.out.println(is2N_2(10));
    //System.out.println(is2N_2(1024));
  }

  /**
   * 时间复杂度O(logN)
   * @param data
   * @return
   */
  public static boolean is2N(int data){
    while( data > 1){
      if (data % 2 != 0){
        return false;
      }
      data /= 2;
    }
    return true;
  }

  /**
   * 时间复杂度O(1)
   * 利用&运算  data & data-1 == 0那么就是2的N次方
   * 举例： 3对应的二进制：011
   *      4对应的二进制：100
   * 规律===>
   *      100 & 011 = 0
   * @param data
   * @return
   */
  public static boolean is2N_2(int data){
    return 0 == (data & data -1);
  }



  /**
   * 插入排序(打扑克的思路) 从小到大排序
   * 时间复杂度： O(n^2) 最优的情况下时间复杂度为: O(n)
   * 稳定性：稳定
   * @param arr
   */
  public static void insertSortAsc(int[] arr){
    //插入排序和希尔排序的核心思想是数据的插入(数据移动) 而不是数据交换
    //统计break执行次数
    int countBreak = 0;
    //统计总执行次数
    int countTotal = 0;
    //for1 外面的未排序元素
    for (int i = 1; i < arr.length; i++) {
      int data = arr[i];
      //for2 已排序元素
      int j;
      for (j = i-1; j >= 0; j--) {
        countTotal++;
        if (data >= arr[j]){
          //插入排序的效率本质就在于这里 这里执行的越多 插入排序的效率最高
          countBreak++;
          break;
        }else{
          //向后移动一个位置
          arr[j+1] = arr[j];
        }
      }
      arr[j+1] = data;
      System.out.printf("第%d次排序的结果为:%s\n",i,Arrays.toString(arr));
    }
    System.out.println("总执行次数："+countTotal+", "+"break执行次数:"+countBreak);
  }

  /**
   * 希尔排序 从小到大排序
   * 时间复杂度： O(n^2)
   * 稳定性：稳定
   * @param arr
   */
  public static void xiSortAsc(int[] arr){
    //统计break执行次数
    int countBreak = 0;
    //统计总执行次数
    int countTotal = 0;
    //设置中间的间隔
    int n = arr.length;
    for (int add = n/2;add >= 1;add /= 2){
      //for1 外面的未排序元素
      for (int i = add; i < arr.length; i++) {
        int data = arr[i];
        //for2 已排序元素
        int j;
        for (j = i-add; j >= 0; j -= add) {
          countTotal++;
          if (data >= arr[j]){
            //插入排序的效率本质就在于这里 这里执行的越多 插入排序的效率最高
            countBreak++;
            break;
          }else{
            //向后移动一个位置
            arr[j+add] = arr[j];
          }
        }
        arr[j+add] = data;
      }
    }
    System.out.println("总执行次数："+countTotal+", "+"break执行次数:"+countBreak);
  }

  /**
   * 归并排序 从小到大排序
   * 时间复杂度： O(nlogN)
   * 空间复杂度：O(n) 增加了一个临时数组
   * 稳定性：稳定
   * @param arr
   */
  public static void mergeSortAsc(int[] arr,int left,int right){
      if (left < right){
        int mid = (left+right)/2;
        //归的过程 时间复杂度logN
        mergeSortAsc(arr,left,mid);
        mergeSortAsc(arr,mid+1,right);
        //并的过程 时间复杂度n
        merge(arr,left,right,mid);
      }
  }

  public static void merge(int[] data,int left,int right,int mid){
      //引入一个临时数组 用来存放排序后的元素 这里每次merge的时候都会开辟内存 当数据量大的时候会耗性能 所以数据量大的时候需要提到全局变量只初始化一次
      //int[] temp = new int[data.length];
      //记录左边待比较元素的位置
      int point2LeftIndex = left;
      //记录右边待比较元素的位置
      int point2RightIndex = mid + 1;
      //记录temp中当前存放有序数组的元素位置
      int newIndex = left;
      while (point2LeftIndex <= mid && point2RightIndex <= right){
        //写法1：这样写更便于理解 如果第一个if写成if(data[point2LeftIndex] > data[point2RightIndex])理解起来会困难些
        if (data[point2LeftIndex] < data[point2RightIndex]){
          temp[newIndex++] = data[point2LeftIndex++];
        }else{
          temp[newIndex++] = data[point2RightIndex++];
        }
        //写法2
        //if(data[point2LeftIndex] > data[point2RightIndex]){
        //    temp[newIndex] = data[point2RightIndex];
        //    point2RightIndex++;
        //} else{
        //  temp[newIndex] = data[point2LeftIndex];
        //  point2LeftIndex++;
        //}
        //newIndex++;
      }
      while (point2LeftIndex <= mid){
        temp[newIndex++] = data[point2LeftIndex++];
        //temp[newIndex] = data[point2LeftIndex];
        //newIndex++;
        //point2LeftIndex++;
      }
      while(point2RightIndex <= right){
        temp[newIndex++] = data[point2RightIndex++];
        //temp[newIndex] = data[point2RightIndex];
        //newIndex++;
        //point2RightIndex++;
      }
      //写法1：数组赋值 最后将temp中从left到right拍好序列的值赋值到data数组中
      if (right - left + 1 >= 0) {
        System.arraycopy(temp, left, data, left, right + 1 - left);
      }
      //写法2：数组赋值 最后将temp中从left到right拍好序列的值赋值到data数组中
      //for (int i = left; i <= right; i++) {
      //  data[i] = temp[i];
      //}
  }

  /**
   * 选择排序 从小到大排序
   * 时间复杂度：O(n^2)
   * 稳定性：不稳定
   */
  public static void selectSortAsc(int[] arr){
     //选择排序的核心思想是数据交换 而不是数据插入 将头一个依次与后面每个比
    int n = arr.length;
    for (int i = 0; i < n; i++) {
      //int minLoc = i;
      for (int j = i+1; j < n; j++) {
        if(arr[i] > arr[j]){
          //数据交换
          swap(arr,i,j);
          //minLoc = j;
          //int temp = arr[i];
          //arr[i] = arr[j];
          //arr[j] = temp;
        }
      }
      //swap(arr,i,minLoc);
    }
  }

  public static void swap(int[] arr,int i,int j){
    //交换另个数的一般写法: 增加一个临时变量
    //int temp = arr[i];
    //arr[i] = arr[j];
    //arr[j] = temp;

    //交换两个数的另一个高效写法 使用+/-
    //arr[i] = arr[i] + arr[j];
    //arr[j] = arr[i] - arr[j];
    //arr[i] = arr[i] - arr[j];

    //交换两个数的另一个高效写法 使用异或的特点 两个相同的数的异或值为1
    arr[i] = arr[i] ^ arr[j];
    arr[j] = arr[i] ^ arr[j];
    arr[i] = arr[i] ^ arr[j];
  }
  /**
   * 冒泡排序 从小到大排序
   * 时间复杂度：O(n^2)   最好最差的情况的时间复杂度都是O(n^2)
   * 空间复杂度：O(n)
   * 稳定性：稳定
   * @param arr
   */
  public static void bubbleSortAsc(int[] arr){
    //每次比较相邻的两个数  每次冒到最后的一定是最大的一个数
    int n = arr.length;
    //统计对比的次数
    int countComp = 0;
    //for1 要比较的次数 比如1,2 只需要比较两次
    for (int i = 0; i < n -1; i++) {
      for (int j = 0; j < n-i-1; j++) {
        countComp++;
        //这里如果要写成arr[j] >= arr[j+1]那么冒泡就是不稳定的算法
        if (arr[j] > arr[j+1]){
          swap(arr,j,j+1);
        }
      }
    }
    System.out.println("比较次数："+countComp);
  }

  /**
   * 冒泡排序的优化
   * 时间复杂度：O(n^2)  最优的情况下(所有的元素已经有序)时间复杂度：O(n)
   * 空间复杂度：O(n)
   * 稳定性：稳定
   * @param arr
   */
  public static void bubbleSortAsc2(int[] arr){
    //每次比较相邻的两个数  每次冒到最后的一定是最大的一个数
    int n = arr.length;
    boolean flag = false;
    //统计对比的次数
    int countComp = 0;
    //for1 要比较的次数 比如1,2 只需要比较两次
    for (int i = 0; i < n -1; i++) {
      for (int j = 0; j < n-i-1; j++) {
        countComp++;
        //这里如果要写成arr[j] >= arr[j+1]那么冒泡就是不稳定的算法
        if (arr[j] > arr[j+1]){
          swap(arr,j,j+1);
          flag = true;
        }
      }
      //如果哪一次排序是有序的 表明当前序列已经是有序了 没必要再走后面的循环比较了
      if(!flag){
        break;
      }
    }
    System.out.println("比较次数："+countComp);
  }


  /**
   * 快速排序的优化
   * 时间复杂度：nlogN 最坏情况下 O(n^2)
   * 空间复杂度：O(n)
   * 稳定性：不稳定
   * @param arr
   */
  static int countLeft;
  static int countRight;
  public static void quickSortAscImprove(int[] arr,int left,int right) {
    //确定基准数 每次排序确定基准数的位置 基准数左边的都是比基准数小的 基准数右边的都是比基准数大的
    //思路：画个图就明白了原理 注意和归并的区别  最终的有序列表其实就是叶子节点的值
    //优化快排的思路就是优化对基准数的选择 可以选择取三个数中间的那个
    setBaseValue(arr,left,(left+right)/2,right);
    //这一步很有意思 每次拿最左边的那个元素作为基准数
    int base = arr[left];
    int ll = left;
    int rr = right;
    while(ll < rr) {
      //拿基数的值 从右边开始比较 left和right位置相同的时候结束
      while (ll < rr && base <= arr[rr]){
        rr--;
        countRight++;
      }
      if (ll < rr){
        //走到这里表示base > arr[right] 交换base和arr[right]的值 然后从左边开始比较
        //注意因为这里base的初始值最左边的值 所以这里交换的始终是ll和rr 理解下
        swap(arr,ll,rr);
        ll++;
      }
      while (ll < rr && base > arr[ll]){
        ll++;
        countLeft++;
      }
      if (ll < rr) {
        //走到这里说明base <= arr[left] 交换base和arr[left]值的值
        swap(arr,ll,rr);
        rr--;
      }
    }
    //走到这里说明第一轮排序已经完成 base 左边一定都是比base小的数 base右边一定都是比base大的数 递归左边和右边
    if (ll > left){
      quickSortAsc(arr,left,ll-1);
    }
    if (rr < right) {
      quickSortAsc(arr,rr+1,right);
    }
  }

  /**
   * 快速排序: 快排也是一种基于递归的排序算法 本质是在每一轮排序完成确定选定的基准数的位置
   * 使得每一轮排序之后基准数左边的都是比基准数小的 基准数右边的都是比基准数大的
   * 所以快排的优化思路其实就是优化基准数的选择问题  这里为了简单每次直接选择数组的第一个元素作为基准数
   *
   * 时间复杂度：nlogN 最坏情况下 O(n^2)
   * 空间复杂度：O(n)
   * 稳定性：不稳定
   * @param arr
   */
  public static void quickSortAsc(int[] arr,int left,int right) {
    //确定基准数 每次排序确定基准数的位置 基准数左边的都是比基准数小的 基准数右边的都是比基准数大的
    //思路：画个图就明白了原理 注意和归并的区别  最终的有序列表其实就是叶子节点的值
    int base = arr[left];
    int ll = left;
    int rr = right;
    while(ll < rr) {
      //拿基数的值 从右边开始比较
      while (ll < rr && base <= arr[rr]){
        rr--;
        countRight++;
      }
      if (ll < rr){
        //走到这里表示base > arr[right] 交换base和arr[right]的值 然后从左边开始比较
        //注意因为这里base的初始值最左边的值 所以这里交换的始终是ll和rr 理解下
        swap(arr,ll,rr);
        ll++;
      }
      while (ll < rr && base > arr[ll]){
        ll++;
        countLeft++;
      }
      if (ll < rr) {
        //走到这里说明base <= arr[left] 交换base和arr[left]值的值
        swap(arr,ll,rr);
        rr--;
      }
    }
    //走到这里说明第一轮排序已经完成 base 左边一定都是比base小的数 base右边一定都是比base大的数 递归左边和右边
    if (ll > left){
      quickSortAsc(arr,left,ll-1);
    }
    if (rr < right) {
      quickSortAsc(arr,rr+1,right);
    }
  }

  /**
   * 堆排序：
   * 关键点理解：
   * 1.其实数据结构中没有所谓的堆 堆的本质其实就是一个完全二叉树或者满二叉树 而完全二叉树或者满二叉树的最高效实现是数组
   * 所以你可以理解为这里的堆其实就是一个数组
   * 2.堆排序的前提是建立好一个大顶堆(根节点都比左右子节点大)或者小顶堆(根节点都比左右子节点小)
   * 3.将原始数组(完全二叉树或者满二叉树的实现)转化成一个大顶堆或者小顶堆的过程叫做堆化
   *
   * 举例：
   * 给定一个原始数组：[0,1,1,7,3,2,8,10,9]
   * 1.建成一个完全二叉树
   *       0
   *     1   1
   *    7  3 2  8
   * 10  9
   * 2.做一次堆化 变成一个大顶堆
   * 堆化的过程 分为自上向下堆化和自下向上堆化 这里以自下向上做堆化举例
   * 针对上面的初始满二叉树,从7开始做替换(为什么是从7开始而不是从最后一个元素9开始,因为这里是要做对比(和左右子节点),所以要找第一个非叶子节点(可以理解为第一个有子节点的节点))
   * 然后蛇形走位，7和(10,9)对比  然后是1和(2,8) 然后继续 这个替换的过程还是挺简单的
   * 需要注意的点是：替换到如下位置10和1换完后 1还需要和他的左右子树做对比 完成替换 直到父节点不比左右小为止
   *       0
   *     10   8
   *    1  3 2  1
   *  7   9
   *
   *  ==>最终的大顶堆：
   *       10
   *     9   8
   *   7  3 2  1
   *  0  1
   * 3.堆化完成做一次堆排序
   * 堆排的过程非常有意思，说白了就是做一个替换然后再做一次堆化 over
   * 如果是大顶堆  将堆顶的位置元素和最后一个元素替换 然后做一次堆化（注意这时候堆化结束的位置有讲究 每次不是堆化到最后一个元素）
   *       1
   *     9   8
   *   7  3 2  1
   *  0  10
   *  ==>堆化(堆化到10这个位置(不包含10) 结束堆化)
   *       9
   *     7   8
   *   1  3 2  1
   *  0  10
   *  继续第一个元素和倒数第二个元素互换  然后做一次堆化(堆化到9这个位置(不包含9) 结束堆化)
   *       0
   *     7   8
   *   1  3 2  1
   *  9  10
   *  ==>堆化
   *       8
   *     7   2
   *   1  3 0  1
   *  9  10
   * ==>最后一次堆化完成：
   *       0
   *     1   1
   *   2  3 7  8
   *  9  10
   *
   * @param arr 完全二叉树的数组表示形式
   */
  public static void stackSortAsc(int[] arr){
    //arr已经是一个完全二叉树的数组表示形式
    //堆化 从第一个非叶子节点开始 最后一个元素的位置为n-1
    int n = arr.length;
    int firstNotLeafNodePosition;
    if (n % 2 == 0){
      //假设最后一个节点为左节点 那么他的父亲节点位置为：2x+1=n-1 x=(n-2)/2
      firstNotLeafNodePosition = (n-2)/2;
    }else{
      //假设最后一个节点为右节点 那么他的父节点位置为：2x+2=n-1 x=(n-3)/2
      firstNotLeafNodePosition = (n-3)/2;
    }
    //堆化
    for (int i=firstNotLeafNodePosition;i>=0;i--){
      //从堆底第一个非叶子节点开始做堆化 从下而上做堆化  这里不能传n-1而要传n 可以想想为啥
      maxHeapImprove2(arr,i,n);
    }
    //堆排序 时间复杂度 nLogN
    for(int i=n-1;i>0;i--){
      swap(arr,i,0);
      //从堆顶开始做堆化 从上而下做堆化
      maxHeapImprove2(arr,0,i);
    }
    //已经排好序了 直接输出即可
    //System.out.println(Arrays.toString(arr));
  }

  /**
   * 堆化 时间复杂度：logN
   * 构建一个大顶堆 每一次maxHeap下来 都能保证从当前父节点parentNodePos开始到lastPos位置结束的堆树都是大顶堆
   * @param arr 完全二叉树的数组表示形式
   * @param parentNodePos 最后一个元素的节点位置
   * @param lastPos 堆化结束的位置
   */
  public static void maxHeap(int[] arr,int parentNodePos,int lastPos){
    int leftNode = 2 * parentNodePos + 1;
    int rightNode = leftNode + 1;
    //这里有个bug问题 当一颗完全二叉树 某一个节点没有刚好只有左子树 没有右子树时 这里的rightNode就可能等于lastPos 比如：{22, 80, 90, 88, 25, 86, 21, 54, 94, 83}
    //所以这时候这个循环就不会进入
    while (leftNode < lastPos && rightNode < lastPos){
      if(arr[rightNode] > arr[leftNode]){
        //和左右节点最大的比
        if(arr[parentNodePos] >= arr[rightNode]){
          return;
        }
        //交换位置
        swap(arr,rightNode,parentNodePos);
        parentNodePos = rightNode;
      }else{
        if(arr[parentNodePos] >= arr[leftNode]){
          return;
        }
        //交换位置
        swap(arr,leftNode,parentNodePos);
        parentNodePos = leftNode;
      }
      leftNode = 2 * parentNodePos + 1;
      rightNode = leftNode + 1;
    }
  }

  /**
   * 堆化 时间复杂度：logN
   * 构建一个大顶堆 每一次maxHeap下来 都能保证从当前父节点parentNodePos开始到lastPos位置结束的堆树都是大顶堆
   * @param arr 完全二叉树的数组表示形式
   * @param parentNodePos 最后一个元素的节点位置
   * @param lastPos 堆化结束的位置
   */
  public static void maxHeapImprove(int[] arr,int parentNodePos,int lastPos){
    int leftNode = 2 * parentNodePos + 1;
    int rightNode = leftNode + 1;
    while (leftNode < lastPos){
      //针对没有右子树的情况做特殊处理 但这样的写法使得整个程序可读性非常差 maxHeapImprove2
      if (rightNode >= lastPos){
        //只有左子树时
        if(arr[parentNodePos] >= arr[leftNode]){
          return;
        }
        //交换位置
        swap(arr,leftNode,parentNodePos);
        parentNodePos = leftNode;
      }else{
        if(arr[rightNode] > arr[leftNode]){
          //和左右节点最大的比
          if(arr[parentNodePos] >= arr[rightNode]){
            return;
          }
          //交换位置
          swap(arr,rightNode,parentNodePos);
          parentNodePos = rightNode;
        }else{
          if(arr[parentNodePos] >= arr[leftNode]){
            return;
          }
          //交换位置
          swap(arr,leftNode,parentNodePos);
          parentNodePos = leftNode;
        }
      }
      leftNode = 2 * parentNodePos + 1;
      rightNode = leftNode + 1;
    }
  }

  /**
   * 堆化 时间复杂度：logN
   * 构建一个大顶堆 每一次maxHeap下来 都能保证从当前父节点parentNodePos开始到lastPos位置结束的堆树都是大顶堆
   * @param arr 完全二叉树的数组表示形式
   * @param parentNodePos 最后一个元素的节点位置
   * @param lastPos 堆化结束的位置
   */
  public static void  maxHeapImprove2(int[] arr,int parentNodePos,int lastPos){
    int leftNode = 2 * parentNodePos + 1;
    int rightNode = leftNode + 1;
    while(leftNode < lastPos){
      int temp = leftNode;
      if (rightNode < lastPos && arr[rightNode] > arr[leftNode]){
        //temp总是存最大的那个
        temp = rightNode;
      }
      //这里只需要比较temp和父节点的大小即可 这就是使用一个临时变量的好处 不需要担心数组越界的问题 程序可读性也非常好
      if (arr[temp] <= arr[parentNodePos]){
        //说明当前的parentNodePos已经是最小的了
        return;
      }
      swap(arr,temp,parentNodePos);
      parentNodePos = temp;
      leftNode = 2 * parentNodePos + 1;
      rightNode = leftNode + 1;
    }
  }
  /**
   * 堆化 时间复杂度：logN
   * 构建一个小顶堆 每一次minHeap下来 都能保证从当前父节点parentNodePos开始到lastPos位置结束的堆树都是小顶堆
   * @param arr 完全二叉树的数组表示形式
   * @param parentNodePos 最后一个元素的节点位置
   * @param lastPos 堆化结束的位置
   */
  public static void  minHeap(int[] arr,int parentNodePos,int lastPos){
    int leftNode = 2 * parentNodePos + 1;
    int rightNode = leftNode + 1;
    while(leftNode < lastPos){
      int temp = leftNode;
      if (rightNode < lastPos && arr[rightNode] < arr[leftNode]){
          //temp总是存最小的那个
          temp = rightNode;
      }
      //这里只需要比较temp和父节点的大小即可
      if (arr[temp] >= arr[parentNodePos]){
        //说明当前的parentNodePos已经是最小的了
        return;
      }
      swap(arr,temp,parentNodePos);
      parentNodePos = temp;
      leftNode = 2 * parentNodePos + 1;
      rightNode = leftNode + 1;
    }
  }

  /**
   * topK问题
   * 给定10亿个整数数字(0~2^32-1) 要求top10的数据,支持动态增加
   * @param number 要测试的数量级 比如这个例子是10亿 那么 number=1000000000
   * @param k 要筛选的top几的数据 比如这里是top10 那么k = 10
   */
  public static void topK(int number,int k){
    long start = System.currentTimeMillis();
    //维护一个k个元素的小顶堆(这里只需要做堆化没必要排序) 每进来一个数据将堆顶元素和当前元素作比较 如果比之大
    //将堆顶元素和当前元素替换 做一次堆化操作(这里只需要做堆化没必要排序)
    Random random = new Random();
    int[] arr = new int[k];
    //初始化数组
    for(int i=0;i<k;i++){
      arr[i]=random.nextInt();
    }
    System.out.println("初始化数组："+Arrays.toString(arr));
    int firstNotLeafNodePosition;
    if (k % 2 == 0){
      //假设最后一个节点为左节点 那么他的父亲节点位置为：2x+1=n-1 x=(n-2)/2
      firstNotLeafNodePosition = (k-2)/2;
    }else{
      //假设最后一个节点为右节点 那么他的父节点位置为：2x+2=n-1 x=(n-3)/2
      firstNotLeafNodePosition = (k-3)/2;
    }
    //堆化
    for (int i=firstNotLeafNodePosition;i>=0;i--){
      //维护一个小顶堆
      //从堆底第一个非叶子节点开始做堆化 从下而上做堆化 这里不能传n-1而要传n 想想为啥(自己画个图或者举个例子就懂了)
      minHeap(arr,i,k);
      System.out.println(i+""+Arrays.toString(arr));
    }
    System.out.println("初始堆化完成后:"+Arrays.toString(arr));
    for(int i=k;i<number;i++){
      int toAddValue = random.nextInt();
      if (toAddValue > arr[0]){
        //替换堆顶元素和要添加的元素
        arr[0] = toAddValue;
        minHeap(arr,0,k);
      }
    }
    System.out.println("top"+k+"元素为:"+Arrays.toString(arr)+",统计number="+number+"个数字总耗时："+(System.currentTimeMillis()-start)+"ms");
  }


  /**
   * 找基准数 选择三个数中中间的那个数
   * @param arr
   * @param left
   * @param mid
   * @param right
   */
  public static void setBaseValue(int[] arr,int left,int mid,int right){
    //假设left是中间数
    if ((arr[left] <= arr[mid] && arr[left] >= arr[right]) || (arr[left] <= arr[right] && arr[left] >= arr[mid])){
      return;
    }
    //假设mid是中间数
    if ((arr[mid] <= arr[left] && arr[mid] >= arr[right]) || (arr[mid] <= arr[right] && arr[mid] >= arr[left])){
      swap(arr,left,mid);
    }
    //以上都不满足 那么right一定是中间数
    swap(arr,left,right);
  }
  /**
   * 生成一个有14亿人口年龄数据的文件 年龄分布为 0~180岁
   */
  public static void createTestAgeFile() throws IOException {
    System.out.println("开始生成数据>>>");
    long begin = System.currentTimeMillis();
    BufferedWriter bw =
        new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\age.txt")));
    Random random = new Random();
    //int的最大值为21亿 所以不会越界
    for (int i = 0; i < 1400000000; i++) {
       //[0,181)之间的随机数
       bw.write(random.nextInt(181)+"\r\n");
    }
    bw.flush();
    bw.close();
    //我本地计算机的计算数据差不多为100w次/s  预估用时在100s左右
    System.out.println("数据生成完成,耗时："+(System.currentTimeMillis()-begin)+"ms");
  }

  /**
   * 生成一个有200w分数数据的文件 分数范围为0~900 double类型
   */
  public static void createTestScoreFile() throws IOException {
    System.out.println("开始生成数据>>>");
    long begin = System.currentTimeMillis();
    BufferedWriter bw =
        new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\score.txt")));
    Random random = new Random();
    //int的最大值为21亿 所以不会越界
    for (int i = 0; i < 2000000; i++) {
      //[0,181)之间的随机数 80001/100
      bw.write(random.nextInt(90001)/100.0+"\r\n");
    }
    bw.flush();
    bw.close();
    //我本地计算机的计算数据差不多为100w次/s  预估用时在100s左右
    System.out.println("数据生成完成,耗时："+(System.currentTimeMillis()-begin)+"ms");
  }

  /**
   * 计数相关问题
   * 统计全国14亿人口的年龄分布问题 假设最高年龄不会超过180岁。
   */
  public static void countRelation() throws IOException {
    long start = System.currentTimeMillis();
    //主要要一行一行读 不要一下子读完
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\age"
        + ".txt"), StandardCharsets.UTF_8));
    String ageLine;
    //新建一个int数组用来存放年龄
    int[] data = new int[190];
    //统计总数据量
    int totalNumber = 0;
    while ((ageLine = br.readLine()) != null){
      data[Integer.parseInt(ageLine)]++;
      totalNumber++;
    }
    System.out.println("统计完成!数据总量为"+totalNumber+"条,总耗时："+(System.currentTimeMillis()-start)+"ms");
    System.out.println("当前年龄分布如下：");
    for (int i = 0; i < data.length; i++) {
      if(data[i] > 0){
        System.out.println(i+"岁的有"+data[i]+"人");
      }
    }
  }
  /**
   * 计数排序
   * 将安徽省200w高考考生成绩总分从低到高排序(总分假设最多两位小数，0~900之间),要求排序算法尽可能高效。
   * 方法1：使用快排
   */
  public static void countSortAsc01() throws IOException {
    long start = System.currentTimeMillis();
    //首先将double小数问题转换成整数来处理 0~900的double类型 ===> 0~90000的int类型处理
    int[] data = new int[2000000];
    //主要要一行一行读 不要一下子读完
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\score"
        + ".txt"), StandardCharsets.UTF_8));
    String scoreLine;
    int i = 0;
    while ((scoreLine = br.readLine()) != null){
      data[i++] = (int) (Double.parseDouble(scoreLine)*100);
    }
    quickSortAsc(data,0,data.length-1);
    //将排完成序的数组元素写入到文件中
    File file = new File("D:\\score-sort-01.txt");
    Writer out = new FileWriter(file);
    for (int result : data) {
      out.write(( result / 100.0) + "\r\n");
    }
    out.close();
    System.out.println("快速排序::总耗时："+(System.currentTimeMillis()-start)+"ms");
  }

  /**
   * 将安徽省200w高考考生成绩总分从低到高排序(总分假设最多两位小数，0~900之间),要求排序算法尽可能高效。
   * 方法1：使用归并排序
   */
  public static void countSortAsc02() throws IOException {
    long start = System.currentTimeMillis();
    //首先将double小数问题转换成整数来处理 0~900的double类型 ===> 0~90000的int类型处理
    int[] data = new int[2000000];
    //主要要一行一行读 不要一下子读完
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\score"
        + ".txt"), StandardCharsets.UTF_8));
    String scoreLine;
    int i = 0;
    while ((scoreLine = br.readLine()) != null){
      data[i++] = (int) (Double.parseDouble(scoreLine)*100);
    }
    mergeSortAsc(data,0,data.length-1);
    //将排完成序的数组元素写入到文件中
    File file = new File("D:\\score-sort-02.txt");
    Writer out = new FileWriter(file);
    for (int result : data) {
      out.write(( result / 100.0) + "\r\n");
    }
    out.close();
    System.out.println("归并排序::总耗时："+(System.currentTimeMillis()-start)+"ms");
  }

  /**
   * 堆排序
   * @throws IOException
   */
  public static void countSortAsc03() throws IOException {
    long start = System.currentTimeMillis();
    //首先将double小数问题转换成整数来处理 0~900的double类型 ===> 0~90000的int类型处理
    int[] data = new int[2000000];
    //主要要一行一行读 不要一下子读完
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\score"
        + ".txt"), StandardCharsets.UTF_8));
    String scoreLine;
    int i = 0;
    while ((scoreLine = br.readLine()) != null){
      data[i++] = (int) (Double.parseDouble(scoreLine)*100);
    }
    stackSortAsc(data);
    //将排完成序的数组元素写入到文件中
    File file = new File("D:\\score-sort-03.txt");
    Writer out = new FileWriter(file);
    for (int result : data) {
      out.write(( result / 100.0) + "\r\n");
    }
    out.close();
    System.out.println("堆排序::总耗时："+(System.currentTimeMillis()-start)+"ms");
  }

  /**
   * 将安徽省200w高考考生成绩总分从低到高排序(总分假设最多两位小数，0~900之间),要求排序算法尽可能高效。
   * 方法3：使用计数排序
   */
  public static void countSortAsc04() throws Exception {
    long start = System.currentTimeMillis();
    //主要要一行一行读 不要一下子读完
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\score"
        + ".txt"), StandardCharsets.UTF_8));
    String scoreLine;
    int[] result = new int[90001];
    while ((scoreLine = br.readLine()) != null){
      result[(int) (Double.parseDouble(scoreLine)*100)]++ ;
    }
    //走到这里其实已经排完序了 result中数组下标(考生的分数)表示的是顺序 如果对应下标的值不为0表示 该分数的考生人数
    File file = new File("D:\\score-sort-04.txt");
    Writer out = new FileWriter(file);
    for (int i = 0; i < result.length ; i++) {
      if (result[i] > 0){
        for (int j = 0; j < result[i]; j++) {
          out.write(((double) (i / 100.0)) + "\r\n");
        }
      }
    }
    out.close();
    System.out.println("计数排序::总耗时："+(System.currentTimeMillis()-start)+"ms");
  }

  /**
   * 贪心算法
   * 公司OA系统 会议室预定问题
   * 公司有N个会议室可供预定，要求最大化会议室利用。
   * 8~10  7~11  10~12
   * 方案1：按照开始时间从小到大排序 选取第一个作为第一个使用会议室的时间 后续按照结束时间对比  ==> 7~11
   * 方案2：按照结束时间从小到大排序 选取第一个作为第一个使用会议室的时间 后续按照结束时间对比  ==> 8~10 10~12
   * 贪心算法应用场景：
   *    1.求解最值问题：最短路径
   *    2.策略问题
   *    3.哈夫曼编码
   */
  public static void oARelation(){

  }

  /**
   * 动态规划(dynamic programming)
   * 背包问题
   * 当前商店存在物品和价值：
   * 1kg 6$     商品1
   * 2kg 10$    商品2
   * 4kg 12$    商品3
   * 当前背包只有5kg 如何最大化背包利用(如果同一种类商品有多个可以分解成多个商品,这里假设每种商品就只有一个)？
   * 思路1：贪心算法  比如: 按价值从大到小排序：1kg+2kg(商品1+商品2) 很显然不能最大化背包价值
   * 思路2：穷举所有的可能性 000 111 110 100 101 011 010 001  最后比较得出101: 商品1+商品3 最大价值：12$+6$ 缺点是：时间复杂度太高
   * 思路3：动态规划
   * 当前要添加的商品\背包拆分 1kg  2kg  3kg     4kg     5kg
   *       商品1            6$   6$   6$      6$      6$
   *       商品2            6$  10$  10$+6$  10$+6$  10$+6$
   *       商品3            6$  10$  10$+6$  10$+6$  12$+6$
   * 最终选择商品1+商品3 最大价值：12$+6$
   * 假设商品总重量为w  商品序号为i  拆分的商品重量为j
   * 每个商品的重量为 product[i].weight 每个商品的价值为 product[i].value
   * 推导出dp公式：  Max(product[i].value+value[i-1][j-product[i].weight],value[i-1][j])
   *
   * 动态规划的应用场景：
   *   短字符相似性匹配：相似度,编辑距离,两个字符串最长公共子串
   */
  public static void dpOfBag(){
    Product product = new Product(1,6);
    Product product2 = new Product(2,10);
    Product product3 = new Product(4,12);
    Product[] products = new Product[]{product,product2,product3};
    int totalWeight = 5;
    int productNum = products.length;
    int[][] values = new int[productNum+1][totalWeight+1];
    for (int i = 1; i <= productNum; i++) {
      //重量分成1,2,3,4,5等分
      for (int j = 1; j <= totalWeight; j++) {
        //商品的重量比分割的重量小 表示可以装进去
        if(products[i-1].weight <= j){
          //注意这里的products[i-1]才表示的是当前商品
          //取当前商品的价值+上一个重量为j-products[i-1].weight的价值
          values[i][j] = Math.max(products[i-1].value+values[i-1][j-products[i-1].weight],
              values[i-1][j]);
        }else{
          //装不进去 拿上次的
          values[i][j] = values[i-1][j];
        }
      }
    }
    //打印最终结果矩阵
    for (int i = 0; i < productNum+1; i++) {
      for (int j = 0; j < totalWeight+1; j++) {
        System.out.printf("%2d  ",values[i][j]);
      }
      System.out.println();
    }

    //统计背包路径
    int tw = totalWeight;
    for (int i = productNum; i > 0; i--) {
      if (tw >= 0 && values[i][tw] != values[i-1][tw]){
        //不相同说明当前物品需要被加入
        System.out.println("product"+i+"加入背包!");
        //注意这里的products[i-1]才表示的是当前商品
        tw = tw - products[i-1].weight;
      }
    }

    //输出最优解
    System.out.println("最优解为："+values[productNum][totalWeight]+"$");
  }

  static class Product{
    int weight;//商品重量
    int value;//商品价值

    public Product(int weight, int value) {
      this.weight = weight;
      this.value = value;
    }
  }
  /**
   * 动态规划(dynamic programming)
   * 清空购物车问题
   * 如果你媳妇准备给你清空购物车(5000￥以内),目前已知商品及价格如下(假设每种商品数量只有一个),你如何最大化使用?
   * 1000￥   商品1
   * 2000￥   商品2
   * 4000￥   商品3
   * 当前要添加的商品\价值      5000￥
   *          商品1          1000￥
   *          商品2          2000￥+1000￥
   *          商品3          4000￥+1000￥
   */
  public static void dpOfCart(){

  }


  public static  int nMutiply(int n){
    if (1 == n){
      return 1;
    }
    return n * nMutiply(n-1);
  }

  public static  int nMutiply2(int n,int result){
    if (1== n){
      //终止条件
      return result;
    }
    return nMutiply2(n-1,n*result);
  }

  public static int fab(int n){
    if (n <= 2){
      return 1;
    }
    return fab(n-1)+fab(n-2);
  }

  public static int fab2(int n,int pre,int prePre){
    if (n <= 2){
      return pre;
    }
    return fab2(n-1,pre+prePre,pre);
  }

  public static void swap(Integer a,Integer b){
    Integer t = a;
    a = b;
    b = t;
  }

  public static void swap2(Integer a,Integer b){
    try {
      Field value = Integer.class.getDeclaredField("value");
      value.setAccessible(true);
      int t = a;
      value.set(a,b);//Integer(2)
      //value.set(b,a);//Integer.valueOf(1)==>自动装箱 变成数组129的位置的值 拿到的是已经修改为2的数值
      //传入的是两个Object 会触发Integer的自动装箱操作
      value.set(b,new Integer(t));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void swap3(Integer a,Integer b){
    System.out.printf("swap3 a=%d,b=%d",b,a);
    System.exit(1);
  }
}
