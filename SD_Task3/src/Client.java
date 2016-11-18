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
	
	public static boolean sendFile(String fileName){
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line=br.readLine())!=null) {
				sb.append(line);
			    sb.append(System.lineSeparator());    
			}
			String everything = sb.toString();
			    //System.out.println(everything);
			Registry registry = LocateRegistry.getRegistry(null);
			BalancerService stub = (BalancerService) registry.lookup("Balancer");
	            
			    //registry = LocateRegistry.getRegistry(host);
			    //stub = (BalancerService) registry.lookup("Balancer");
			String proxy_id=null;
			proxy_id=stub.getProxy();
			if(proxy_id!=null){
				System.out.println(proxy_id+" foi escolhido!");
				ProxyClassService stub2 = (ProxyClassService) registry.lookup(proxy_id);
			    stub2.writeFile(everything,"edu.txt");
			    	//System.out.println(stub2.hash("edu.txt"));
			}
	        //ProxyService stub2 = (ProxyService) registry.lookup("Proxy1");
	        //String fileName = "edu.txt";
	        //String path = "/Users/eduardo/SD_Task3/str1/"+fileName;
	        //SimpleRemoteInputStream istream = new SimpleRemoteInputStream(new FileInputStream(fileName));
            //stub2.sendFile(istream.export(),path);
	     } catch (Exception e) {
	    	 System.err.println("Client exception: " + e.toString());
	         e.printStackTrace();
	     }
		return true;
	}
	
	private Client() {}
		public static void main(String[] args) {
		
        String host = (args.length < 1) ? null : args[0];
        boolean loop = true;
        while(loop){
        	System.out.println("Operation:\n1)To Read\n2)To Write\n3)To Exit\n");
        	Scanner scanner = new Scanner(System.in);
        	int i= scanner.nextInt();
        	scanner.nextLine();
        	switch(i){
        		case 1:
        			break;
        		case 2:
        			System.out.println("File Name:");
        			String filename = scanner.nextLine();
        			sendFile(filename);
        			break;
        		case 3:
        			loop=false;
        			break;
        	}
        }
        //try {
	      //  Registry registry = LocateRegistry.getRegistry(host);
	       // BalancerService stub = (BalancerService) registry.lookup("Balancer");
	       // sendFile("edu.txt");    
	        //registry = LocateRegistry.getRegistry(host);
	        //stub = (BalancerService) registry.lookup("Balancer");
	       // String proxy_id=null;
	        //proxy_id=stub.getProxy();
	        //if(proxy_id!=null){
	        //	System.out.println(proxy_id+" foi escolhido!");
	        //	ProxyClassService stub2 = (ProxyClassService) registry.lookup(proxy_id);
	        //	stub2.writeFile("edu\ng\nd","edu.txt");
	        	//System.out.println(stub2.hash("edu.txt"));
	       // }
	        //ProxyService stub2 = (ProxyService) registry.lookup("Proxy1");
	        //String fileName = "edu.txt";
	        //String path = "/Users/eduardo/SD_Task3/str1/"+fileName;
	        //SimpleRemoteInputStream istream = new SimpleRemoteInputStream(new FileInputStream(fileName));
            //stub2.sendFile(istream.export(),path);
	            
	           
	           
	     //} catch (Exception e) {
	      //      System.err.println("Client exception: " + e.toString());
	       //     e.printStackTrace();
	     //}
        
    }

}