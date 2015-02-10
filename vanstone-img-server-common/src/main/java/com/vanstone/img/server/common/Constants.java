/**
 * 
 */
package com.vanstone.img.server.common;

import java.util.Random;

/**
 * @author shipeng
 *
 */
public class Constants {
	
	/**默认图片缩放质量*/
	public static final int DEFAULT_QUALITY = 80;
	
	/** 默认种子生成器*/
	public static final Random SYSTEM_RANDOM = new Random(System.currentTimeMillis());
	
	/**URL默认分隔符*/
	public static final String URL_SEPARATOR = "/";
	
	/**请求前缀*/
	public static final String HTTPSERVER_REQUEST_URL_PREFIX = "img";
	
	/**MUR种子值*/
	public static final int MUR_MUR_HASH_SEED = 19811228;
	
}
