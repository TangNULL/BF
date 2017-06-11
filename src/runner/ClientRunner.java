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
import ui.Register;

public class ClientRunner {
	private RemoteHelper remoteHelper;//������Server��ӣ�������
	
	public ClientRunner() throws InterruptedException {
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
	private void login() throws InterruptedException{
		LoginFrame loginFrame = new LoginFrame();
		Thread.sleep(4000);// �ȴ�ȷ���Ƿ�ע�ᡣ������
		if(loginFrame.wannaRegister==true){  //�ͻ������ע�ᰴť  ϣ��ע��
			register();
		}
		boolean AlreadyLogin=false;
		while(!AlreadyLogin){
		//	loginFrame = new LoginFrame();
			Thread.sleep(10000);// �ȴ������¼��������
			String UserName=loginFrame.UserName;
			String PassWord=loginFrame.PassWord;
			try {
				boolean canLogin=remoteHelper.getUserService().login(UserName, PassWord);
				if(canLogin==true){
					JOptionPane.showMessageDialog(null, "��¼�ɹ�"); 
					loginFrame.getFrame().setVisible(false);
					AlreadyLogin=true;
					break;
				}
				else{
					JOptionPane.showMessageDialog(null, "�û������������ ", "��ʾ ", JOptionPane.ERROR_MESSAGE);
					loginFrame.getFrame().setVisible(false);
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
		while(!AlreadyRegister){
			//register=new Register();
			Thread.sleep(10000);// �ȴ�����ע�ᡣ������
			String registerName=register.registerName;
			String registerPass=register.registerPass;
			try {
				boolean canRegister=remoteHelper.getUserService().register(registerName, registerPass);
				if(canRegister==true){
					JOptionPane.showMessageDialog(null, "ע��ɹ�"); 
					register.getRegisterFrame().setVisible(false);
					AlreadyRegister=true;
					break;
				}
				else{
					JOptionPane.showMessageDialog(null, "�û����Ѵ��� ", "��ʾ ", JOptionPane.ERROR_MESSAGE);
					register.getRegisterFrame().setVisible(false);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			register=new Register();
			
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
	
	public static void main(String[] args) throws InterruptedException{
		ClientRunner cr = new ClientRunner();
		//cr.test();
	}
}
