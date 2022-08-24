package com.yeepay.g3;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wencheng
 * @since 2022/8/23
 */
public class ChannelSdkManager implements Closeable {

	private final Map<String, URLClassLoader> classLoaderMap = new HashMap<>();

	private final Map<String, String> sdkClassNameMap = new HashMap<>();

	private final Map<String, String> sdkMethodNameMap = new HashMap<>();


	public void registerSdk(String channelCode, File sdkJar, File dependentJar, String sdkClassName, String sdkMethodName) throws MalformedURLException {
		URLClassLoader classLoader = new URLClassLoader(new URL[]{sdkJar.toURI().toURL(), dependentJar.toURI().toURL()});
		classLoaderMap.put(channelCode, classLoader);
		sdkClassNameMap.put(channelCode, sdkClassName);
		sdkMethodNameMap.put(channelCode, sdkMethodName);
	}

	public Class<?> getSdk(String channelCode) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (!classLoaderMap.containsKey(channelCode)) {
			throw new IllegalArgumentException("Channel '" + channelCode + "' not registered");
		}

		Class<?> sdkClass = classLoaderMap.get(channelCode).loadClass(sdkClassNameMap.get(channelCode));
		return sdkClass;
//		return createSdkInstance(sdkClass);
	}

//	private Object createSdkInstance(Class<?> sdkClass) throws InstantiationException, IllegalAccessException {
//		return sdkClass.newInstance();
//	}

	@Override
	public void close() {
		classLoaderMap.values().forEach(cl -> {
			try {
				cl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
