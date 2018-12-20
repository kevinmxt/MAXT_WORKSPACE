/**
 * 
 */
package com.maxt.boxman.core;

import com.maxt.boxman.core.Warehouse.Direction;

import junit.framework.TestCase;

/**
 * Description: 
 * @author maxt@yusys.com.cn
 * @since 2018年12月20日
 */
public class LevelManagerTest extends TestCase {

	public void testLevel1() {
		Warehouse warehouse = LevelManager.loadLevel(1);
		LevelManager.renderStringChart(warehouse);
		assertFalse(warehouse.isWin());
		assertTrue(warehouse.move(Direction.DOWN));
		assertTrue(warehouse.move(Direction.DOWN));
		assertTrue(warehouse.move(Direction.DOWN));
		assertTrue(warehouse.move(Direction.RIGHT));
		assertTrue(warehouse.move(Direction.DOWN));
		assertTrue(warehouse.move(Direction.RIGHT));
		assertTrue(warehouse.move(Direction.UP));
		assertTrue(warehouse.move(Direction.UP));
		assertTrue(warehouse.move(Direction.RIGHT));
		assertTrue(warehouse.move(Direction.UP));
		assertTrue(warehouse.move(Direction.LEFT));
		System.out.println();
		LevelManager.renderStringChart(warehouse);
		assertTrue(warehouse.isWin());
	}
}
