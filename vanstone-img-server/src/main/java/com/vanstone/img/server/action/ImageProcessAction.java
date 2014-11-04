/**
 * 
 */
package com.vanstone.img.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.fs.ContentType;
import com.vanstone.gm.GMHandlerFactory;
import com.vanstone.gm.IGMImageHandler;
import com.vanstone.img.server.HttpServletResponseUtil;
import com.vanstone.img.server.cmd.ImageProcessCommand;
import com.vanstone.weedfs.client.impl.WeedFSClient;

/**
 * 图片处理Servlet
 * @author shipeng
 */
public class ImageProcessAction extends HttpServlet {
	
	private static final long serialVersionUID = -8812634884321074618L;
	
	private static Logger LOG = LoggerFactory.getLogger(ImageProcessAction.class);
	
	private IGMImageHandler handler = GMHandlerFactory.getGMImageHandler();
	
	@Override
	protected void doPost(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		ImageProcessCommand command = ImageProcessCommand.parseCommand(servletRequest);
		if (LOG.isInfoEnabled()) {
			LOG.info(command.getFullPath());
		}
		if (!new File(command.getPath()).exists()) {
			new File(command.getPath()).mkdirs();
		}
		//下载文件到orig中
		WeedFSClient fsClient = new WeedFSClient();
		if (!new File(command.getOrigFullPath()).exists()) {
			//原始文件不存在
			new File(command.getOrigPath()).mkdirs();
			boolean isok = fsClient.downloadToFile(command.getFileid(), new File(command.getOrigFullPath()));
			if (!isok) {
				servletResponse.sendError(404);
				return;
			}
		}
		ContentType contentType = ContentType.parseContentType(FilenameUtils.getExtension(command.getOrigFullPath()));
		if (command.isOrigable()) {
			HttpServletResponseUtil.writeStream(new FileInputStream(command.getOrigFullPath()), servletResponse, contentType);
			return;
		}
		
		String path = command.getPath();
		if (!new File(path).exists()) {
			new File(path).mkdirs();
		}
		new File(command.getPath()).mkdirs();
		if (command.isExtent()) {
			//生成扩展面板
			handler.scaleImageExtent(new File(command.getOrigFullPath()), new File(command.getFullPath()), command.getQuality(), command.getWidth()	, command.getHeight());
		}else {
			//生成图片
			 handler.scaleImage(new File(command.getOrigFullPath()), new File(command.getFullPath()), command.getQuality(), command.getWidth(), command.getHeight());
		}
		HttpServletResponseUtil.writeStream(new FileInputStream(command.getFullPath()), servletResponse, contentType);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
