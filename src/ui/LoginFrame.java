package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.rmi.RemoteException;
import rmi.RemoteHelper;
import runner.ClientRunner;



public class LoginFrame {
	public String UserName;
	public String PassWord;
	public boolean wannaRegister=false;
	public JFrame logframe;
	public LoginFrame() {
		logframe = new JFrame("Log in");
		logframe.setLayout(new BorderLayout());
		JPanel LogPanel=new JPanel();
		JLabel name=new JLabel("User:");
		JTextField Name=new JTextField();
		JLabel pass=new JLabel("PassWord:");
		JTextField Pass=new JTextField();
		
		name.setBounds(50, 50, 150, 30);
		Name.setBounds(170,50, 150, 30);
		pass.setBounds(50,110, 150, 30);
		Pass.setBounds(170,110, 150, 30);
		LogPanel.add(name);
		LogPanel.add(Name);
		LogPanel.add(pass);
		LogPanel.add(Pass);
		
		JButton But1=new JButton("Check");
		JButton But2=new JButton("Cancel");
		JButton But3=new JButton("Register");
		
		But1.setBounds(20,180,85,25);
		But2.setBounds(120,180,85,25);
		But3.setBounds(220,180,85,25);
		LogPanel.add(But1);
		LogPanel.add(But2);
		LogPanel.add(But3);

		
		class LogAgainthread implements Runnable{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//open=false;
				try {
					while(true){
						if(RemoteHelper.getInstance().getUserService().loginAgain()){
							ClientRunner x=new ClientRunner();
							break;
						}
					}
				} catch (RemoteException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}

		
		
		
		But1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean AlreadyLogin=false;
				while(!AlreadyLogin){
					UserName=Name.getText();
					PassWord=Pass.getText();
					try {
						boolean canLogin=RemoteHelper.getInstance().getUserService().login(UserName, PassWord);
						if(canLogin==true&&RemoteHelper.getInstance().getUserService().getClient()==null){
							RemoteHelper.getInstance().getUserService().setClient(UserName);
							RemoteHelper.getInstance().getUserService().setloginAgain(false);
							JOptionPane.showMessageDialog(null, "��¼�ɹ�"); 
							String filepath2 = "E:\\ѧϰ\\����ҵ\\BFServer\\"+RemoteHelper.getInstance().getUserService().getClient();
							File pack=new File(filepath2);//Ϊ���û��������Ӧ�ļ���
							if(pack.exists()){
								String sets ="attrib -h -r -s "+filepath2; //��ʾ֮ǰ���صĶ�Ӧ�ļ��� 
					            // ��������  
					            try {
									Runtime.getRuntime().exec(sets);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}  
							}
							logframe.dispose();
							System.gc();
							AlreadyLogin=true;
							MainFrame mainframe=new MainFrame();//���������
						
							Thread thread=new Thread(new LogAgainthread());
							thread.start();//ʹ����mainframe�����login����ʵ���ٴε�¼
							
							break;
						}
						else{
							JOptionPane.showMessageDialog(null, "�û��������������ѵ�¼", "��ʾ ", JOptionPane.ERROR_MESSAGE);
							logframe.dispose();
							System.gc();
							LoginFrame loginFrame = new LoginFrame();
							break;
						}
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});
		But2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		But3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				wannaRegister=true;
				try {
					if(wannaRegister==true&&RemoteHelper.getInstance().getUserService().getClient()==null){  //�ͻ������ע�ᰴť  ϣ��ע��
						Register register=new Register();
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		
		LogPanel.setLayout(null);
		
		logframe.getContentPane().add(BorderLayout.CENTER, LogPanel);
		
		logframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logframe.setSize(350, 300);
		logframe.setLocation(450, 300);
		logframe.setVisible(true);
		
		
		
	}
	/*public JFrame getFrame(){
		return logframe;
	}*/
	
	
}
