package com.yeepay.g3;

import java.io.File;

/**
 * @author wencheng
 * @since 2022/8/23
 */
public class Application {

	public static void main(String[] args) throws Exception {
		String path = System.getProperty("user.dir");
		ChannelSdkManager channelSdkManager = new ChannelSdkManager();
		channelSdkManager.registerSdk("a", new File(path + "/lib/sdk-a-1.0-SNAPSHOT.jar"), new File(path + "/lib/cipher-1.0.jar"), "org.example.ASdk", "sdk");
		channelSdkManager.registerSdk("b", new File(path + "/lib2/sdk-b-1.0-SNAPSHOT.jar"), new File(path + "/lib2/cipher-2.0.jar"), "org.example.BSdk", "sdk");
		channelSdkManager.getSdk("a").getMethod("sdk").invoke(channelSdkManager.getSdk("a").newInstance());
		channelSdkManager.getSdk("b").getMethod("sdk").invoke(channelSdkManager.getSdk("b").newInstance());

	}

}
