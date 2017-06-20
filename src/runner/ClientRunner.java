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
	private RemoteHelper remoteHelper;//������Server���
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
					JOptionPane.showMessageDialog(null, "�ֵ�½��");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "��¼����û��Ӧ");
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
		Thread.sleep(4000);// �ȴ�ȷ���Ƿ�ע�ᡣ������
		if(loginFrame.wannaRegister==true&&remoteHelper.getUserService().getClient()==null){  //�ͻ������ע�ᰴť  ϣ��ע��
			register();
		}
		boolean AlreadyLogin=false;
		while(!AlreadyLogin){
			Thread.sleep(6000);// �ȴ������¼��������
			String UserName=loginFrame.UserName;
			String PassWord=loginFrame.PassWord;
			try {
				boolean canLogin=remoteHelper.getUserService().login(UserName, PassWord);
				if(canLogin==true&&remoteHelper.getUserService().getClient()==null){
					remoteHelper.getUserService().setClient(UserName);
					RemoteHelper.getInstance().getUserService().setloginAgain(false);
					JOptionPane.showMessageDialog(null, "��¼�ɹ�"); 
					String filepath2 = "E:\\ѧϰ\\����ҵ\\BFServer\\"+RemoteHelper.getInstance().getUserService().getClient();
					File pack=new File(filepath2);
					if(pack.exists()){
						String sets ="attrib -h -r -s "+filepath2;  
			            // ��������  
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
					JOptionPane.showMessageDialog(null, "�û��������������ѵ�¼", "��ʾ ", JOptionPane.ERROR_MESSAGE);
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
			Thread.sleep(10000);// �ȴ�����ע�ᡣ������
			String registerName=register.registerName;
			String registerPass=register.registerPass;
			if(registerName.equals(null)||registerPass.equals(null)||(registerName!=null&&registerName.contains(" "))||(registerPass!=null&&registerPass.contains(" "))||(registerName!=null&&registerName.equals(""))||(registerPass!=null&&registerPass.equals(""))){
				JOptionPane.showMessageDialog(null, "һ�в���Ϊ���Ҳ��ô��пո�����������"); 
				register.getRegisterFrame().dispose();
			}
			else{
				try {
					boolean canRegister=remoteHelper.getUserService().register(registerName, registerPass);
					if(canRegister==true){
						JOptionPane.showMessageDialog(null, "ע��ɹ�"); 
						register.getRegisterFrame().dispose();
						AlreadyRegister=true;
						break;
					}
					else{
						JOptionPane.showMessageDialog(null, "�û����Ѵ��� ", "��ʾ ", JOptionPane.ERROR_MESSAGE);
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