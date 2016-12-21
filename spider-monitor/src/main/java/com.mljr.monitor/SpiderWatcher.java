package com.mljr.monitor;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Spider 使用Curator监控爬虫节点的生命周期
 *
 */
public class SpiderWatcher implements Watcher{
	private CuratorFramework client;
	List<String> children ;
	public SpiderWatcher() {
		String connectString = "127.0.0.1:2181";
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
		client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
		client.start();
		try {
			children = client.getChildren().usingWatcher(this).forPath("/spider");
			System.out.println("children = " + children);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
		try {
			List<String> newChildren = client.getChildren().usingWatcher(this).forPath("/spider");
			System.out.println("newChildren = "+newChildren);
			for (String node :children) {
				if(!newChildren.contains(node)){
					System.out.println(node+"节点消失了~~~~~~~~~~~~~~~~~~~~~~~~");
					//TODO-- 在这需要给管理员发送邮件或者短信提醒
					System.out.println("发封邮件给大飞哥-----------");
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
		SpiderWatcher spiderWatcher = new SpiderWatcher();
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
