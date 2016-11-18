//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

//import com.healthmarketscience.rmiio.RemoteInputStream;



public interface ProxyService extends Remote {
	String storage(String a) throws RemoteException;
	String makeRequest(String id) throws RemoteException;
	//void sendFile(RemoteInputStream data,String path) throws IOException, RemoteException;
	//void writeToFile(InputStream stream,String path) throws IOException;
}
