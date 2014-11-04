/**
 * 
 */
package com.vanstone.img.server.common.strategy.support;

import javax.servlet.ServletRequest;

import com.vanstone.img.server.common.strategy.AbstractServerRouteStrategy;

/**
 * 文件ID Hash 方式 路由Server
 * @author shipeng
 */
public class WeedFSFileIDServerRouteStrategy extends AbstractServerRouteStrategy {
	
	@Override
	public String retrievalImageServerInternal(String scaleSize,int quality,boolean watermark,String fileId,String extName,ServletRequest servletRequest) {
		if (fileId == null || "".equals(fileId)) {
			throw new IllegalArgumentException("FILEID IS NULL");
		}
		int vol = this.weedfsFileId2VolInt(fileId);
		int mod = vol%this.getImgServerConf().getHttpserverCount();
		return this.getImgServerConf().getHttpserverAddresses()[mod];
	}
	
	/**
	 * WeedFS文件ID转Long类型
	 * @param fileId
	 * @return
	 */
	private int weedfsFileId2VolInt(String fileId) {
		int n = fileId.indexOf(",");
		int vol = Integer.parseInt(fileId.substring(0, n));
		return vol;
	}
	
//	public static void main(String[] args) {
//		String fileId = "12,232323ersdfsdfsdfs.jpg";
//		WeedFSFileIDServerRouteStrategy strategy = new WeedFSFileIDServerRouteStrategy();
//		System.out.println(strategy.retrievalImageServer(null, 1, false, fileId, null, null));
//	}
}
