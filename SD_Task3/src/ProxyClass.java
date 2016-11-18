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
	
	@Override
	public String readFile(String content) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String makeRequest(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return id;
	}
	
	public ArrayList<Integer> getMap(int id) throws FileNotFoundException, IOException{
		ArrayList<Integer> mapList = new ArrayList<Integer>();
		try(BufferedReader br = new BufferedReader(new FileReader("map.txt"))) {
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while((line=br.readLine())!=null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        StringTokenizer st = new StringTokenizer(line);
		        if(Integer.parseInt(st.nextToken())==id){
		        	System.out.println(line);
		        	int count=st.countTokens();
		        	for(int i=1;i<=count;i++){
		        		mapList.add(Integer.parseInt(st.nextToken()));
		        	}
		        }
		    }
		    String everything = sb.toString();
		}
		return mapList;
	}
	
	@Override
	public String writeFile(String content, String fileName) throws FileNotFoundException, IOException {
		ArrayList<Integer>map = new ArrayList<Integer>();
		int id = hash(fileName);
		id=2;
		map=getMap(id);
		System.out.println("map:"+map.get(0));
		System.out.println("map:"+map.get(1));
		
		String ret=null;
		for(int i=0;i<map.size();i++){
			String identificator = "Storage"+map.get(i);
			try {
				String filename = "str"+map.get(i)+"/"+fileName;
				Registry registry = LocateRegistry.getRegistry(null);
				StorageService stub = (StorageService) registry.lookup(identificator);
				ret = stub.writeFile(content,filename);
				System.out.println(ret+identificator);
			} catch (Exception e) {
				System.out.println("Client exception: " + e.toString());
				e.printStackTrace();
				ret = null;
			}
			if(ret!=null);
		}
		
		return ret;
		// TODO Auto-generated method stub
	}
	
	
	public int hash(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(StandardCharsets.UTF_8.encode(filename));
			String result = String.format("%032x", new BigInteger(1, md5.digest()));
			System.out.println(result);
			return 1;
			
		} catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
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
            //ProxyClass a = new ProxyClass();
            //a.hash("edu.txt");
            //maplist=a.getMap(1);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
