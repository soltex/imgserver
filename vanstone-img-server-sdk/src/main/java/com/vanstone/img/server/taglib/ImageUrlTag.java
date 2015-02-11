/**
 * 
 */
package com.vanstone.img.server.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.img.server.common.Constants;
import com.vanstone.img.server.common.ImageURLBuilder;

/**
 * 图片文件标签
 * @author shipeng
 */
public class ImageUrlTag extends TagSupport {
	
	/***/
	private static final long serialVersionUID = -4909904623648968599L;
	
	private static Logger LOG = LoggerFactory.getLogger(ImageUrlTag.class);
	
	//autoCal 是否为自动计算
	
	/**目前缩放命令，目前只支持100x100, 100 , x100 , e100x100   四种模式 */
	private String scaleSize;
	/** 缩放质量*/
	private int quality = Constants.DEFAULT_QUALITY;
	/**是否增加水印*/
	private boolean watermark = false;
	/**文件ID*/
	private String fileId;
	/**文件扩展名*/
	private String extName;
	@Override
	public int doStartTag() throws JspException {
		if (this.scaleSize == null || "".equals(this.scaleSize)) {
			throw new IllegalArgumentException("Scale Size Empty.");
		}
		if (this.fileId == null || "".equals(this.fileId)) {
			throw new IllegalArgumentException("File Name Empty.");
		}
		return super.doStartTag();
	}
	
	@Override
	///img +  "/" + this.quality + "/" + (this.watermark ? 1:0) + "/" + this.scaleSize + "/" + fileid
	public int doEndTag() throws JspException {
		//开始处理生成缩放图片URL地址
		String address = ImageURLBuilder.createImageURL(scaleSize, quality, watermark, fileId, extName);
		if (address == null || "".equals(address)) {
			throw new IllegalArgumentException("Address Empty");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(address);
		if (!address.endsWith(Constants.URL_SEPARATOR)) {
			sb.append(Constants.URL_SEPARATOR);
		}
		//img
		sb.append(Constants.HTTPSERVER_REQUEST_URL_PREFIX).append(Constants.URL_SEPARATOR);
		//quality
		sb.append(this.quality).append(Constants.URL_SEPARATOR);
		//watermark
		sb.append(this.watermark ? 1 : 0).append(Constants.URL_SEPARATOR);
		//scaleSize
		sb.append(this.scaleSize).append(Constants.URL_SEPARATOR);
		//fileid
		sb.append(this.fileId);
		if (this.extName != null && !this.extName.equals("")) {
			sb.append(".").append(this.extName);
		}
		
		LOG.debug("Image URL : {}" , sb.toString());
		
		//write string to out
		try {
			this.pageContext.getOut().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		return super.doEndTag();
	}
	
	public String getScaleSize() {
		return scaleSize;
	}

	public void setScaleSize(String scaleSize) {
		this.scaleSize = scaleSize;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public boolean isWatermark() {
		return watermark;
	}

	public void setWatermark(boolean watermark) {
		this.watermark = watermark;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getExtName() {
		return extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

}
