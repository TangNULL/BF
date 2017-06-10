package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import rmi.RemoteHelper;
import service.IOService;
import service.UserService;
import ui.LoginFrame;
import ui.MainFrame;

public class ClientRunner {
	private RemoteHelper remoteHelper;//用来和Server相接？？？？
	
	public ClientRunner() {
		linkToServer();
		initGUI();
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
	
	private void initGUI() {
		MainFrame mainFrame = new MainFrame();
	}
	private void login(){
		LoginFrame loginFrame = new LoginFrame();
		String UserName=loginFrame.UserName;
		String PassWord=loginFrame.PassWord;
		try {
			boolean canLogin=remoteHelper.getUserService().login(UserName, PassWord);
			if(canLogin==true){
				JOptionPane.showMessageDialog(null, "登录成功"); 
			}
			else{
				JOptionPane.showMessageDialog(null, "用户名或密码错误 ", "提示 ", JOptionPane.ERROR_MESSAGE);
				loginFrame = new LoginFrame();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public static void main(String[] args){
		ClientRunner cr = new ClientRunner();
		//cr.test();
	}
}
