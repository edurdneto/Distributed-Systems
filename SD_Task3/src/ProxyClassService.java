import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ProxyClassService extends Remote {
	String writeFile(String content, String fileName) throws RemoteException, FileNotFoundException, IOException;
	String readFile(String content) throws RemoteException;
	String makeRequest(String id) throws RemoteException;
		//void sendFile(RemoteInputStream data,String path) throws IOException, RemoteException;
		//void writeToFile(InputStream stream,String path) throws IOException;
}
