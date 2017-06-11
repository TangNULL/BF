package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rmi.RemoteHelper;
import runner.ClientRunner;


public class MainFrame extends JFrame {
	private JTextArea textArea;
	private JLabel resultLabel;
	
	public String code;
	public boolean wannaExecute=false,wannaNew=false,wannaOpen=false,wannaSave=false,wannaExit=false;
	public boolean wannaOpenFileList=false;
	public MainFrame() {
		// 鍒涘缓绐椾綋
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu searchMenu = new JMenu("Search");
		JMenu runMenu=new JMenu("Run");
		menuBar.add(fileMenu);
		menuBar.add(searchMenu);
		menuBar.add(runMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		JMenuItem fileListMenuItem = new JMenuItem("fileList");
		searchMenu.add(fileListMenuItem);
		JMenuItem executeMenuItem=new JMenuItem("Execute");
		runMenu.add(executeMenuItem);
		
		frame.setJMenuBar(menuBar);
		
		

		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		executeMenuItem.addActionListener(new MenuItemActionListener());

		textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.LIGHT_GRAY);
		frame.add(textArea, BorderLayout.CENTER);

		// 鏄剧ず缁撴灉
		resultLabel = new JLabel();
		resultLabel.setText("result:");
		frame.add(resultLabel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}
	public String setExit(String username){
		return username;
	}

	class MenuItemActionListener implements ActionListener {
		/**
		 * 瀛愯彍鍗曞搷搴斾簨浠�
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				
			} 
			else if (cmd.equals("Execute")) {
				wannaExecute=true;
			} 
			else if(cmd.equals("New")){
				wannaNew=true;
			}
			else if(cmd.equals("Exit")){
				//wannaExit=true;
				try {
					String Client=RemoteHelper.getInstance().getUserService().getClient();
					RemoteHelper.getInstance().getUserService().logout(Client);
					JOptionPane.showMessageDialog(null, "成功退出"); 
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else if(cmd.equals("fileList")){
				wannaOpenFileList=true;
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			code = textArea.getText();
		}

	}
}
