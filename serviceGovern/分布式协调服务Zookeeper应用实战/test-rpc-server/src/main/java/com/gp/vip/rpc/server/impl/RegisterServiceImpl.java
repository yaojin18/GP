package com.gp.vip.rpc.server.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import com.gp.vip.rpc.server.IRegister;

public class RegisterServiceImpl implements IRegister {

	public CuratorFramework curatorFramework;

	{
		curatorFramework = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace("register").build();
		curatorFramework.start();
	}

	public void register(String serverName, String serviceAddress) {

		try {
			Stat stat = curatorFramework.checkExists().forPath("/" + serverName);

			if (null == stat) {
				curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
						.forPath("/" + serverName);
			}

			curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/" + serverName + "/" + serviceAddress);
			System.out.println("服务注册成功" + serverName + "/" + serviceAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
