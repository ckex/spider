package com.mljr.sync.common.amqp;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * Created by ckex on 24/05/2017.
 */
@Service
public class DefaultConsumer {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private Advice statelessRetryOperationsInterceptorFactoryBean;

}
