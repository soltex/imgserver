/**
 * 
 */
package com.vanstone.img.server.common.strategy;

import org.junit.Test;

/**
 * @author shipeng
 *
 */
public class WeedFSFileIDServerStrategyTest {

	@Test
	public void testWeedFSFileID() {
		String fileId = "111,12312312312serwe.jpg";
		int n = fileId.indexOf(",");
		int vol = Integer.parseInt(fileId.substring(0, n));
		System.out.println(vol);
	}

}
