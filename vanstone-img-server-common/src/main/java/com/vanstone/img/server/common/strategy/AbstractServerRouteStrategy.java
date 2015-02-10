/**
 * 
 */
package com.vanstone.img.server.common.strategy;

import javax.servlet.ServletRequest;

import com.vanstone.common.component.hash.MurmurHash3;
import com.vanstone.img.server.common.conf.ImgServerConf;

/**
 * 抽象路由策略
 * @author shipeng
 */
public abstract class AbstractServerRouteStrategy implements ServerRouteStrategy {
	
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
	public String retrievalImageServer(String scaleSize,int quality,boolean watermark,String fileId,String extName,ServletRequest servletRequest) {
		if (!this.getImgServerConf().existsHttpserverAddresses()) {
			return null;
		}
		int hash = MurmurHash3.MurmurHash3_x64_32(fileId.getBytes(), 1000);
		int lenght = this.getImgServerConf().getHttpserverCount();
		int index = Math.abs(hash%lenght);
//		if (this.getImgServerConf().getHttpserverAddresses().length == 1) {
//			return this.getImgServerConf().getHttpserverAddresses()[0];
//		}
		String prefix = this.getImgServerConf().getHttpserverAddresses()[index];
		return this.retrievalImageServerInternal(prefix, scaleSize, quality, watermark, fileId, extName,servletRequest);
		
	}
	
	/**
	 * 内部实现
	 * @param params
	 * @return
	 */
	public abstract String retrievalImageServerInternal(String url, String scaleSize,int quality,boolean watermark,String fileId,String extName,ServletRequest servletRequest);
	
}
