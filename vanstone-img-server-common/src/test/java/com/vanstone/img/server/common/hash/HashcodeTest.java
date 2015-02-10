/**
 * 
 */
package com.vanstone.img.server.common.hash;

import org.junit.Test;

import com.vanstone.common.component.hash.MurmurHash3;

/**
 * @author shipeng
 *
 */
public class HashcodeTest {
	
	@Test
	public void testGetHashCode() {
		String fileId1 = "4,a13d178ed6";
		String fileId = "7,a28c5c6eae";
		int hash = MurmurHash3.MurmurHash3_x64_32(fileId.getBytes(), 1000);
		int hash1 = MurmurHash3.MurmurHash3_x64_32(fileId1.getBytes(), 1000);
		System.out.println(hash1);
		System.out.println(hash);
		int lenght =3;
		int index = Math.abs(hash%lenght);
		System.out.println(index);
		int index1 = Math.abs(hash1%lenght);
		System.out.println(index1);
	}
}
