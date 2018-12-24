/**
 * 
 */
package com.maxt.boxman.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import com.maxt.boxman.core.LevelManager;
import com.maxt.boxman.core.Warehouse;

/**
 * Description: 
 * @author maxt@yusys.com.cn
 * @since 2018年12月21日
 */
public class EditToolbar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	Warehouse warehouse;
	
	private JButton wallBt;
	private JButton floorBt;
	private JButton boxBt;
	private JButton markBt;
	private JButton boxmanBt;
	private JLabel desc;
	private JLabel currentSelected;
	private JButton saveBt;

	public EditToolbar(Warehouse warehouse) {
		this.warehouse = warehouse;
		wallBt = new JButton(ResourceItemHolder.WALL_IMG);
		floorBt = new JButton(ResourceItemHolder.FLOOR_IMG);
		boxBt = new JButton(ResourceItemHolder.BOX_IMG);
		markBt = new JButton(ResourceItemHolder.MARK_IMG);
		boxmanBt = new JButton(ResourceItemHolder.BOXMAN_IMG);
		add(wallBt);
		add(floorBt);
		add(boxBt);
		add(markBt);
		add(boxmanBt);
		addSeparator();
		desc = new JLabel(" 当前选择: ");
		currentSelected = new JLabel(ResourceItemHolder.WALL_IMG);
		currentSelected.setName(Warehouse.WALL + "");
		add(desc);
		add(currentSelected);
		addSeparator();
		saveBt = new JButton("保存");
		add(saveBt);
		addListeners();
	}

	/**
	 * 
	 */
	private void addListeners() {
		wallBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentSelected.setIcon(ResourceItemHolder.WALL_IMG);
			}
		});
		floorBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentSelected.setIcon(ResourceItemHolder.FLOOR_IMG);
			}
		});
		boxBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentSelected.setIcon(ResourceItemHolder.BOX_IMG);
			}
		});
		markBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentSelected.setIcon(ResourceItemHolder.MARK_IMG);
			}
		});
		boxmanBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentSelected.setIcon(ResourceItemHolder.BOXMAN_IMG);
			}
		});
		saveBt.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				LevelManager.generateLevel(warehouse);
			}
		});
	}
	
	/**
	 * 获取当前选中的素材类型
	 * @return
	 */
	public int getCurrentType() {
		int type = -1;
		if (ResourceItemHolder.WALL_IMG.equals(currentSelected.getIcon())) {
			type  = Warehouse.WALL; 
		} else if (ResourceItemHolder.FLOOR_IMG.equals(currentSelected.getIcon())) {
			type  = Warehouse.FLOOR; 
		} else if (ResourceItemHolder.BOX_IMG.equals(currentSelected.getIcon())) {
			type  = Warehouse.BOX; 
		} else if (ResourceItemHolder.MARK_IMG.equals(currentSelected.getIcon())) {
			type  = Warehouse.MARK; 
		} else if (ResourceItemHolder.BOXMAN_IMG.equals(currentSelected.getIcon())) {
			type  = Warehouse.BOXMAN; 
		}
		return type;
	}

	/**
	 * @param warehouse the warehouse to set
	 */
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
}
