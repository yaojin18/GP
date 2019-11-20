package com.example.demo.format.jmx;

public interface MechineMBean {

	int getCpuCore();
	
	long getFreeMemory();
	
	void shutdown();
}
