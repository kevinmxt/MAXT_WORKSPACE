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
	
	/**
	 * 根据warehouse生成level的json数据
	 * @param warehouse
	 * @return
	 */
	public static String generateLevel(Warehouse warehouse) {
		//关卡数据
		JSONObject levelObj = new JSONObject();
		levelObj.accumulate("x", warehouse.getX());
		levelObj.accumulate("y", warehouse.getY());
		int[][] space = warehouse.getSpace();
		JSONArray floorArr = new JSONArray();
		JSONArray boxArr = new JSONArray();
		JSONArray markArr = new JSONArray();
		JSONArray markboxArr = new JSONArray();
		for (int i = 0; i < warehouse.getSpace().length; i++) {
			int[] row = warehouse.getSpace()[i];
			for (int j = 0; j < row.length; j++) {
				if (space[i][j] == Warehouse.FLOOR) {
					floorArr.add(j + "," + i);
				} else if (space[i][j] == Warehouse.BOX) {
					boxArr.add(j + "," + i);
				} else if (space[i][j] == Warehouse.MARK) {
					markArr.add(j + "," + i);
				} else if (space[i][j] == Warehouse.MARKBOX) {
					markboxArr.add(j + "," + i);
				}
			}
		}
		JSONObject spaceObj = new JSONObject();
		spaceObj.accumulate("floor", floorArr);
		spaceObj.accumulate("box", boxArr);
		spaceObj.accumulate("mark", markArr);
		if (markboxArr.size() > 0) {
			spaceObj.accumulate("markbox", markboxArr);
		}
		levelObj.accumulate("space", spaceObj);
		System.out.println(levelObj);
		return levelObj.toString();
	}
}
