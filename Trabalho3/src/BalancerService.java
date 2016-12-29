import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

//import com.healthmarketscience.rmiio.RemoteInputStream;


public interface BalancerService extends Remote {
	String getProxy() throws RemoteException;
}
