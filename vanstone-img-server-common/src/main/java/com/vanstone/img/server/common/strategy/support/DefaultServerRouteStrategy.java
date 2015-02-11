/**
 * 
 */
package com.vanstone.img.server.common.strategy.support;

import com.vanstone.img.server.common.Constants;
import com.vanstone.img.server.common.strategy.AbstractServerRouteStrategy;

/**
 * 简单方式<br>
 * 随机Server地址信息,默认是实现方式为随机从地址表中获取地址信息
 * @author shipeng
 */
public class DefaultServerRouteStrategy extends AbstractServerRouteStrategy {

	@Override
	public String retrievalImageServerInternal(String scaleSize, int quality, boolean watermark, String fileId, String extName) {
		return this.getImgServerConf().getHttpserverAddresses()[Constants.SYSTEM_RANDOM.nextInt(this.getImgServerConf().getHttpserverAddresses().length)];
	}
	
}
