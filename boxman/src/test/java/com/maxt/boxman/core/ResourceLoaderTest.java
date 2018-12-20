/**
 * 
 */
package com.maxt.boxman.core;

import java.io.IOException;

import junit.framework.TestCase;

/**
 * Description: 
 * @author maxt@yusys.com.cn
 * @since 2018年12月20日
 */
public class ResourceLoaderTest extends TestCase {

	public void testGetFileContent() throws IOException {
		String s = ResourceLoader.getFileContent("level/boxman_level_1.json");
		System.out.println("String result : " + s);
	}
	
	public void testGetFileByteData() throws IOException {
		byte[] s = ResourceLoader.getFileByteData("picture/wall.png");
		System.out.println("byte result : " + s);
	}
}
