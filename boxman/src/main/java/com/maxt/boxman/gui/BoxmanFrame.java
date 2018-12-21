/**
 * 
 */
package com.maxt.boxman.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.maxt.boxman.core.LevelManager;
import com.maxt.boxman.core.Warehouse;
import com.maxt.boxman.core.Warehouse.Direction;

/**
 * Description: Boxman主窗口
 * @author maxt@yusys.com.cn
 * @since 2018年12月19日
 */
public class BoxmanFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	int currentLvl = 1;

	/** game panel */
	private WarehousePanel panel;
	
	private Warehouse warehouse;
	
	private EditToolbar toolbar;
	
	private boolean editMode = false;

	public BoxmanFrame(String title) {
        // 标题栏
        super(title);
        // 大小
        setSize(950, 900);
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
	 * 添加监听事件
	 */
	private void addListeners() {
		// 键盘监听
		addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
			}
			
			public void keyReleased(KeyEvent e) {
			}
			
			public void keyPressed(KeyEvent e) {
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
			refreshGraphic();
			isWin();
		}
	}
	
	/**
	 * 刷新画面
	 */
	private void refreshGraphic() {
		panel.renderGraphic();
		setVisible(true);
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
	 * 生成用户界面
	 */
	private void generateInterface() {
		generateMenu();
//		initResource();
		generateGamePanel();
	}

	/**
	 * 生成菜单栏
	 */
	private void generateMenu() {
		// 菜单栏
        JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("option");
		JMenuItem startMenu = new JMenuItem("new game");
		startMenu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("new game");
				currentLvl = 1;
				generateGamePanel();
			}
		});
		fileMenu.add(startMenu);
		JMenuItem resetMenu = new JMenuItem("reset game");
		resetMenu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("reset game");
				generateGamePanel();
			}
		});
		fileMenu.add(resetMenu);
		JMenuItem selectLvlMenu = new JMenuItem("select level");
		selectLvlMenu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("select level");
				String result = JOptionPane.showInputDialog(null, "please input level", "select level",
						JOptionPane.OK_CANCEL_OPTION);
				System.out.println(result);
				if (result != null) {
					try {
						currentLvl = Integer.parseInt(result);
						generateGamePanel();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Invalid level number", "Error", JOptionPane.YES_OPTION);
					}
				}
			}
		});
		fileMenu.add(selectLvlMenu);

		JMenu debugMenu = new JMenu("DEBUG MODE");
		JMenuItem editMenu = new JMenuItem("edit level");
		editMenu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("edit level");
				toolbar.setVisible(true);
				currentLvl = 0;
				editMode = true;
				generateGamePanel();
			}
		});
		debugMenu.add(editMenu);
		JMenuItem quiteditMenu = new JMenuItem("quit edit");
		quiteditMenu.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("quit edit");
				toolbar.setVisible(false);
//				currentLvl = 0;
				editMode = false;
//				generateGamePanel();
				refreshGraphic();
			}
		});
		debugMenu.add(quiteditMenu);
		
		mb.add(fileMenu);
		mb.add(debugMenu);
		setJMenuBar(mb);
		//添加工具栏, 默认不可见
		toolbar = new EditToolbar();
        setLayout(new BorderLayout());
		add(toolbar, BorderLayout.NORTH);
		toolbar.setVisible(false);
		setVisible(true);
	}
	
	/**
	 * 生成游戏面板
	 */
	private void generateGamePanel() {
		if (panel != null) {
			remove(panel);
			setVisible(true);
		}
		loadLevel();
	}
	
	/**
	 * 加载关卡数据
	 */
	private void loadLevel() {
		warehouse = LevelManager.loadLevel(currentLvl);
		panel = new WarehousePanel(warehouse);
		add(panel);
		refreshGraphic();
	}
	
	/**
	 * 返回当前选中的素材类型
	 * @return
	 */
	public int getCurrentType() {
		return toolbar.getCurrentType();
	}

	/**
	 * @return the editMode
	 */
	public boolean isEditMode() {
		return editMode;
	}
	
}
