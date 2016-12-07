package com.mljr.spider.queue;

import java.util.concurrent.TimeUnit;

class Annoyance extends Exception {
}

class Sneeze extends Annoyance {
}

class Human {

	// public static void main(String[] args) throws Exception {

	// try{
	// try {
	// throw new Sneeze();
	// }catch ( Annoyance a ) {
	// System.out.println("Caught Annoyance");
	// throw a;
	// }
	// }catch ( Sneeze s ) {
	// System.out.println("Caught Sneeze");
	// return;
	// }finally {
	// System.out.println("Hello world");
	// }
	//     }

	// 
	public static void main(String[] args) throws Exception {
		try {
			try {
				throw new Sneeze();
			} catch (Annoyance a) {
				System.out.println("Caught Annoyance");// 1
				TimeUnit.SECONDS.sleep(1);
//				throw new Annoyance();
				throw a;
			}
		} catch (Sneeze s) {
			System.out.println("Caught Sneeze");
			return;
		} finally {
			System.out.println("HELLO WORLD.");//2
		}
	}

}
