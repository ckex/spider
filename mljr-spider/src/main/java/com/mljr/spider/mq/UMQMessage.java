/**
 * 
 */
package com.mljr.spider.mq;

//import com.ucloud.umq.action.MessageData;

/**
 * @author Ckex zha </br>
 *         2016年11月13日,下午8:35:01
 *
 */
public class UMQMessage {

  public final String messageId;
  public final String message;

  public UMQMessage(String messageId, String message) {
    super();
    this.messageId = messageId;
    this.message = message;
  }

//  public UMQMessage(MessageData msgData) {
//    this(msgData.getMsgId(), msgData.getMsgBody());
//  }

  @Override
  public String toString() {
    return "UMQMessage [messageId=" + messageId + ", message=" + message + "]";
  }

}
