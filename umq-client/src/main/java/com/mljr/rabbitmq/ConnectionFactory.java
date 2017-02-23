/**
 * 
 */
package com.mljr.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.ucloud.umq.common.ServiceConfig;

import net.jodah.lyra.ConnectionOptions;
import net.jodah.lyra.Connections;
import net.jodah.lyra.config.Config;
import net.jodah.lyra.config.RecoveryPolicies;
import net.jodah.lyra.config.RetryPolicy;
import net.jodah.lyra.util.Duration;

/**
 * @author Ckex zha </br>
 *         2016年11月25日,上午9:45:23
 *
 */
public class ConnectionFactory {

  private final ConnectionOptions options;

  private ConnectionFactory() {
    super();
    this.options = new ConnectionOptions().withHost(ServiceConfig.getRmqHost()).withPort(ServiceConfig.getRmqPort())
        .withUsername(ServiceConfig.getRmqUsername()).withPassword(ServiceConfig.getRmqPassword()).withVirtualHost(ServiceConfig.getRmqVhost());
  }

  protected static Connection newConnection() throws IOException, TimeoutException {
    Config config = new Config().withRecoveryPolicy(RecoveryPolicies.recoverAlways())
        .withRetryPolicy(new RetryPolicy().withMaxAttempts(15).withInterval(Duration.seconds(5)).withMaxDuration(Duration.minutes(8)));
    Connection connection = Connections.create(FactoryHolder.FA.options, config);
    return connection;
  }

  private static final class FactoryHolder {
    private static final ConnectionFactory FA = new ConnectionFactory();
  }
}
