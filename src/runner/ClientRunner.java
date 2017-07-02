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
	private RemoteHelper remoteHelper;//������Server���
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
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //��������������ʾ�������ʹ��ϵͳ���  
                FontUIResource f = new FontUIResource("΢���ź�", 0, 13); // ���壬��ʽ��������б�壬�Ӵ֣����ֺ�  
                Enumeration keys = UIManager.getDefaults().keys();  
                while (keys.hasMoreElements()){  
                    Object key = keys.nextElement();  
                    Object value = UIManager.get(key);  
                    if(value instanceof javax.swing.plaf.FontUIResource)  
                        UIManager.put(key, f);  
                }  
            } catch (Exception e){  
                e.printStackTrace();  
            }//ʹ��Windows���  
		}
		
	}
	
	
	
	
	public static void main(String[] args) throws RemoteException, InterruptedException{
		ClientRunner cr=new ClientRunner();
		//cr.test();
	}
}