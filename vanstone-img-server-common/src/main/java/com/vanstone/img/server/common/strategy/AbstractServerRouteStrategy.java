/**
 * 
 */
package com.vanstone.img.server.common.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.img.server.common.conf.ImgServerConf;

/**
 * 抽象路由策略
 * @author shipeng
 */
public abstract class AbstractServerRouteStrategy implements ServerRouteStrategy {
	
	private static Logger LOG = LoggerFactory.getLogger(AbstractServerRouteStrategy.class);
	
	/**
	 * 获取当前服务器配置信息
	 * @return
	 */
	public ImgServerConf getImgServerConf() {
		return ImgServerConf.getConf();
	}

	/* (non-Javadoc)
	 * @see com.vanstone.img.server.common.strategy.ServerRouteStrategy#retrievalImageServer(java.lang.String, int, boolean, java.lang.String, java.lang.String, javax.servlet.ServletRequest)
	 */
	@Override
	public String retrievalImageServer(String scaleSize,int quality,boolean watermark,String fileId,String extName) {
		if (!this.getImgServerConf().existsHttpserverAddresses()) {
			LOG.error("Img Server Not Config.");
			return null;
		}
		String serverURL = this.retrievalImageServerInternal(scaleSize, quality, watermark, fileId, extName);
		if (serverURL.endsWith("/")) {
			serverURL = serverURL.substring(0, serverURL.length() - 1);
		}
		return serverURL;
	}
	
	/**
	 * 内部实现
	 * @param params
	 * @return
	 */
	public abstract String retrievalImageServerInternal(String scaleSize,int quality,boolean watermark,String fileId,String extName);
	
}
