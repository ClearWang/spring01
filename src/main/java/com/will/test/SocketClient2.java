package com.will.test;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * test
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:24 13:04
 **/
public class SocketClient2 {

  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("127.0.0.1",10001);
    Scanner scanner = new Scanner(System.in);
    while(true){
      String input = scanner.nextLine();
      if ("exit".equals(input)) {
        break;
      }
      socket.getOutputStream().write(input.getBytes());
    }
    socket.close();
  }

  private static String getConsoleInput() {
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNextLine()) {
      return scanner.nextLine();
    } else {
      return getConsoleInput();
    }
  }

}
