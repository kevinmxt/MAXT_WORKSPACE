/**
 * 
 */
package com.maxt.boxman.gui;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.maxt.boxman.core.Warehouse;

/**
 * Description: 
 * @author maxt@yusys.com.cn
 * @since 2018年12月21日
 */
public class WarehousePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	Warehouse warehouse;
	
	public WarehousePanel(Warehouse warehouse2) {
		setLayout(new GridLayout(Warehouse.HEIGHT, Warehouse.WIDTH));
		warehouse = warehouse2;
		addListeners();
	}

	/**
	 * 
	 */
	private void addListeners() {
		// 鼠标监听事件
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
			}
			
			public void mouseEntered(MouseEvent e) {
			}
			
			public void mouseClicked(MouseEvent e) {
//				System.out.println("mouse clicked");
				try {
					BoxmanFrame frame = (BoxmanFrame) e.getComponent().getParent().getParent().getParent().getParent();
					if (frame.isEditMode() && frame.getCurrentType() != -1) {
						JLabel l = (JLabel) e.getComponent().getComponentAt(e.getPoint());
						String[] position = l.getName().split(",");
						//素材类型
						int type = frame.getCurrentType();
						int x = Integer.parseInt(position[0]);
						int y = Integer.parseInt(position[1]);
						if (type == Warehouse.BOXMAN) {
							warehouse.setX(x);
							warehouse.setY(y);
						} else if (type == Warehouse.BOX && ResourceItemHolder.MARK_IMG.equals(l.getIcon())) {
							warehouse.getSpace()[y][x] = Warehouse.MARKBOX;
						} else if (type == Warehouse.MARK && ResourceItemHolder.BOX_IMG.equals(l.getIcon())) {
							warehouse.getSpace()[y][x] = Warehouse.MARKBOX;
						} else {
							warehouse.getSpace()[y][x] = type;
						}
						renderGraphic();
						frame.setVisible(true);
//						System.out.println(l.getIcon());
//						System.out.println(frame.getCurrentType());
					}
				} catch (Exception e2) {
					System.out.println("invalid position");
				}
			}
		});
	}
	
	/**
	 * 渲染画面
	 * @param warehouse
	 */
	public void renderGraphic() {
		removeAll();
		for (int i = 0; i < warehouse.getSpace().length; i++) {
			int[] row = warehouse.getSpace()[i];
			for (int j = 0; j < row.length; j++) {
				JLabel l = new JLabel();
				l.setName(j + "," + i);
				if (i == warehouse.getY() && j == warehouse.getX()) {
					l.setText("☺");
					l.setIcon(ResourceItemHolder.BOXMAN_IMG);
				} else {
					switch (row[j]) {
					case Warehouse.BOX:
						l.setText("□");
						l.setIcon(ResourceItemHolder.BOX_IMG);
						break;
					case Warehouse.MARK:
						l.setText("☆");
						l.setIcon(ResourceItemHolder.MARK_IMG);
						break;
					case Warehouse.MARKBOX:
						l.setText("■");
						l.setIcon(ResourceItemHolder.MARKBOX_IMG);
						break;
					case Warehouse.FLOOR:
						l.setText("░");
						l.setIcon(ResourceItemHolder.FLOOR_IMG);
						break;
					case Warehouse.WALL:
						l.setText("█");
						l.setIcon(ResourceItemHolder.WALL_IMG);
						break;
					default:
						break;
					}
				}
				add(l);
			}
		}
		setVisible(true);
	}
}
