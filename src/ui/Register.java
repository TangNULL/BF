package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register {
	public String registerName;
	public String registerPass;
	public JFrame registerFrame;
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
		
		But1.setBounds(40,180,100,20);
		But2.setBounds(100,180,100,20);
		registerPanel.add(But1);
		registerPanel.add(But2);
		
		But1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				registerName=Name.getText();
				registerPass=Pass.getText();
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
