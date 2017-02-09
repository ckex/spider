/**
 * 
 */
package com.mljr.spider.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

//		String jsonText = readJsonText();
//		System.out.println(jsonText.length());
//		Gson gson = new GsonBuilder().create();
//		// gson.fromJson(jsonText, Map.class);
//		// JSON.parse(jsonText);
//		JSONObject jsonObject = JSON.parseObject(jsonText);

//		String jsonText = readJsonText();
		String jsonText = "[\n" +
				"\t\n" +
				"\t\t{\n" +
				"\tID:\"1445\",\n" +
				"\tName:\"宝马2系Gran Tourer\",\n" +
				"\tPureName:\"宝马2系Gran Tourer\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1295\",\n" +
				"\tName:\"宝马2系Active Tourer\",\n" +
				"\tPureName:\"宝马2系Active Tourer\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1245\",\n" +
				"\tName:\"宝马M4\",\n" +
				"\tPureName:\"宝马M4\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"241\",\n" +
				"\tBrandName:\"进口BMW M\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1241\",\n" +
				"\tName:\"宝马i8\",\n" +
				"\tPureName:\"宝马i8\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1214\",\n" +
				"\tName:\"宝马X4\",\n" +
				"\tPureName:\"宝马X4\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1161\",\n" +
				"\tName:\"宝马2系\",\n" +
				"\tPureName:\"宝马2系\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1134\",\n" +
				"\tName:\"宝马i3\",\n" +
				"\tPureName:\"宝马i3\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1114\",\n" +
				"\tName:\"宝马4系\",\n" +
				"\tPureName:\"宝马4系\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1070\",\n" +
				"\tName:\"宝马X6 M\",\n" +
				"\tPureName:\"宝马X6 M\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"241\",\n" +
				"\tBrandName:\"进口BMW M\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1069\",\n" +
				"\tName:\"宝马X5 M\",\n" +
				"\tPureName:\"宝马X5 M\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"241\",\n" +
				"\tBrandName:\"进口BMW M\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1051\",\n" +
				"\tName:\"宝马3系GT\",\n" +
				"\tPureName:\"宝马3系GT\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"1031\",\n" +
				"\tName:\"宝马5系GT\",\n" +
				"\tPureName:\"宝马5系GT\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"903\",\n" +
				"\tName:\"华晨宝马X1\",\n" +
				"\tPureName:\"华晨宝马X1\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"112\",\n" +
				"\tBrandName:\"华晨宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"766\",\n" +
				"\tName:\"宝马M6\",\n" +
				"\tPureName:\"宝马M6\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"241\",\n" +
				"\tBrandName:\"进口BMW M\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"765\",\n" +
				"\tName:\"宝马M5\",\n" +
				"\tPureName:\"宝马M5\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"241\",\n" +
				"\tBrandName:\"进口BMW M\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"764\",\n" +
				"\tName:\"宝马M3\",\n" +
				"\tPureName:\"宝马M3\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"241\",\n" +
				"\tBrandName:\"进口BMW M\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"715\",\n" +
				"\tName:\"宝马X1(进口)\",\n" +
				"\tPureName:\"宝马X1(进口)\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"476\",\n" +
				"\tName:\"宝马X6\",\n" +
				"\tPureName:\"宝马X6\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"228\",\n" +
				"\tName:\"宝马Z4\",\n" +
				"\tPureName:\"宝马Z4\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"227\",\n" +
				"\tName:\"宝马X5\",\n" +
				"\tPureName:\"宝马X5\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"226\",\n" +
				"\tName:\"宝马X3\",\n" +
				"\tPureName:\"宝马X3\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"225\",\n" +
				"\tName:\"宝马7系\",\n" +
				"\tPureName:\"宝马7系\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"224\",\n" +
				"\tName:\"宝马6系\",\n" +
				"\tPureName:\"宝马6系\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"223\",\n" +
				"\tName:\"宝马5系(进口)\",\n" +
				"\tPureName:\"宝马5系(进口)\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"222\",\n" +
				"\tName:\"宝马3系(进口)\",\n" +
				"\tPureName:\"宝马3系(进口)\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"221\",\n" +
				"\tName:\"宝马1系\",\n" +
				"\tPureName:\"宝马1系\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"141\",\n" +
				"\tBrandName:\"进口宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"114\",\n" +
				"\tName:\"宝马5系\",\n" +
				"\tPureName:\"宝马5系\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"112\",\n" +
				"\tBrandName:\"华晨宝马\"\n" +
				"\t}\n" +
				"\t\t,\n" +
				"\t\t\n" +
				"\t\t{\n" +
				"\tID:\"113\",\n" +
				"\tName:\"宝马3系\",\n" +
				"\tPureName:\"宝马3系\",\n" +
				"\tManufacturerId:\"6\",\n" +
				"\tBrandId:\"112\",\n" +
				"\tBrandName:\"华晨宝马\"\n" +
				"\t}\n" +
				"\t\t\n" +
				"\t\t ]";
		Gson gson = new GsonBuilder().create();
		List<String> list = gson.fromJson(jsonText, ArrayList.class);
		for (Object str : list) {
			if (str instanceof LinkedTreeMap){
				Map m = (LinkedTreeMap)str;
				System.out.println(m.get("ID"));
			}

		}


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
