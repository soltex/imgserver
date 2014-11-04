/**
 * 
 */
package com.vanstone.img.server.common.strategy;

import javax.servlet.ServletRequest;

/**
 * 图片URL策略
 * @author shipeng
 */
public interface ServerRouteStrategy {

	/**
	 * 获取图片服务器地址,
	 * 如以"/"结尾，则去掉结尾 "/"
	 * @param scaleSize 
	 * @param quality
	 * @param watermark
	 * @param fileId
	 * @param extName
	 * @param servletRequest
	 * @return
	 */
	String retrievalImageServer(String scaleSize,int quality,boolean watermark,String fileId,String extName,ServletRequest servletRequest);
	
}
