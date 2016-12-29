import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface ProxyClassService extends Remote {
	String createFile(String content, String fileName) throws RemoteException, FileNotFoundException, IOException;
	String readFile(String fileName) throws RemoteException, FileNotFoundException, IOException;
	String makeRequest(String id) throws RemoteException;
}