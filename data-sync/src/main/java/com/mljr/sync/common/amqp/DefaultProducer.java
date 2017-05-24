package com.mljr.sync.common.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ckex on 24/05/2017.
 */
@Service
public class DefaultProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @FunctionalInterface
    public interface SentTemplate<T> {
        void sentMsg(T t);
    }

    public void sent(SentTemplate<? super AmqpTemplate> template) {
        template.sentMsg(amqpTemplate);
    }


}
