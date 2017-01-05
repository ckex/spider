/**
 *
 */
package com.mljr.spider.listener;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.mljr.utils.IpUtils;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class StatusCodeListener extends AbstractMonitorCache implements SpiderListener, Serializable {
	private static final long serialVersionUID = 1L;

	private final String domain;

	public StatusCodeListener(String domain) {
		this.domain = domain;
	}

	@Override
	public void onSuccess(Request request) {
		update(true, request);
	}

	@Override
	public void onError(Request request) {
		update(false, request);
	}

	private void update(boolean isSuccess, Request request) {
		int statusCode = (Integer) request.getExtras().get("statusCode");
		Setter setter = data -> {
			Set<String> codes = new HashSet<>();
			codes.add(String.valueOf(statusCode));
			if (StringUtils.isNotBlank(data.getStatusCodes())) {
				codes.addAll(Splitter.on(",").omitEmptyStrings().splitToList(data.getStatusCodes()));
			}
			data.setTotalRequests(data.getTotalRequests() + 1);
			data.setStatusCodes(Joiner.on(",").join(codes));
			if (isSuccess) {
				data.setOnSuccessCount(data.getOnSuccessCount() + 1);
			} else {
				data.setOnErrorCount(data.getOnErrorCount() + 1);
			}
			switch (statusCode) {
			case 200:
				data.setFreq200(data.getFreq200() + 1);
				break;
			case 301:
				data.setFreq301(data.getFreq301() + 1);
				break;
			case 302:
				data.setFreq302(data.getFreq302() + 1);
				break;
			case 304:
				data.setFreq304(data.getFreq304() + 1);
				break;
			case 307:
				data.setFreq307(data.getFreq307() + 1);
				break;
			case 401:
				data.setFreq401(data.getFreq401() + 1);
				break;
			case 403:
				data.setFreq403(data.getFreq403() + 1);
				break;
			case 404:
				data.setFreq404(data.getFreq404() + 1);
				break;
			case 500:
				data.setFreq500(data.getFreq500() + 1);
				break;
			case 501:
				data.setFreq501(data.getFreq501() + 1);
				break;
			case 504:
				data.setFreq504(data.getFreq504() + 1);
				break;
			case 9999:
				data.setFreqParseFail(data.getFreqParseFail() + 1);
				break;
			default:
				logger.warn(" Invalid http code" + statusCode);
				break;
			}
		};
		updateValue(new LocalCacheKey(new Date(), IpUtils.getHostName(), domain), setter);
	}

}
