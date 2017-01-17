/**
 * 
 */
package com.mljr.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import com.rabbitmq.client.Channel;

/**
 * @author Ckex zha </br>
 *         Jan 16, 2017,5:08:17 PM
 *
 */
public class Rmq {

	private Channel channel;

	public Rmq() {
		super();
		this.channel = getChannel();
	}

	private Channel getChannel() {
		try {
			return RabbitmqClient.newChannel();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Create rabbitmq channel error. ", e);
		}

	}

	public boolean publish(Function<Channel, Boolean> function) {
		checkChannel();
		return function.apply(channel);
	}

	private synchronized void checkChannel() {
		if (!channel.isOpen()) {
			closed();
			this.channel = getChannel();
		}
	}

	public synchronized void closed() {
		if (channel != null) {
			try {
				channel.close();
			} catch (IOException | TimeoutException e) {
				e.printStackTrace();
			}
		}
	}

}
