package com.example;

public class Info {
String app;
String port;
public String getApp() {
	return app;
}
public void setApp(String app) {
	this.app = app;
}
public String getPort() {
	return port;
}
public void setPort(String port) {
	this.port = port;
}
public Info(String app, String port) {
	super();
	this.app = app;
	this.port = port;
}
public Info() {
	super();
	// TODO Auto-generated constructor stub
}


}
