package com.will.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * test
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:24 12:53
 **/
public class BioServerSocket {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket= new ServerSocket(10001);
    byte[] readContents = new byte[1024];
    while (true){
      System.out.println("begin accept...");
      //这里会阻塞
      Socket socket = serverSocket.accept();
      System.out.println("end accept...");
      //这里也会阻塞
      int read = socket.getInputStream().read(readContents);
      System.out.println("read over:size="+read);
    }
  }
}
