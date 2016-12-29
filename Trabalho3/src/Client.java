import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.UUID;
//import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

public class Client {

	public boolean sendData(String filename, byte[] data, int len) throws RemoteException{
		return true;
	}

	public static boolean readFile(String fileName){
		try {
			Registry registry = LocateRegistry.getRegistry(null);
			BalancerService stub = (BalancerService) registry.lookup("Balancer");
			String proxy_id=null;
			proxy_id=stub.getProxy();
			if(proxy_id!=null){
				System.out.println(proxy_id+" was chosen.");
				ProxyClassService stub2 = (ProxyClassService) registry.lookup(proxy_id);
			    System.out.println(stub2.readFile(fileName));
			} else {
				System.out.println("Proxy Down!");
			}
	     } catch (Exception e) {
	    	System.out.println("File not found.");
	     }
		return true;
	}

	public static boolean createFile(String fileName, String content){
		try {
			Registry registry = LocateRegistry.getRegistry(null);
			BalancerService stub = (BalancerService) registry.lookup("Balancer");
	    	String proxy_id=null;
			proxy_id=stub.getProxy();
			if(proxy_id!=null){
				System.out.println(proxy_id+" was chosen.");
				ProxyClassService stub2 = (ProxyClassService) registry.lookup(proxy_id);
			    System.out.println(stub2.createFile(content, fileName));
			} else {
				System.out.println("Proxy Down!");
			}
	     } catch (Exception e) {
	    	 System.out.println("Unable to create file.");
	     }
		return true;
	}

	private Client() {}
		public static void main(String[] args) {

	        String host = (args.length < 1) ? null : args[0];
	        boolean loop = true;
	        while(loop){
	        	System.out.println("Operation:\n1) To Create\n2) To Read\n3) To Exit\n");
	        	Scanner scanner = new Scanner(System.in);
	        	int i= scanner.nextInt();
	        	scanner.nextLine();
	        	switch(i){
	        		case 1:
	        			System.out.print("File name: ");
	        			String filename = scanner.nextLine();
	        			System.out.print("File content: ");
	        			String content = scanner.nextLine();
	        			createFile(filename, content);
	        			break;
	        		case 2:
	        			System.out.print("File name: ");
	        			String filename2 = scanner.nextLine();
	        			readFile(filename2);
	        			break;
	        		case 3:
	        			loop=false;
	        			break;
	        	}
	        }
        }
}
