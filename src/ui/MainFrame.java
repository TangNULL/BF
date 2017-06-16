package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JButton;
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
	private JButton filenamefield;
	public String code;
	public static String currentFilepath="";
	public boolean out;
	public MainFrame() {
		// 鍒涘缓绐椾綋
		out=false;
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(new BorderLayout());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu searchMenu = new JMenu("Search");
		JMenu versionMenu = new JMenu("Version");
		JMenu runMenu=new JMenu("Run");
		JMenu userMenu=new JMenu("User");
		menuBar.add(fileMenu);
		menuBar.add(searchMenu);
		menuBar.add(runMenu);
		menuBar.add(versionMenu);
		menuBar.add(userMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		userMenu.add(exitMenuItem);
		JMenuItem loginMenuItem = new JMenuItem("Log in");
		userMenu.add(loginMenuItem);
		JMenuItem fileListMenuItem = new JMenuItem("fileList");
		searchMenu.add(fileListMenuItem);
		JMenuItem executeBFMenuItem=new JMenuItem("Execute as BF");
		runMenu.add(executeBFMenuItem);
		JMenuItem executeOokMenuItem=new JMenuItem("Execute as Ook!");
		runMenu.add(executeOokMenuItem);
		
		frame.setJMenuBar(menuBar);
		
		filenamefield=new JButton("当前文档");
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		fileListMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		executeBFMenuItem.addActionListener(new MenuItemActionListener());
		executeOokMenuItem.addActionListener(new MenuItemActionListener());
		loginMenuItem.addActionListener(new MenuItemActionListener());

		textArea = new JTextArea(25,20);
		textArea.setText("code here");
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.LIGHT_GRAY);
		paramtextArea = new JTextArea(25,20);
		paramtextArea.setText("param here");
		paramtextArea.setMargin(new Insets(10, 10, 10, 10));
		frame.add(textArea, BorderLayout.WEST);
		frame.add(paramtextArea, BorderLayout.EAST);
		frame.add(filenamefield,BorderLayout.NORTH);
		
		// 鏄剧ず缁撴灉
		resultLabel = new JLabel();
		resultLabel.setText("Result here");
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
				String filepath="";
				if(currentFilepath!=""){
					JOptionPane.showMessageDialog(null, "还有文件没保存呢亲...","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					try {
						if(RemoteHelper.getInstance().getUserService().getClient()!=null){
							try {
								filepath = "E:\\学习\\大作业\\BFServer\\"+RemoteHelper.getInstance().getUserService().getClient()+"\\";
								String filename=JOptionPane.showInputDialog("你要打开的文件名是什么呀？（文件类型别忘了加）");
								if(filename==null){
									return;
								}
								else if(filename==""){
									filename=JOptionPane.showInputDialog(null,"en?你要打开的文件名是什么？","提示信息",JOptionPane.ERROR_MESSAGE);
								}
								String result=RemoteHelper.getInstance().getIOService().readFile(filepath, filename);
								textArea.setText(result);
								filenamefield.setText(filename);
								filepath+=filename;
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						else{
							JOptionPane.showMessageDialog(null, "无用户 ", "提示 ", JOptionPane.ERROR_MESSAGE);
						}
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					currentFilepath=filepath;
				}
			
				
				/*String result="";
				JFileChooser choose = new JFileChooser("E:\\");
				//fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
				choose.showOpenDialog(null);  
				File file = choose.getSelectedFile();  
				String filepath=file.getAbsolutePath();
				try {
					FileReader fileReader=new FileReader(file);
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
				*/
				
				
			} 
			else if (cmd.equals("Execute as BF")) {
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
			else if(cmd.equals("Execute as Ook!")){
				String result="";
				String code=textArea.getText();
				String param=paramtextArea.getText();
				try {
					result=RemoteHelper.getInstance().getExecuteService().executeOok(code, param);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				resultLabel.setText(result);		
			}
			
			else if(cmd.equals("New")){
				String client="quq";
				if(currentFilepath!=""){
					JOptionPane.showMessageDialog(null, "还有文件没保存呢亲...","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					try {
						client=RemoteHelper.getInstance().getUserService().getClient();
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "无用户 ", "提示 ", JOptionPane.ERROR_MESSAGE);
					}
					if(client!=null){
						String name=JOptionPane.showInputDialog("请给你的文件取一个名字吧");
						if(name==null){
							return;
						}
						else if(name==""){
							name=JOptionPane.showInputDialog(null,"请给你的文件取一个名字吧","提示信息",JOptionPane.ERROR_MESSAGE);
							return;
						}
						String[] options = {"bf","ook"};
					    int number= JOptionPane.showOptionDialog(MainFrame.this, "Which language do you choose?", "提示", 0, JOptionPane.QUESTION_MESSAGE, null, options, "BF");
						String language=options[number];
						
						//先创建文件夹
						File pack=new File("E:\\学习\\大作业\\BFServer\\"+client);
						if(!pack.exists())
							pack.mkdir();
						//创建文件
						String filepath="E:\\学习\\大作业\\BFServer\\"+client+"\\"+name+"."+language;
						File file=new File(filepath);
						try {
							file.createNewFile();
							JOptionPane.showMessageDialog(null, "成功创建文件 ", "提示 ", JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "文件已存在或文件夹不存在 ", "提示 ", JOptionPane.ERROR_MESSAGE);
						}
						currentFilepath=filepath;
					}
					else{
						JOptionPane.showMessageDialog(null, "无用户 ", "提示 ", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else if(cmd.equals("Exit")){
				if(currentFilepath!=""){
					JOptionPane.showMessageDialog(null, "还有文件没保存呢亲...","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					try {
						String Client=RemoteHelper.getInstance().getUserService().getClient();
						if(Client!=null){
							RemoteHelper.getInstance().getUserService().logout(Client);
							JOptionPane.showMessageDialog(null, "成功退出"); 
						}
						else{
							JOptionPane.showMessageDialog(null, "已经退出过啦"); 
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			else if(cmd.equals("Log in")){
				try {
					RemoteHelper.getInstance().getUserService().setloginAgain(true);
					out=true;
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(cmd.equals("fileList")){
				String client="";
				try {
					client=RemoteHelper.getInstance().getUserService().getClient();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "无用户 ", "提示 ", JOptionPane.ERROR_MESSAGE);
				}
				if(client!=null){
					File file=new File("E:\\学习\\大作业\\BFServer\\"+client);
					String[] FileName=file.list();
					String newline = System.getProperty("line.separator");
					String myStr="";
					for(String str:FileName){
						myStr+=str+newline;
					}
					JOptionPane.showMessageDialog(null,myStr, "FileList", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "无用户 ", "提示 ", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			code = textArea.getText();
			try {
				if(RemoteHelper.getInstance().getUserService().getClient()!=null){
					try {
						RemoteHelper.getInstance().getIOService().writeFile(code, currentFilepath,"");
						JOptionPane.showMessageDialog(null, "保存成功！");
						currentFilepath="";
						filenamefield.setText("空");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "无用户 ", "提示 ", JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

	}
}
