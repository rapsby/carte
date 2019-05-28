package com.o2o.action.server.rest;

public class Study1JSON {
	private String name;
	private long age;
	
	public Study1JSON() {
	}
	
	public Study1JSON(String name, long age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}
	
	
}
