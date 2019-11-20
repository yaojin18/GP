package com.example.demo.format.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class JMXMain {

	public static void main(String[] args) throws Exception {
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
		
		ObjectName on = new ObjectName("com.example.demo.format.jmx.Mechine:type=mechine");
		MechineMBean mechineMBean = new Mechine();
		
		beanServer.registerMBean(mechineMBean, on);
		System.in.read();
	}

}
