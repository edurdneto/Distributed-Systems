import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class ProxyClass implements ProxyClassService {

	private ProxyClass() {}

	public String readFile(String fileName) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<Integer>map = new ArrayList<Integer>();
		int id = hash(fileName);
		map=getMap(id);

		String ret=null;
		for(int i=0;i<map.size();i++){
			String identificator = "Storage"+map.get(i);
			try {
				String filename = "/Users/eduardo/Downloads/Trabalho3/str"+map.get(i)+"/"+fileName;
				Registry registry = LocateRegistry.getRegistry(null);
				StorageService stub = (StorageService) registry.lookup(identificator);
				ret = stub.readFile(filename);
			} catch (Exception e) {
				System.out.println("Client exception: " + e.toString());
				e.printStackTrace();
			}
			if(ret!=null) return ret;
		}
		return "File doesn't exist.";
	}

	@Override
	public String makeRequest(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return id;
	}

	public ArrayList<Integer> getMap(int id) throws FileNotFoundException, IOException{
		ArrayList<Integer> mapList = new ArrayList<Integer>();
		try(BufferedReader br = new BufferedReader(new FileReader("map.txt"))) {
		    String line = null;
		    while((line=br.readLine())!=null) {
		        StringTokenizer st = new StringTokenizer(line);
		        if(Integer.parseInt(st.nextToken())==id){
		        	System.out.println(line);
		        	int count=st.countTokens();
		        	for(int i=1;i<=count;i++){
		        		mapList.add(Integer.parseInt(st.nextToken()));
		        	}
		        }
		    }
		 }
		return mapList;
	}

	@Override
	public String createFile(String content, String fileName) throws FileNotFoundException, IOException {
		ArrayList<Integer>map = new ArrayList<Integer>();
		int id = hash(fileName);
		map=getMap(id);
		//System.out.println("map: "+map.get(0));
		//System.out.println("map: "+map.get(1));

		String ret="";
		for(int i=0;i<map.size();i++){
			String identificator = "Storage"+map.get(i);
			try {
				String filename = "/Users/eduardo/Downloads/Trabalho3/str"+map.get(i)+"/"+fileName;
				Registry registry = LocateRegistry.getRegistry(null);
				StorageService stub = (StorageService) registry.lookup(identificator);
				ret = ret + stub.writeFile(content,filename) + map.get(i) + ".\n";
				//System.out.println(ret+identificator);
			} catch (Exception e) {
				System.out.println("Client exception: " + e.toString());
				e.printStackTrace();
				ret = ret + "\nFail sending to " + identificator;
			}
			if(ret!=null);
		}
		return ret;
	}

	public int hash(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		try {
           MessageDigest m=MessageDigest.getInstance("MD5");
           m.update(filename.getBytes(), 0, filename.length());
           BigInteger b = new BigInteger(1,m.digest());
           String id = b.mod(new BigInteger("3")).toString(16);
           return Integer.parseInt(id);
		} catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        System.out.println("Proxy exception: " + e.toString());
	    }
		return -1;
	}

	public static void main(String args[]) {
    	System.out.println("Proxy id:(int[0,n])");
    	Scanner scanner = new Scanner(System.in);
    	ArrayList<Integer> maplist = new ArrayList<Integer>();
    	int i= scanner.nextInt();
    	String id = "Proxy"+Integer.toString(i);
    	try {
            ProxyClass obj = new ProxyClass();
            ProxyClassService stub = (ProxyClassService) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            //registry.bind("Service", stub);
            registry.rebind(id, (Remote) stub);
            System.err.println("Proxy ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
