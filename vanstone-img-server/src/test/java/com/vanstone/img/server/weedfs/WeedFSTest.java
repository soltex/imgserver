/**
 * 
 */
package com.vanstone.img.server.weedfs;

import java.io.File;

import org.junit.Test;

import com.vanstone.weedfs.client.RequestResult;
import com.vanstone.weedfs.client.impl.WeedFSClient;

/**
 * @author shipeng
 */
public class WeedFSTest {
	
	@Test
	public void uploadFiles() {
		String dir = "/var/www/aiyutian/tmp";
		File[] files = new File(dir).listFiles();
		WeedFSClient weedFSClient = new WeedFSClient();
		for (File file : files) {
			RequestResult requestResult = weedFSClient.upload(file);
			System.out.println(requestResult.getFid());
		}
	}
	
}
