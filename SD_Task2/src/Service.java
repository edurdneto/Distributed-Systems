import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Service extends Remote {
    void makeRequest(int id) throws RemoteException;
    String operation(float i, float j, char o) throws RemoteException;
    int getStatus(int id) throws RemoteException;
    void freeLock(int id) throws RemoteException;
    void getFila() throws RemoteException;
}
