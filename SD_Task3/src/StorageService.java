import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface StorageService extends Remote {
	String writeFile(String a, String filename) throws RemoteException, IOException;
	String readFile(String filename) throws RemoteException;
}
