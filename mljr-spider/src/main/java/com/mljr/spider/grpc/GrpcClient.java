/**
 * GrpcClient.java by Ckex.zha
 */
package com.mljr.spider.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ucloud.umq.common.ServiceConfig;

/**
 * @author Ckex.zha <br/>
 *         Nov 16, 201611:19:39 AM
 */
public class GrpcClient {

  protected transient Logger logger = LoggerFactory.getLogger(getClass());

  private final String rpcHost;
  private final int rpcPort;

  private final GrpcQueueClient client;

  private GrpcClient() {
    super();
    logger.info("INSTANCE GRPC CLIENT.");
    this.rpcHost = ServiceConfig.getRpcHost();
    this.rpcPort = ServiceConfig.getRpcPort();
    logger.info("GRPC INFO " + rpcHost + ":" + rpcPort);
    this.client = new GrpcQueueClient(rpcHost, rpcPort);
  }

  public static final GrpcClient getInstance() {
    return GrpcClientHolder.INSTANCE;
  }

  private static class GrpcClientHolder {
    private static final GrpcClient INSTANCE = new GrpcClient();
  }

  public String pullMsg(String queueId) {
    // logger.debug("get queue ===> "+queueId);
    return client.pullMsg(queueId);
  }
}
