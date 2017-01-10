package com.mljr.spider.phantomjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PhantomJSTest {

	public PhantomJSTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("phantomjs -v");

		InputStream is = process.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			stringBuffer.append(line).append("\n");
		}
		System.out.println(stringBuffer.toString());
	}

}
