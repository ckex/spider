/**
 * 
 */
package com.mljr.spider.umq;

import com.ucloud.umq.action.MessageData;

/**
 * @author Ckex zha </br>
 *         2016年11月13日,下午8:35:01
 *
 */
public class UMQMessage {
	public final MessageData msgData;

	public UMQMessage(MessageData msgData) {
		super();
		this.msgData = msgData;
	}
}