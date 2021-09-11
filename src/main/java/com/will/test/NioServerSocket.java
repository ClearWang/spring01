package com.will.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * test
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:24 13:08
 **/
public class NioServerSocket {
  static List<SocketChannel> socketChannelList = new ArrayList<>();
  static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

  public static void main(String[] args) throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",10001));
    //1.设置为非阻塞
    serverSocketChannel.configureBlocking(false);

    while(true){
      //维护一个socket连接数组
      for (SocketChannel socketChannel : socketChannelList) {
        //打印结果
        printOutSocketValue(socketChannel);
      }
      SocketChannel socketChannel = serverSocketChannel.accept();
      if (null != socketChannel){
        //2.设置为非阻塞
        socketChannel.configureBlocking(false);
        socketChannelList.add(socketChannel);
        System.out.println("一个新的socket连接成功!当前维护的socket连接数:"+socketChannelList.size());
      }

    }
  }

  public static void printOutSocketValue(SocketChannel socketChannel) throws IOException {
    int read = socketChannel.read(byteBuffer);
    //大于0说明有数据
    if(read>0){
      byteBuffer.flip();
      byte[] bs = new byte[read];
      byteBuffer.get(bs);
      String content = new String(bs);
      System.out.println("server receive: "+content);
      byteBuffer.flip();
    }
  }

}
