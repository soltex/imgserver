/**
 * 
 */
package com.vanstone.img.server;

import com.vanstone.img.server.impl.ImgServerManagerImpl;

/**
 * @author shipeng
 *
 */
public class ImgServerManagerFactory {
	
	private static class ImgServerManagerInstance {
		private static final ImgServerManager instance = new ImgServerManagerImpl();
	}
	
	/**
	 * 获取单例
	 * @return
	 */
	public static ImgServerManager getInstance() {
		return ImgServerManagerInstance.instance;
	}
	
}
