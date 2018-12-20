/**
 * 
 */
package com.maxt.boxman.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

/**
 * Description: 
 * @author maxt@yusys.com.cn
 * @since 2018年12月20日
 */
public class ResourceLoader {

	public static String getFileContent(String fileName) throws IOException {
		//返回读取指定资源的输入流  
        InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        try {
        	BufferedReader br=new BufferedReader(new InputStreamReader(is));
        	StringBuilder sb = new StringBuilder();
        	String s="";  
        	while((s=br.readLine())!=null) {
//        		System.out.println(s);
        		sb.append(s);
        	}
        	return sb.toString();
		} finally {
			is.close();
		}
	}
	
	public static byte[] getFileByteData(String fileName) throws IOException {
		//返回读取指定资源的输入流  
		InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		try {
			byte[] result = IOUtils.toByteArray(is);
			return result;
		} finally {
			is.close();
		}
	}
}
