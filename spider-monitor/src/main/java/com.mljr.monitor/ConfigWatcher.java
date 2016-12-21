package com.mljr.monitor;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * 配置监控
 *
 */
public class ConfigWatcher implements Watcher{
	private static CuratorFramework client;
	List<String> children ;
	public ConfigWatcher() {
		if (client == null) {
			String connectString = "127.0.0.1:2181";
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
			client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
			client.start();
		}

		try {
			children = client.getChildren().usingWatcher(this).forPath("/config");
			System.out.println("children = " + children);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent arg0) {
		try {
			List<String> newChildren = client.getChildren().usingWatcher(this).forPath("/config");
			System.out.println("newChildren = "+newChildren);
			for (String node :children) {
				if(!newChildren.contains(node)){
					System.out.println("this node is changed   "+ node );
				}
			}
			for (String node : newChildren) {
				if(!children.contains(node)){
					System.out.println("新增节点:"+node);
				}
			}
			
			children = newChildren;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConfigWatcher spiderWatcher = new ConfigWatcher();
		spiderWatcher.start();
	}
	
	/**
	 * 让当前进程一直运行
	 */
	private void start() {
		while(true){
			;
		}
	}

}
