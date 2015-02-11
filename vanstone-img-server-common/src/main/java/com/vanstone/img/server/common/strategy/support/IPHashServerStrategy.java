/**
 * 
 */
package com.vanstone.img.server.common.strategy.support;


/**
 * 根据IP地址路由Server
 * @author shipeng
 *
 */
public class IPHashServerStrategy {

//	@Override
//	public String retrievalImageServerInternal(String scaleSize,int quality,boolean watermark,String fileId,String extName,ServletRequest servletRequest) {
//		long num = this.ip2long(ServletUtils.getRemotetIP((HttpServletRequest)servletRequest));
//		int mod = (int)(num%this.getImgServerConf().getHttpserverCount());
//		return this.getImgServerConf().getHttpserverAddresses()[mod];
//	}
//	
//	private long ip2long(String ip) {
//		if (StringUtils.isEmpty(ip) || StringUtils.isBlank(ip)) {
//			throw new IllegalArgumentException();
//		}
//		ip = StringUtils.replace(ip, ".", "");
//		return Long.parseLong(ip);
//	}
}
