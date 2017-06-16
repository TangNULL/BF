//服务器UserService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public boolean login(String username, String password) throws RemoteException;

	public boolean logout(String username) throws RemoteException;
	
	public boolean register(String username,String password) throws RemoteException;
	
	public boolean loginAgain() throws RemoteException;
	
	public void setloginAgain(boolean wannaloginAgain) throws RemoteException;
	
	public void setClient(String username) throws RemoteException;
	
	public String getClient() throws RemoteException;
	
	public void setLanguage(String language) throws RemoteException;
	
	public String getLanguage() throws RemoteException;
 }
