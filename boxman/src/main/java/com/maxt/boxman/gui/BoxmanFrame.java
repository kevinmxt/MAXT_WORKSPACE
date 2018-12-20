/**
 * 
 */
package com.maxt.boxman.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.maxt.boxman.core.LevelManager;
import com.maxt.boxman.core.ResourceLoader;
import com.maxt.boxman.core.Warehouse;
import com.maxt.boxman.core.Warehouse.Direction;

/**
 * Description: Boxman主窗口
 * @author maxt@yusys.com.cn
 * @since 2018年12月19日
 */
public class BoxmanFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int currentLvl = 1;

	JPanel panel;
	
	Warehouse warehouse;

	ImageIcon wallImg = null;
	ImageIcon floorImg = null;
	ImageIcon boxmanImg = null;
	ImageIcon markImg = null;
	ImageIcon boxImg = null;
	ImageIcon markboxImg = null;
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public BoxmanFrame(String title) {
        // 标题栏
        super(title);
        // 大小
        setSize(1000, 900);
        // 居中
        setLocationRelativeTo(null);
        // 默认关闭操作
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // 禁止修改大小  
        setResizable(false);
        // 初始化文本框与按钮
        generateInterface();
        addListeners();
        // 显示  
        setVisible(true);
	}

	/**
	 * 
	 */
	private void addListeners() {
		// TODO Auto-generated method stub
		addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == 37) { //left
					boxmanMove(Direction.LEFT);
				} else if (e.getKeyCode() == 38) { //up
					boxmanMove(Direction.UP);
				} else if (e.getKeyCode() == 39) { //right
					boxmanMove(Direction.RIGHT);
				} else if (e.getKeyCode() == 40) { //down
					boxmanMove(Direction.DOWN);
				}
			}

		});
	}

	/**
	 * boxman移动的方法
	 * @param left
	 */
	private void boxmanMove(Direction direction) {
		if (warehouse.move(direction)) {
			renderGraphic();
			isWin();
		}
	}
	
	/**
	 * 
	 */
	private void isWin() {
		if (warehouse.isWin()) {
			JOptionPane.showMessageDialog(panel, "You Win! Go to next level! ", "Congratulation", JOptionPane.DEFAULT_OPTION);
			currentLvl++;
			generateGamePanel();
		}
	}

	/**
	 * 生成界面
	 */
	private void generateInterface() {
		generateMenu();
		initResource();
		generateGamePanel();
	}
	
	/**
	 * 初始化资源文件
	 */
	private void initResource() {
		try {
			wallImg = new ImageIcon(ResourceLoader.getFileByteData("picture/wall.png"));
			floorImg = new ImageIcon(ResourceLoader.getFileByteData("picture/floor.png"));
			boxmanImg = new ImageIcon(ResourceLoader.getFileByteData("picture/boxman.png"));
			markImg = new ImageIcon(ResourceLoader.getFileByteData("picture/mark.png"));
			boxImg = new ImageIcon(ResourceLoader.getFileByteData("picture/box.png"));
			markboxImg = new ImageIcon(ResourceLoader.getFileByteData("picture/markbox.png"));
		} catch (IOException e) {
			System.out.println("loading picture error");
			e.printStackTrace();
		}
	}

	/**
	 * 生成菜单栏
	 */
	private void generateMenu() {
		// 菜单栏
        JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("file");
		JMenuItem startMenu = new JMenuItem("new game");
		startMenu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("new game");
				generateGamePanel();
			}
		});
		fileMenu.add(startMenu);
		mb.add(fileMenu);
		setJMenuBar(mb);
	}
	
	/**
	 * 生成游戏面板
	 */
	private void generateGamePanel() {
		if (panel != null) {
			remove(panel);
			setVisible(true);
		}
		panel = new JPanel();
//			panel.setBackground(Color.BLACK);
		panel.setLayout(new GridLayout(Warehouse.HEIGHT, Warehouse.WIDTH));
		add(panel);
		loadLevel();
		setVisible(true);
	}
	
	/**
	 * 加载关卡数据
	 */
	private void loadLevel() {
		warehouse = LevelManager.loadLevel(currentLvl);
		renderGraphic();
	}
	
	/**
	 * 渲染画面
	 * @param warehouse
	 */
	private void renderGraphic() {
		panel.removeAll();
		for (int i = 0; i < warehouse.getSpace().length; i++) {
			int[] row = warehouse.getSpace()[i];
			for (int j = 0; j < row.length; j++) {
				JLabel l = new JLabel();
				if (i == warehouse.getY() && j == warehouse.getX()) {
//					System.out.print("* ");
					l.setText("☺");
					l.setIcon(boxmanImg);
				} else {
//					System.out.print(row[j] + " ");
					switch (row[j]) {
					case Warehouse.BOX:
						l.setText("□");
						l.setIcon(boxImg);
						break;
					case Warehouse.MARK:
						l.setText("☆");
						l.setIcon(markImg);
						break;
					case Warehouse.MARKBOX:
						l.setText("■");
						l.setIcon(markboxImg);
						break;
					case Warehouse.FLOOR:
						l.setText("░");
						l.setIcon(floorImg);
						break;
					case Warehouse.WALL:
						l.setText("█");
						l.setIcon(wallImg);
						break;
					default:
						break;
					}
				}
				panel.add(l);
			}
//			System.out.println();
		}
		setVisible(true);
	}

}
