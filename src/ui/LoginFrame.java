package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.MainFrame.MenuItemActionListener;

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
		JTextField Name=new JTextField(8);
		JLabel pass=new JLabel("PassWord:");
		JTextField Pass=new JTextField(8);
		
		name.setBounds(50, 50, 100, 30);
		Name.setBounds(170,50, 100, 30);
		pass.setBounds(50,110, 100, 30);
		Pass.setBounds(170,110, 100, 30);
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
		
		But1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			/*	logframe.setVisible(false);*/
				UserName=Name.getText();
				PassWord=Pass.getText();
				//logframe.setVisible(false);
			}
		});
		But2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
				/*Name.setText("");
				Pass.setText("");*/
			}
		});
		But3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				wannaRegister=true;
			}
			
		});
		
		
		LogPanel.setLayout(null);
		
		logframe.getContentPane().add(BorderLayout.CENTER, LogPanel);
		
		logframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logframe.setSize(350, 300);
		logframe.setLocation(450, 300);
		logframe.setVisible(true);
		
		
		
	}
	public JFrame getFrame(){
		return logframe;
	}
	/*
	class MouseActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Check")){
				e.getSource().setVisible(false);
			}
	        if(e.getActionCommand().equals("Cancel")){
	        	
	        }
		}
	}
	*/
	
	
}
