
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Service {

    public Server() {}

    public String sayHello() {
        return "Hello, world!";
    }

    public String operation(float i, float j, char o) {
        float res=0;
        if(o=='-'){
            res=i-j;
        }
        if(o=='+'){
            res=i+j;
        }
        if(o=='/'){
            res=i/j;
        }
        if(o=='*'){
            res=i*j;
        }
        return String.valueOf(res);
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            Service stub = (Service) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Service", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
