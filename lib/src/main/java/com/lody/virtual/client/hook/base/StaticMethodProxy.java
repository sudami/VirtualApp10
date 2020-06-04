package com.lody.virtual.client.hook.base;
public class StaticMethodProxy extends MethodProxy {
	private String mName;
	public StaticMethodProxy(String name) {
		this.mName = name;
	}
	@Override
	public String getMethodName() {
		return mName;
	}
}
