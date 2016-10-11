import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Service extends Remote {
    String makeRequest(String id) throws RemoteException;
    String operation(float i, float j, char o) throws RemoteException;
    int getStatus(String id) throws RemoteException;
    void freeLock() throws RemoteException;
    String getFila() throws RemoteException;
}
