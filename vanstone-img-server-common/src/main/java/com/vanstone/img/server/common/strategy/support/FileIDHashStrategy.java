/**
 * 
 */
package com.vanstone.img.server.common.strategy.support;

import com.vanstone.common.component.hash.MurmurHash3;
import com.vanstone.img.server.common.Constants;
import com.vanstone.img.server.common.strategy.AbstractServerRouteStrategy;

/**
 * 文件ID Hash 方式 路由Server
 * @author shipeng
 */
public class FileIDHashStrategy extends AbstractServerRouteStrategy {
	
	@Override
	public String retrievalImageServerInternal(String scaleSize,int quality,boolean watermark,String fileId,String extName) {
		if (fileId == null || "".equals(fileId)) {
			throw new IllegalArgumentException("FILEID IS NULL");
		}
		int hash = MurmurHash3.MurmurHash3_x64_32(fileId.getBytes(), Constants.MUR_MUR_HASH_SEED);
		int lenght = this.getImgServerConf().getHttpserverCount();
		int index = Math.abs(hash%lenght);
		return  this.getImgServerConf().getHttpserverAddresses()[index];
//		int vol = this.weedfsFileId2VolInt(fileId);
//		int mod = vol%this.getImgServerConf().getHttpserverCount();
//		return this.getImgServerConf().getHttpserverAddresses()[mod];
	}
	
//	/**
//	 * WeedFS文件ID转Long类型
//	 * @param fileId
//	 * @return
//	 */
//	private int weedfsFileId2VolInt(String fileId) {
//		int n = fileId.indexOf(",");
//		int vol = Integer.parseInt(fileId.substring(0, n));
//		return vol;
//	}
	
//	public static void main(String[] args) {
//		String fileId = "12,232323ersdfsdfsdfs.jpg";
//		WeedFSFileIDServerRouteStrategy strategy = new WeedFSFileIDServerRouteStrategy();
//		System.out.println(strategy.retrievalImageServer(null, 1, false, fileId, null, null));
//	}
}
