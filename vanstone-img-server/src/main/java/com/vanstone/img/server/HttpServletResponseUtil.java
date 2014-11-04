/**
 * 
 */
package com.vanstone.img.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.vanstone.fs.ContentType;

/**
 * @author shipeng
 */
public class HttpServletResponseUtil {

	/**
	 * @param is
	 * @param servletResponse
	 * @param contentType
	 */
	public static void writeStream(InputStream is, HttpServletResponse servletResponse, ContentType contentType) {
		if (is == null || servletResponse == null || contentType == null) {
			throw new IllegalArgumentException();
		}
		servletResponse.setContentType(contentType.getMimeType());
		byte[] buffer = new byte[4096];
		int n = -1;
		OutputStream os;
		try {
			os = servletResponse.getOutputStream();
			while ((n = is.read(buffer, 0, buffer.length)) != -1) {
				os.write(buffer, 0, n);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
