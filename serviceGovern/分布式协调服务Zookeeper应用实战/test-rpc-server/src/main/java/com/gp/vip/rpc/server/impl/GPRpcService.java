package com.gp.vip.rpc.server.impl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.gp.vip.rpc.pojo.RequestMessageDto;
import com.gp.vip.rpc.server.IRegister;
import com.gp.vip.rpc.server.RpcService;

public class GPRpcService implements ApplicationContextAware, InitializingBean {

	private int port;
	
	public GPRpcService(int port) {
		this.port = port;
	}

	private IRegister registry = new RegisterServiceImpl();

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
		for (Object bean : beanMap.values()) {
			RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
			String serviceName = rpcService.value().getName();
			registry.register(serviceName, getAddress() + ":"+port);
		}

	}

	private static String getAddress() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inetAddress.getHostAddress();// 获得本机的ip地址
	}

	public void afterPropertiesSet() throws Exception {

        ServerSocket serverSocket = new ServerSocket(port);
        StudentServiceImpl service = new StudentServiceImpl();

        Socket socket = serverSocket.accept();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        RequestMessageDto request = (RequestMessageDto)ois.readObject();
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

        // 通过反射的方式调用方法
        Class clazz = Class.forName(request.getClassName());
        Method method = clazz.getMethod(request.getMethodName(), request.getParamType());
        Object result = method.invoke(service, request.getArgs());

        os.writeObject(result);
	}

}
