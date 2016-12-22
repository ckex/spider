/**
 * 
 */
package com.v8.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Ckex zha </br>
 *         2016年12月21日,下午8:46:23
 *
 */
public class StreamTests {

	/**
	 * 
	 */
	public StreamTests() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = Arrays.asList("abc1", "abc2", "abc3", "abc4", "abc5");
		Optional.ofNullable(list).ifPresent(x -> {
			System.out.println(x);
		});
		list.add("abc1");
		list.add("abc2");
		list.add("abc3");
		list.add("abc4");
		list.add("abc5");
		System.out.println("--------");
		list.stream().filter(x -> {
			return !isOk(x);
		}).findFirst();

	}

	private static boolean isOk(String val) {
		System.out.println(val);
		return !val.endsWith("3");
	}

}
