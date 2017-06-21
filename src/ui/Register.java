package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class Register {
	public String registerName;
	public String registerPass;
	public JFrame registerFrame;
	public static boolean waexit;
	public Register(){
		registerFrame=new JFrame("Register");
		registerFrame.setLayout(new BorderLayout());
		JPanel registerPanel=new JPanel();
		JLabel name=new JLabel("User:");
		JTextField Name=new JTextField(8);
		JLabel pass=new JLabel("PassWord:");
		JTextField Pass=new JTextField(8);
		
		name.setBounds(50, 50, 100, 30);
		Name.setBounds(170,50, 100, 30);
		pass.setBounds(50,110, 100, 30);
		Pass.setBounds(170,110, 100, 30);
		registerPanel.add(name);
		registerPanel.add(Name);
		registerPanel.add(pass);
		registerPanel.add(Pass);
		
		JButton But1=new JButton("OK");
		JButton But2=new JButton("Cancel");
		
		But1.setBounds(40,180,80,25);
		But2.setBounds(180,180,80,25);
		registerPanel.add(But1);
		registerPanel.add(But2);
		
		But1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {//点击注册
				// TODO Auto-generated method stub
				boolean AlreadyRegister=false;
				while(!AlreadyRegister){
					registerName=Name.getText();
					registerPass=Pass.getText();
					if(registerName.equals(null)||registerPass.equals(null)||(registerName!=null&&registerName.contains(" "))||(registerPass!=null&&registerPass.contains(" "))||(registerName!=null&&registerName.equals(""))||(registerPass!=null&&registerPass.equals(""))||registerName.contains(".")){
						JOptionPane.showMessageDialog(null, "一切不得为空且不得带有空格，用户名不得带有.等特殊字符，请重新输入"); 
						registerFrame.dispose();
						Register register=new Register();
						break;
					}
					else{
						try {
							boolean canRegister=RemoteHelper.getInstance().getUserService().register(registerName, registerPass);
							if(canRegister==true){
								JOptionPane.showMessageDialog(null, "注册成功"); 
								registerFrame.dispose();
								AlreadyRegister=true;
								break;
							}
							else{
								JOptionPane.showMessageDialog(null, "用户名已存在 ", "提示 ", JOptionPane.ERROR_MESSAGE);
								registerFrame.dispose();
								Register register=new Register();
								break;
							}
						} catch (RemoteException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					}
				}
				
				
			}
		});
		But2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				waexit=true;
				registerFrame.dispose();
			}
			
		});
		
		registerPanel.setLayout(null);
		
		registerFrame.getContentPane().add(BorderLayout.CENTER, registerPanel);
		
		registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		registerFrame.setSize(350, 300);
		registerFrame.setLocation(450, 300);
		registerFrame.setVisible(true);
		
	}
	public JFrame getRegisterFrame(){
		return registerFrame;
	}
	
}
