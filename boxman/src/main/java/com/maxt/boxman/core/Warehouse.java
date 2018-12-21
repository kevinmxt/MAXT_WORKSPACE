/**
 * 
 */
package com.maxt.boxman.core;

/**
 * Description: 仓库类
 * @author maxt@yusys.com.cn
 * @since 2018年12月19日
 */
public class Warehouse {
	
	/**
	 * 墙, 不可移动空间
	 */
	public static final int WALL = 1;
	
	/**
	 * 地板, 可移动空间
	 */
	public static final int FLOOR = 0;
	
	/**
	 * 箱子, 可移动物体
	 */
	public static final int BOX = 2;
	
	/**
	 * 标记, 箱子需要移动到的指定位置
	 */
	public static final int MARK = 3;
	
	/**
	 * 箱子管理员
	 */
	public static final int BOXMAN = 4;
	
	/**
	 * 已经有箱子在上面的标记点
	 */
	public static final int MARKBOX = BOX + MARK;
	
	/**
	 * 仓库长
	 */
	public static int WIDTH = 20;
	
	/**
	 * 仓库宽
	 */
	public static int HEIGHT = 20;
	
	/**
	 * 仓库管理员位置的横坐标
	 */
	protected int x = WIDTH/2;
	
	/**
	 * 仓库管理员位置的纵坐标
	 */
	protected int y = HEIGHT/2;
	
	/**
	 * 仓库的空间
	 */
	protected int[][] space;
	
	public Warehouse(int x, int y, int[][] space) {
		this.x = x;
		this.y = y;
		this.space = space;
	}
	
	/**
	 * 检查是否胜利
	 * 胜利条件: 所有标记点均已被箱子占据
	 * @return
	 */
	public boolean isWin() {
		for (int[] is : space) {
			for (int i : is) {
				if (MARK == i) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Description: 方向, boxman的移动方向
	 * @author maxt@yusys.com.cn
	 * @since 2018年12月19日
	 */
	public enum Direction {
		UP,DOWN,LEFT,RIGHT;
	}
	
	/**
	 * 移动方法
	 * @param direction
	 * @return
	 */
	public boolean move(Direction direction) {
		if (space[y][x] != FLOOR && space[y][x] != MARK) {
			return false;
		}
		int x1,y1,x2,y2;
		switch (direction) {
		case UP:
			x1 = x;
			y1 = y - 1;
			x2 = x;
			y2 = y - 2;
			break;
		case DOWN:
			x1 = x;
			y1 = y + 1;
			x2 = x;
			y2 = y + 2;
			break;
		case LEFT:
			x1 = x - 1;
			y1 = y;
			x2 = x - 2;
			y2 = y;
			break;
		case RIGHT:
			x1 = x + 1;
			y1 = y;
			x2 = x + 2;
			y2 = y;
			break;
		default:
			return false;
		}
		if (y1 < 0) {
			return false;
		}
		if (space[y1][x1] == WALL) {
			return false;
		} else if (space[y1][x1] == BOX || space[y1][x1] == MARKBOX) {
			if (space[y2][x2] == FLOOR || space[y2][x2] == MARK) {
				// BOX移开, BOX变为FLOOR, MARKBOX变为MARK
				space[y1][x1] -= BOX;
				// BOX移入, FLOOR变为BOX, MARK变为MARKBOX
				space[y2][x2] += BOX;
			} else {
				return false;
			}
		}
		x = x1;
		y = y1;
		return true;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the space
	 */
	public int[][] getSpace() {
		return space;
	}

	/**
	 * @param space the space to set
	 */
	public void setSpace(int[][] space) {
		this.space = space;
	}

}
