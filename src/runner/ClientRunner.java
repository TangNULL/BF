package runner;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rmi.RemoteHelper;
import service.IOService;
import service.UserService;
import ui.LoginFrame;
import ui.MainFrame;
import ui.Register;

public class ClientRunner {
	private RemoteHelper remoteHelper;//用来和Server相接
	private MainFrame mainFrame;
	public boolean open;
	public ClientRunner() throws InterruptedException, RemoteException {
		linkToServer();
		login();
	}
	
	private void linkToServer() {
		try {
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://127.0.0.1:8887/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
/*	class myThread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(true){
				try {
					login();
					JOptionPane.showMessageDialog(null, "又登陆了");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "登录还是没反应");
			}
		}
		
	}*/
	class Mythread2 extends Thread{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true){
				if(RemoteHelper.getInstance().getUserService().loginAgain()){
					ClientRunner x=new ClientRunner();
					open=true;
					break;
				}
			}
			
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
	
		
	
	

	
	private void initGUI() throws InterruptedException, RemoteException {
		Mythread2 th2=new Mythread2();
		Thread t2=new Thread(th2);
		t2.start();
		mainFrame = new MainFrame();
	}
	
	private void login() throws InterruptedException, RemoteException{
		LoginFrame loginFrame = new LoginFrame();
		Thread.sleep(4000);// 等待确定是否注册。。。。
		if(loginFrame.wannaRegister==true&&remoteHelper.getUserService().getClient()==null){  //客户点击了注册按钮  希望注册
			register();
		}
		boolean AlreadyLogin=false;
		while(!AlreadyLogin){
			Thread.sleep(6000);// 等待输入登录。。。。
			String UserName=loginFrame.UserName;
			String PassWord=loginFrame.PassWord;
			try {
				boolean canLogin=remoteHelper.getUserService().login(UserName, PassWord);
				if(canLogin==true&&remoteHelper.getUserService().getClient()==null){
					remoteHelper.getUserService().setClient(UserName);
					RemoteHelper.getInstance().getUserService().setloginAgain(false);
					JOptionPane.showMessageDialog(null, "登录成功"); 
					String filepath2 = "E:\\学习\\大作业\\BFServer\\"+RemoteHelper.getInstance().getUserService().getClient();
					File pack=new File(filepath2);
					if(pack.exists()){
						String sets ="attrib -h -r -s "+filepath2;  
			            // 运行命令  
			            try {
							Runtime.getRuntime().exec(sets);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
					}
					//loginFrame.getFrame().setVisible(false);
					loginFrame.getFrame().dispose();
					System.gc();
					AlreadyLogin=true;
					initGUI();
					break;
				}
				else{
					JOptionPane.showMessageDialog(null, "用户名或密码错误或已登录", "提示 ", JOptionPane.ERROR_MESSAGE);
					loginFrame.getFrame().dispose();
					System.gc();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			loginFrame = new LoginFrame();
		}
	}
	
	
	private void register() throws InterruptedException{
		Register register=new Register();
		boolean AlreadyRegister=false;
		Thread.sleep(2500);
		if(register.waexit==true){
			AlreadyRegister=true;
		}
		while(!AlreadyRegister){
			Thread.sleep(10000);// 等待输入注册。。。。
			String registerName=register.registerName;
			String registerPass=register.registerPass;
			if(registerName.equals(null)||registerPass.equals(null)||(registerName!=null&&registerName.contains(" "))||(registerPass!=null&&registerPass.contains(" "))||(registerName!=null&&registerName.equals(""))||(registerPass!=null&&registerPass.equals(""))){
				JOptionPane.showMessageDialog(null, "一切不得为空且不得带有空格，请重新输入"); 
				register.getRegisterFrame().dispose();
			}
			else{
				try {
					boolean canRegister=remoteHelper.getUserService().register(registerName, registerPass);
					if(canRegister==true){
						JOptionPane.showMessageDialog(null, "注册成功"); 
						register.getRegisterFrame().dispose();
						AlreadyRegister=true;
						break;
					}
					else{
						JOptionPane.showMessageDialog(null, "用户名已存在 ", "提示 ", JOptionPane.ERROR_MESSAGE);
						register.getRegisterFrame().dispose();
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			register=new Register();
			
		}
		if(register.waexit==true){
			register.getRegisterFrame().dispose();
		}
	}
	public void test(){
		try {
			System.out.println(remoteHelper.getUserService().login("admin", "123456a"));
			System.out.println(remoteHelper.getIOService().writeFile("suck", "admin", "testFile"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws RemoteException, InterruptedException{
		ClientRunner cr=new ClientRunner();
		//cr.test();
	}
}