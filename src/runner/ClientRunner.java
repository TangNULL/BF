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
	
	public static void main(String[] args) throws RemoteException, InterruptedException{
		ClientRunner cr=new ClientRunner();
		
		//cr.test();
	}
}