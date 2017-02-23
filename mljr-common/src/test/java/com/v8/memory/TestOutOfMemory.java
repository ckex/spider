/**
 * 
 */
package com.v8.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ckex zha </br>
 *         Jan 10, 2017,11:56:58 AM
 *
 */
public class TestOutOfMemory {

  /**
   * 
   */
  public TestOutOfMemory() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    List<Object> res = new ArrayList<>();
    while (true) {
      res.add(new byte[1024]);
    }

  }

}
