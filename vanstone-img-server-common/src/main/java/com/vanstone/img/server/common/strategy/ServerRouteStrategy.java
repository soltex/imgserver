/**
 * 
 */
package com.vanstone.img.server.common.strategy;


/**
 * 图片URL策略
 * @author shipeng
 */
public interface ServerRouteStrategy {

	/**
	 * 获取图片服务器地址,可通过传入参数自定义路由策略
	 * 如以"/"结尾，则去掉结尾 "/"
	 * @param scaleSize 
	 * @param quality
	 * @param watermark
	 * @param fileId
	 * @param extName
	 * @return
	 */
	String retrievalImageServer(String scaleSize,int quality,boolean watermark,String fileId,String extName);
	
}
