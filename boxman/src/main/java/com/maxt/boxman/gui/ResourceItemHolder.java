/**
 * 
 */
package com.maxt.boxman.gui;

import java.io.IOException;

import javax.swing.ImageIcon;

import com.maxt.boxman.core.ResourceLoader;

/**
 * Description: 资源持有器
 * @author maxt@yusys.com.cn
 * @since 2018年12月21日
 */
public class ResourceItemHolder {
	
	public static ImageIcon WALL_IMG = null;
	public static ImageIcon FLOOR_IMG = null;
	public static ImageIcon BOXMAN_IMG = null;
	public static ImageIcon MARK_IMG = null;
	public static ImageIcon BOX_IMG = null;
	public static ImageIcon MARKBOX_IMG = null;
	
	/**
	 * 初始化资源文件
	 */
	static {
		try {
			System.out.println("loading picture resources");
			WALL_IMG = new ImageIcon(ResourceLoader.getFileByteData("picture/wall.png"));
			FLOOR_IMG = new ImageIcon(ResourceLoader.getFileByteData("picture/floor.png"));
			BOXMAN_IMG = new ImageIcon(ResourceLoader.getFileByteData("picture/boxman.png"));
			MARK_IMG = new ImageIcon(ResourceLoader.getFileByteData("picture/mark.png"));
			BOX_IMG = new ImageIcon(ResourceLoader.getFileByteData("picture/box.png"));
			MARKBOX_IMG = new ImageIcon(ResourceLoader.getFileByteData("picture/markbox.png"));
		} catch (IOException e) {
			System.out.println("loading picture error");
			e.printStackTrace();
		}
	}

}
