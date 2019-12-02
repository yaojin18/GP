package com.gp.vip.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.gp.vip.rpc.pojo.RequestMessageDto;
import com.gp.vip.rpc.service.ServiceDiscovery;

public class ProxyHandler implements InvocationHandler {

	private ServiceDiscovery serviceDiscovery;

	public ProxyHandler(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}

	public Object getProxyObject(Class clzz) {
		return Proxy.newProxyInstance(clzz.getClassLoader(), new Class[] { clzz }, this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// 组装发送rpc报文
		RequestMessageDto request = new RequestMessageDto();
		request.setArgs(args);
		request.setParamType(method.getParameterTypes());
		request.setMethodName(method.getName());
		request.setClassName(method.getDeclaringClass().getName());

		String serviceAddress = serviceDiscovery.discovery(method.getDeclaringClass().getName());

		return new SendRpcMessage(serviceAddress.split(":")[0], Integer.valueOf(serviceAddress.split(":")[1]), request)
				.sendMessage();
	}

}
