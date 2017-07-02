package runner;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

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
		Thread thread=new Thread(new ChangeStyleThread());
		thread.start();
		linkToServer();
		initGUI();
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


	private void initGUI() throws InterruptedException, RemoteException{
		LoginFrame loginFrame = new LoginFrame();
	}
	
	

	
	public void test(){
		try {
			System.out.println(remoteHelper.getUserService().login("admin", "123456a"));
			System.out.println(remoteHelper.getIOService().writeFile("suck", "admin", "testFile"));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	class ChangeStyleThread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{  
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //设置整个窗口显示风格，这里使用系统风格  
                FontUIResource f = new FontUIResource("微软雅黑", 0, 13); // 字体，样式（正常，斜体，加粗），字号  
                Enumeration keys = UIManager.getDefaults().keys();  
                while (keys.hasMoreElements()){  
                    Object key = keys.nextElement();  
                    Object value = UIManager.get(key);  
                    if(value instanceof javax.swing.plaf.FontUIResource)  
                        UIManager.put(key, f);  
                }  
            } catch (Exception e){  
                e.printStackTrace();  
            }//使用Windows风格  
		}
		
	}
	
	
	
	
	public static void main(String[] args) throws RemoteException, InterruptedException{
		ClientRunner cr=new ClientRunner();
		//cr.test();
	}
}