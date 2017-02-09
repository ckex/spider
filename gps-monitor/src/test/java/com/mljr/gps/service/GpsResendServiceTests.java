package com.mljr.gps.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class GpsResendServiceTests {

	private GpsResendService gpsResendService = new GpsResendService();

	public GpsResendServiceTests() {
	}

	public static void main(String[] args) {
		GpsResendServiceTests test = new GpsResendServiceTests();
//		test.run();
		 test.sent();
	}

	private void run() {
		String fileName = "/Users/ckex/Desktop/2017-01-08-05-08.json";
		fileName = "/Users/ckex/Desktop/2017-01-09-01-08.json";
		File file = new File(fileName);
		Map<String, Object> dataMap = gpsResendService.parseJsonFile(file);
		List<String> dataList = (List<String>) dataMap.get("dataList");
		for (String str : dataList) {
			JSONObject jsonObject = JSONObject.parseObject(str);
			System.out.println(jsonObject.toJSONString());
			System.out.println();
		}
	}

	private void sent() {
		String fileName = "/Users/ckex/Desktop/2017-01-08-05-08.json";
		gpsResendService.resend(fileName);
	}
}
//