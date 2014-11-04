/**
 * 
 */
package com.vanstone.img.server.cmd;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.vanstone.img.server.common.conf.ImgServerConf;

/**
 * URL 参数 /img +  "/" + this.quality + "/" + (this.watermark ? 1:0) + "/" + this.scaleSize + "/" + fileid
 * @author shipeng
 */
public class ImageProcessCommand {
	
	/**
	 * URL Pattern
	 */
	public static final Pattern URL_PATTERN = Pattern.compile("/(\\d+)/(\\d+)/(.+)/(.+)");
	/**
	 * e100x100 模式
	 */
	public static final Pattern SCALE_EXTENT_PATTERN = Pattern.compile("e(\\d+)x(\\d+)");
	
	/**
	 * 100x100
	 */
	public static final Pattern SCALE_WIDTH_HEIGHT_PATTERN = Pattern.compile("(\\d+)x(\\d+)");
	
	/**
	 * 100x
	 */
	public static final Pattern SCALE_WIDHT_PATTERN = Pattern.compile("(\\d+)x");
	
	/**
	 * x100
	 */
	public static final Pattern SCALE_HEIGHT_PATTERN = Pattern.compile("x(\\d+)");
	
	/**
	 * 图片质量
	 */
	private int quality;
	/**
	 * 文件id
	 */
	private String fileid;
	
	/**
	 * 缩放尺寸
	 */
	private String scaleSize;
	
	/**
	 * 宽度
	 */
	private Integer width;
	
	/**
	 * 高度
	 */
	private Integer height;
	/**
	 * 是否扩展到画布
	 */
	private boolean extent = false;
	/**
	 * 是否增加水印
	 */
	private boolean watermark = false;
	
	private boolean origable = false;
	
	private ImageProcessCommand() {}
	
	/**
	 * 解析命令
	 * "/" + this.quality + "/" + (this.watermark ? 1:0) + "/" + this.scaleSize + "/" + fileid
	 * @param servletRequest
	 * @return
	 */
	public static ImageProcessCommand parseCommand(HttpServletRequest servletRequest) {
		if (servletRequest == null) {
			throw new IllegalArgumentException();
		}
		ImageProcessCommand command = new ImageProcessCommand();
		String requestURI = servletRequest.getRequestURI();
		if (requestURI == null || "".equals(requestURI)) {
			throw new IllegalArgumentException();
		}
		Matcher matcher = URL_PATTERN.matcher(requestURI);
		if (!matcher.find()) {
			throw new IllegalArgumentException();
		}
		command.quality = Integer.parseInt(matcher.group(1));
		command.watermark = (matcher.group(2) != null && matcher.group(2).equals("1")) ? true : false;
		command.scaleSize = matcher.group(3);
		command.fileid = matcher.group(4);
		_parseScaleSize(command.scaleSize,command);
		return command;
	}
	
	/**
	 * 初始化缩放命令模式
	 * @param scaleSize
	 * @param command
	 */
	private static void _parseScaleSize(String scaleSize,ImageProcessCommand command) {
		if (scaleSize.equals(ImgServerConf.DIR_ORIG)) {
			command.origable = true;
		}else if (SCALE_EXTENT_PATTERN.matcher(scaleSize).matches()) {
			//Extent 模式
			scaleSize = scaleSize.replace("e", "");
			int n = scaleSize.indexOf("x");
			command.width = Integer.parseInt(scaleSize.substring(0,n));
			command.height = Integer.parseInt(scaleSize.substring(n+1));
			command.extent = true;
		}else if (SCALE_WIDHT_PATTERN.matcher(scaleSize).matches()) {
			//Width 模式
			command.extent = false;
			command.width =  Integer.parseInt(scaleSize.replace("x", ""));
		}else if (SCALE_HEIGHT_PATTERN.matcher(scaleSize).matches()) {
			command.extent = false;
			command.height = Integer.parseInt(scaleSize.replace("x", ""));
		}else if (SCALE_WIDTH_HEIGHT_PATTERN.matcher(scaleSize).matches()) {
			int n = scaleSize.indexOf("x");
			command.width = Integer.parseInt(scaleSize.substring(0,n));
			command.height = Integer.parseInt(scaleSize.substring(n+1));
			command.extent = false;
		}else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 获取宽度 
	 * @return
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * 获取高度
	 * @return
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * 是否扩展到画布上
	 * @return
	 */
	public boolean isExtent() {
		return extent;
	}

	/**
	 * 图片质量
	 * @return
	 */
	public int getQuality() {
		return quality;
	}

	/**
	 * 文件id
	 * @return
	 */
	public String getFileid() {
		return fileid;
	}

	/**
	 * 缩放尺寸
	 * @return
	 */
	public String getScaleSize() {
		return scaleSize;
	}

	/**
	 * 获取不包含文件名的文件路径
	 * 如 ： /var/aiyutian/images/100/1|0/250x300
	 * @return
	 */
	public String getPath() {
		return ImgServerConf.getConf().getOutputDir() + "/" + this.quality + "/" + (this.watermark ? 1:0) + "/" + this.scaleSize;
	}
	
	/**
	 * 获取包含文件名的文件路径
	 * 如 ： /var/aiyutian/images/100/250x300/0|1/1,123123123123.jpg
	 * @return
	 */
	public String getFullPath() {
		return this.getPath() + "/" + this.fileid;
	}
	
	/**
	 * 获取原始目录
	 * @return
	 */
	public String getOrigPath() {
		return ImgServerConf.getConf().getOutputDir() + "/" + ImgServerConf.DIR_ORIG;
	}
	
	/**
	 * 获取原始目录以及文件名称
	 * @return
	 */
	public String getOrigFullPath() {
		return this.getOrigPath() + "/" + this.fileid;
	}

	/**
	 * 水印
	 * @return
	 */
	public boolean isWatermark() {
		return watermark;
	}

	/**
	 * 是否为原始图片
	 * @return
	 */
	public boolean isOrigable() {
		return origable;
	}
}
