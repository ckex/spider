/**
 * 
 */
package com.mljr.spider.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

/**
 * @author Ckex zha </br>
 *         Dec 27, 2016,1:56:04 PM
 *
 */
public class GsonTests {

	/**
	 * 
	 */
	public GsonTests() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String jsonText = readJsonText();
		System.out.println(jsonText.length());
		Gson gson = new GsonBuilder().create();
		// gson.fromJson(jsonText, Map.class);
		// JSON.parse(jsonText);
		JSONObject jsonObject = JSON.parseObject(jsonText);

	}

	public static String readJsonText() {
		String filePath = "/Users/ckex/Desktop/gps.json";
		filePath = "/Users/ckex/Desktop/1485063511129-part-0000.json";
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuffer result = new StringBuffer();
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					result.append(lineTxt);
				}
				read.close();
				return result.toString();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return null;
	}

}
