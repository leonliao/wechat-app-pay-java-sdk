package com.leonoss.wechat.apppay.cfg;

public class HttpConf {
	public int numberOfConnections = 5;
	public int timeOutInSeconds = -1;
	@Override
	public String toString() {
		return "HttpConf [numberOfConnections=" + numberOfConnections
				+ ", timeOutInSeconds=" + timeOutInSeconds + "]";
	}
}
