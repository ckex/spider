/**
 * 
 */
package com.mljr.rabbitmq;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;

/**
 * @author Ckex zha </br>
 *         2016年11月25日,上午11:23:50
 *
 */
public class ConnectionTests {

  /**
   * 
   */
  public ConnectionTests() {
    // TODO Auto-generated constructor stub
  }

  /**
   * @param args
   * @throws TimeoutException
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    Connection conn = ConnectionFactory.newConnection();
    System.out.println(conn.toString());
    conn.close();
  }

}
