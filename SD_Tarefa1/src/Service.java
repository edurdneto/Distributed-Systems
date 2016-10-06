
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Service extends Remote {
    String sayHello() throws RemoteException;
    String operation(float i, float j, char o) throws RemoteException;
}
