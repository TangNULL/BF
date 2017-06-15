package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JFileChooser;
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
	private JTextArea paramtextArea;
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
		JMenuItem versionListMenuItem = new JMenuItem("versionList");
		searchMenu.add(versionListMenuItem);
		JMenuItem executeMenuItem=new JMenuItem("Execute");
		runMenu.add(executeMenuItem);
		
		frame.setJMenuBar(menuBar);
		
		

		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		executeMenuItem.addActionListener(new MenuItemActionListener());

		textArea = new JTextArea(25,20);
		textArea.setText("code here");
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.LIGHT_GRAY);
		paramtextArea = new JTextArea(25,20);
		paramtextArea.setText("param here");
		paramtextArea.setMargin(new Insets(10, 10, 10, 10));
		frame.add(textArea, BorderLayout.WEST);
		frame.add(paramtextArea, BorderLayout.EAST);

		// 鏄剧ず缁撴灉
		resultLabel = new JLabel();
		resultLabel.setText("sssssss");
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
				String result="";
				JFileChooser choose = new JFileChooser("E:\\");
				File file=choose.getSelectedFile();
				String filepath=file.getAbsolutePath();
				try {
					FileReader fileReader=new FileReader(filepath);
					BufferedReader buffer=new BufferedReader(fileReader);
					String Line=null;
					while((Line=buffer.readLine())!=null){
						result=result+Line;
					}
					textArea.setText(result);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
			else if (cmd.equals("Execute")) {
				String result="";
				String code=textArea.getText();
				String param=paramtextArea.getText();
				try {
					result=RemoteHelper.getInstance().getExecuteService().execute(code, param);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				resultLabel.setText(result);
				
				
			} 
			else if(cmd.equals("New")){
				String name=JOptionPane.showInputDialog("请输入要保存的文件名");
				String client="quq";
				try {
					client=RemoteHelper.getInstance().getUserService().getClient();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "无用户 ", "提示 ", JOptionPane.ERROR_MESSAGE);
				}
				//先创建文件夹
				File pack=new File("E:\\学习\\大作业\\BFServer\\"+client);
				if(!pack.exists())
					pack.mkdir();
				//创建文件
				File file=new File("E:\\学习\\大作业\\BFServer\\"+client+"\\"+client+"_"+name+".text");
				try {
					file.createNewFile();
					JOptionPane.showMessageDialog(null, "成功创建文件 ", "提示 ", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "文件已存在或文件夹不存在 ", "提示 ", JOptionPane.ERROR_MESSAGE);
				}
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
			else if(cmd.equals("versionList")){
				
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
