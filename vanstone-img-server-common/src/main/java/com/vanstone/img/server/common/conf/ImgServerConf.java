/**
 * 
 */
package com.vanstone.img.server.common.conf;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.img.server.common.Constants;
import com.vanstone.img.server.common.strategy.DefaultServerRouteStrategy;
import com.vanstone.img.server.common.strategy.ServerRouteStrategy;

/**
 * 图片服务器配置
 * @author shipeng
 */
public class ImgServerConf {

	private static Logger LOG = LoggerFactory.getLogger(ImgServerConf.class);
	
	/***/
	private static ImgServerConf conf = new ImgServerConf();

	/**
	 * 原始文件存储目录
	 */
	public static final String DIR_ORIG = "orig";

	/**
	 * 配置文件
	 */
	public static final String CONF_PATH = "/imgserver.properties";

	/**
	 * 处理完成后，输出目录
	 */
	public String outputDir;
	/**
	 * 水印文件
	 */
	private File watermarkFile;
	/**
	 * 水印位置
	 */
	private String watermarkGravity;
	
	/**httpserver 地址信息*/
	private String[] httpserverAddresses;
	
	/**
	 * 默认server路由器
	 */
	private ServerRouteStrategy serverRouteStrategy;
	/**
	 * 默认路由类
	 */
	private String routeClass = DefaultServerRouteStrategy.class.getName();
	
	/**
	 * httpserver地址信息数量
	 */
	private int httpserverCount = 0;
	
	public static ImgServerConf getConf() {
		LOG.debug(ToStringBuilder.reflectionToString(conf, ToStringStyle.SHORT_PREFIX_STYLE));
		return conf;
	}

	private ImgServerConf() {
		Properties properties = new Properties();
		try {
			properties.load(ImgServerConf.class.getResourceAsStream(CONF_PATH));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
		this.outputDir = properties.getProperty("imgserver.output");
		if (this.outputDir == null || "".equals(outputDir)) {
			throw new ExceptionInInitializerError();
		}
		if (this.outputDir.endsWith("/")) {
			this.outputDir = this.outputDir.substring(0,
					this.outputDir.length());
		}
		if (!new File(this.outputDir).exists()) {
			new File(this.outputDir).mkdirs();
		}
		if (!new File(this.outputDir + "/" + DIR_ORIG).exists()) {
			new File(this.outputDir + "/" + DIR_ORIG).mkdirs();
		}
		
		// 水印信息初始化
		String watermark = properties.getProperty("imgserver.watermark");
		if (watermark == null || "".equals(watermark)) {
			this.watermarkFile = new File(watermark);
			if (!this.watermarkFile.exists()) {
				throw new IllegalArgumentException("watermark not exists");
			}
			this.watermarkGravity = properties.getProperty("imgserver.watermark.gravity");
			if (this.watermarkGravity == null || "".equals(this.watermarkGravity)) {
				throw new IllegalArgumentException("watermark gravity not exists");
			}
		}
		
		//httpserverAddresses初始化
		if (!StringUtils.isEmpty(properties.getProperty("httpserver.addresses"))) {
			String strs = properties.getProperty("httpserver.addresses");
			httpserverAddresses = StringUtils.split(strs, ',');
			Set<String> tempSet = new LinkedHashSet<String>();
			for (String httpserverAddress : httpserverAddresses) {
				if (httpserverAddress.endsWith(Constants.URL_SEPARATOR)) {
					httpserverAddress = httpserverAddress.substring(0, httpserverAddress.length()-1);
				}
				tempSet.add(httpserverAddress);
			}
			this.httpserverAddresses = tempSet.toArray(new String[tempSet.size()]);
			this.httpserverCount = tempSet.size();
		}
		
		//httpserver.routeclass
		if (!StringUtils.isEmpty(properties.getProperty("httpserver.routeclass"))) {
			this.routeClass = properties.getProperty("httpserver.routeclass");
		}
		try {
			this.serverRouteStrategy = (ServerRouteStrategy)Class.forName(this.routeClass).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 获取输出目录
	 * 
	 * @return
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * 获取水印文件
	 * 
	 * @return
	 */
	public File getWatermarkFile() {
		return watermarkFile;
	}
	
 	/**
	 * 获取水印位置
	 * 
	 * @return
	 */
	public String getWatermarkGravity() {
		return watermarkGravity;
	}

	/**
	 * 获取httpserver Addresses
	 * @return
	 */
	public String[] getHttpserverAddresses() {
		return httpserverAddresses;
	}
	
	/**
	 * 是否存在httpserver addresses
	 * @return
	 */
	public boolean existsHttpserverAddresses() {
		return this.httpserverCount > 0;
	}

	/**
	 * 获取httpserver地址数量
	 * @return
	 */
	public int getHttpserverCount() {
		return httpserverCount;
	}
	
	/**
	 * 是否为单一httpserver
	 * @return
	 */
	public boolean isSingleHttpserver() {
		return this.httpserverCount == 1;
	}

	/**
	 * 当前配置的路由策略
	 * @return
	 */
	public ServerRouteStrategy getServerRouteStrategy() {
		return serverRouteStrategy;
	}
	
}
