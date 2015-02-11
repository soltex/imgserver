/**
 * 
 */
package com.vanstone.img.server.common;

import com.vanstone.img.server.common.conf.ImgServerConf;

/**
 * @author shipeng
 */
public class ImageURLBuilder {
	
	/**
	 * img +  "/" + this.quality + "/" + (this.watermark ? 1:0) + "/" + this.scaleSize + "/" + fileid
	 * @param scaleSize
	 * @param quality
	 * @param watermark
	 * @param fileId
	 * @param extName
	 * @return
	 */
	public static String createImageURL(String scaleSize,int quality,boolean watermark,String fileId,String extName) {
		String server = ImgServerConf.getConf().getServerRouteStrategy().retrievalImageServer(scaleSize, quality, watermark, fileId, extName);
		StringBuffer sb = new StringBuffer();
		//http://img1.ecointel.com.cn
		sb.append(server).append(Constants.URL_SEPARATOR);
		//img
		sb.append(Constants.HTTPSERVER_REQUEST_URL_PREFIX).append(Constants.URL_SEPARATOR);
		//quality
		sb.append(quality).append(Constants.URL_SEPARATOR);
		//watermark
		sb.append(watermark ? 1:0).append(Constants.URL_SEPARATOR);
		//scaleSize
		sb.append(scaleSize).append(Constants.URL_SEPARATOR);
		//fileid
		sb.append(fileId).append(".").append(extName);
		return sb.toString();
	}
	
}
