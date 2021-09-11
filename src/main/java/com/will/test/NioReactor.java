package com.will.test;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * test
 *
 * @author AnonymousCodeMaker
 * @create 2021:07:24 17:18
 **/
public class NioReactor {

  public static void main(String[] args) throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    Selector selector = Selector.open();
    while (true){
      int fdValidNumber = selector.select();
      Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
      while(selectionKeyIterator.hasNext()){
        //TODO

      }
    }

  }
}
