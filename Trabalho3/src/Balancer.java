import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Balancer implements BalancerService {
	int number_of_proxy=1;
	
	public int getNumber_of_proxy() {
		return number_of_proxy;
	}

	public void setNumber_of_proxy(int number_of_proxy) {
		this.number_of_proxy = number_of_proxy;
	}

	@Override
	public String getProxy() throws RemoteException {
		String ret=null;
		for(int i=0;i<getNumber_of_proxy();i++){
			String id = "Proxy"+Integer.toString(i);
			try {
				Registry registry = LocateRegistry.getRegistry(null);
				ProxyClassService stub = (ProxyClassService) registry.lookup(id);
				ret = stub.makeRequest(id);
				break;
			} catch (Exception e) {
				System.out.println("Client exception: " + e.toString());
				e.printStackTrace();
				ret = null;
			}
			if(ret!=null);
		}	
		return ret;
	}
	
	public static void main(String args[]) {
    	
		try {
            Balancer obj = new Balancer();
            BalancerService stub = (BalancerService) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            //registry.bind("Service", stub);
            registry.rebind("Balancer", stub);
            
        	System.out.print("Number of proxys: ");
        	Scanner scanner = new Scanner(System.in);
        	int i= scanner.nextInt();
        	obj.setNumber_of_proxy(i);
        	
            System.err.println("Balancer ready.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}