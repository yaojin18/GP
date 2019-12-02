package com.gp.vip.rpc.client;

import java.util.List;
import java.util.Random;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.shaded.com.google.common.collect.Lists;

import com.gp.vip.rpc.service.ServiceDiscovery;

public class ServiceDiscoveryImpl implements ServiceDiscovery {

	private List<String> resps = Lists.newArrayList();

	public CuratorFramework curatorFramework;

	{
		curatorFramework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("register").build();

		curatorFramework.start();
	}

	public String discovery(String serviceName) {
		try {
			if(resps.isEmpty()) {
				resps = curatorFramework.getChildren().forPath("/" + serviceName);
				registerWatch("/" + serviceName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Random random = new Random();
		int index = random.nextInt(resps.size());

		return resps.get(index);
	}

	private void registerWatch(String path) throws Exception {
		PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
		
		PathChildrenCacheListener pathChildrenCacheListener =  (client, event)->{
			resps = client.getChildren().forPath(path);
		};
		
		pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
		pathChildrenCache.start();
	}

}
