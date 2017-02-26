/**
 * 
 */
package com.ucloud.umq.action;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ucloud.umq.client.ServerResponseException;
import com.ucloud.umq.common.ServiceConfig;
import com.ucloud.umq.model.Message;
import com.ucloud.umq.model.Role;

/**
 * @author Ckex zha </br>
 *         2016年11月9日,上午10:01:12
 *
 */
public class ConsumerTests {

  public ConsumerTests() {
    super();
  }

  public static void main(String[] args) throws ServerResponseException, InterruptedException {
    String publicKey = ServiceConfig.getPublicKey();
    String privateKey = ServiceConfig.getPrivateKey();
    UmqConnection.NewConnection(publicKey, privateKey);
    String region = "cn-bj2";
    String queueId = "umq-luj3bt";
    List<Role> consumers = RoleAction.createRole(region, queueId, 1, "Sub");
    Role consumer = consumers.get(0);
    while (true) {
      try {
        Message msg = MessageAction.pullMsg(region, queueId, consumer.getId(), consumer.getToken());
        System.out.println(msg.getMsgId());
        System.out.println(msg.getMsgBody());
        System.out.println(msg.getMsgTime());
        boolean succ = MessageAction.ackMsg(region, queueId, consumer.getId(), msg.getMsgId());
        System.out.println(succ);
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println(" ------------------------- \t\n");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
