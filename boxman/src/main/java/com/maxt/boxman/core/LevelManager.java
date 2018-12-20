/**
 * 
 */
package com.maxt.boxman.core;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Description: 
 * @author maxt@yusys.com.cn
 * @since 2018年12月19日
 */
public class LevelManager {
	
	/**
	 * 关卡文件名前缀
	 */
	public static final String LEVEL_FILE_NAME = "level/boxman_level_";

	/**
	 * 载入关卡
	 * @param level
	 * @return
	 */
	public static Warehouse loadLevel(int level) {
		int x = 0;
		int y = 0;
		int[][] space = null;
		String levelStr = loadLevelFile(level);
		//关卡数据
		JSONObject levelObj = JSONObject.fromObject(levelStr);
		//boxman坐标
		x = levelObj.getInt("x");
		y = levelObj.getInt("y");
		//空间数据
		JSONObject spaceObj = levelObj.getJSONObject("space");
		space = new int[Warehouse.WIDTH][Warehouse.HEIGHT];
		for (int[] is : space) {
			for (int i = 0; i < is.length; i++) {
				is[i] = Warehouse.WALL;
			}
		}
		
		//地板坐标
		JSONArray floorArr = spaceObj.getJSONArray("floor");
		for (Object obj : floorArr) {
			String[] axis = ((String)obj).split(",");
			space[Integer.valueOf(axis[1])][Integer.valueOf(axis[0])] = Warehouse.FLOOR;
		}
		//箱子坐标
		JSONArray boxArr = spaceObj.getJSONArray("box");
		for (Object obj : boxArr) {
			String[] axis = ((String)obj).split(",");
			space[Integer.valueOf(axis[1])][Integer.valueOf(axis[0])] = Warehouse.BOX;
		}
		//标记坐标
		JSONArray markArr = spaceObj.getJSONArray("mark");
		for (Object obj : markArr) {
			String[] axis = ((String)obj).split(",");
			space[Integer.valueOf(axis[1])][Integer.valueOf(axis[0])] = Warehouse.MARK;
		}
		try {
			//箱子标记坐标
			JSONArray markboxArr = spaceObj.getJSONArray("markbox");
			for (Object obj : markboxArr) {
				String[] axis = ((String)obj).split(",");
				space[Integer.valueOf(axis[1])][Integer.valueOf(axis[0])] = Warehouse.MARKBOX;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		Warehouse warehouse = new Warehouse(x, y, space);
		return warehouse;
	}
	
	/**
	 * 输出字符图形
	 * @param warehouse
	 */
	public static void renderStringChart(Warehouse warehouse) {
		for (int i = 0; i < warehouse.getSpace().length; i++) {
			int[] row = warehouse.getSpace()[i];
			for (int j = 0; j < row.length; j++) {
				if (i == warehouse.getY() && j == warehouse.getX()) {
					System.out.print("* ");
				} else {
					System.out.print(row[j] + " ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * 读取关卡文件
	 * @param level
	 * @return
	 */
	private static String loadLevelFile(int level) {
		String fileName = LEVEL_FILE_NAME + level + ".json";
        String str = null;
		try {
			str = ResourceLoader.getFileContent(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
