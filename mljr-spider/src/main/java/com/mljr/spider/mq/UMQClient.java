///**
// * 
// */
//package com.mljr.spider.mq;
//
//import java.net.URISyntaxException;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.ucloud.umq.action.HttpClient;
//import com.ucloud.umq.action.MessageData;
//import com.ucloud.umq.action.MsgHandler;
//import com.ucloud.umq.client.ServerResponseException;
//import com.ucloud.umq.common.ServiceConfig;
//
///**
// * @author Ckex zha </br>
// *         2016年11月9日,下午3:10:55
// *
// */
//public class UMQClient {
//
//  protected transient Logger logger = LoggerFactory.getLogger(getClass());
//
//  private final HttpClient client;
//
//  private final String host;
//  private final String publicKey;
//  private final String privateKey;
//  private final String region;
//  private final String account;
//  private final String projectId;
//  private final String consumerId;
//  private final String consumerToken;
//  private final Integer organizationId;
//
//  private UMQClient() {
//    super();
//    this.host = ServiceConfig.getHost();
//    this.region = ServiceConfig.getRegion();
//    this.account = ServiceConfig.getAccount();
//    this.publicKey = ServiceConfig.getPublicKey();
//    this.projectId = ServiceConfig.getProjectId();
//    this.consumerId = ServiceConfig.getConsumerId();
//    this.privateKey = ServiceConfig.getPrivateKey();
//    this.consumerToken = ServiceConfig.getConsumerToken();
//    this.organizationId = ServiceConfig.getOrganizationId();
//    this.client = initConnection();
//  }
//
//  private HttpClient initConnection() {
//    return HttpClient.NewClient(this.host, this.publicKey, this.privateKey, this.region, this.account, this.projectId);
//  }
//
//  private static class UMQClientHolder {
//    private static final UMQClient instance = new UMQClient();
//  }
//
//  public static final UMQClient getInstence() {
//    return UMQClientHolder.instance;
//  }
//
//  public void subscribeQueue(MessageHandler handler) throws URISyntaxException {
//    logger.info("subscribe queue " + handler.queueId);
//    this.client.subscribeQueue(this.organizationId, handler.queueId, consumerId, consumerToken, handler);
//  }
//
//  private void ackMessage(String queueId, MessageData msg) {
//    try {
//      this.client.ackMsg(queueId, consumerId, msg.getMsgId());
//    } catch (ServerResponseException e) {
//      logger.error(String.format("%s %s Ack message error %s", queueId, msg.toString(), ExceptionUtils.getStackTrace(e)));
//    }
//  }
//
//  public abstract class MessageHandler implements MsgHandler {
//
//    protected final String queueId;
//
//    public MessageHandler(String queueId) {
//      super();
//      this.queueId = queueId;
//    }
//
//    public abstract boolean processMsg(MessageData msg);
//
//    @Override
//    public boolean process(MessageData msg) {
//      if (msg == null || StringUtils.isBlank(msg.getMsgId())) {
//        logger.debug("Msg is Empty.");
//        return false;
//      }
//      // boolean ret = false;
//      // try {
//      // ret = processMsg(msg);
//      // } finally {
//      // if (ret) {
//      // ackMessage(queueId, msg);
//      // }
//      // }
//      // return ret;
//      return processMsg(msg);
//    }
//
//  }
//
//  // public Message getMessage(String region, String role, String queueId)
//  // throws ServerResponseException {
//  // if (StringUtils.isBlank(region)) {
//  // region = ServiceAttributes.getRegion();
//  // }
//  // if (StringUtils.isBlank(role)) {
//  // role = ServiceAttributes.getRole();
//  // }
//  // if (StringUtils.isBlank(queueId)) {
//  // throw new IllegalArgumentException("QueueID can't be null.");
//  // }
//  // // List<Role> consumers = RoleAction.createRole(region, queueId, 1,
//  // // role);
//  // // Role consumer = consumers.get(0);
//  // // return MessageAction.pullMsg(region, queueId, consumer.getId(),
//  // // consumer.getToken());
//  // return MessageAction.pullMsg(region, queueId,
//  // ServiceAttributes.getConsumerId(),
//  // ServiceAttributes.getConsumerToken());
//  // }
//
//  // public boolean ackMsg(String region, String role, String queueId, String
//  // msgId) throws ServerResponseException {
//  // if (StringUtils.isBlank(region)) {
//  // region = ServiceAttributes.getRegion();
//  // }
//  // if (StringUtils.isBlank(queueId)) {
//  // throw new IllegalArgumentException("QueueID can't be null.");
//  // }
//  // List<Role> consumers = RoleAction.createRole(region, queueId, 1, role);
//  // Role consumer = consumers.get(0);
//  // return MessageAction.ackMsg(region, queueId, consumer.getId(), msgId);
//  // }
//}
