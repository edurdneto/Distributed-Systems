//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
//import java.security.Provider.Service;
import java.util.Scanner;

//import com.healthmarketscience.rmiio.RemoteInputStream;
//import com.healthmarketscience.rmiio.RemoteInputStreamClient;


public class Proxy implements ProxyService{ 
	
	private Proxy() {}
	//String uniqueID = UUID.randomUUID().toString();
	
    //String host = (args.length < 1) ? null : args[0];
    //Scanner scanner = new Scanner(System.in);
    public String storage(String a){
    	String ret;
    	/*Boolean keep = true;
    	try {
    			Registry registry = LocateRegistry.getRegistry(null);
    			Service stub = (Service) registry.lookup("Service2");
    			stub = (Service) registry.lookup("Service2");
            	
         } catch (Exception e) {
        	 System.err.println("Client exception: " + e.toString());
             e.printStackTrace();
         }*/
    	return "oi2";
    }
    
    public static void main(String args[]) {
    	System.out.println("Proxy id:(int[0,n])");
    	Scanner scanner = new Scanner(System.in);
    	int i= scanner.nextInt();
    	String id = "Proxy"+Integer.toString(i);
    	try {
            Proxy obj = new Proxy();
            ProxyService stub = (ProxyService) UnicastRemoteObject.exportObject(obj, 0);

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
	
	public String makeRequest(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return id;
	}
	
	/*@Override
    public void sendFile(RemoteInputStream data,String path) throws IOException, RemoteException {
        InputStream input = null;
        try {
            input = RemoteInputStreamClient.wrap(data);
            System.out.println("entrei nesse!");
            writeToFile(input,path);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }*/
	
	/*public void writeToFile(InputStream stream,String path) throws IOException {
        FileOutputStream output = null;
        File yourFile = new File(path);
        yourFile.createNewFile(); // if file already exists will do nothing 
    	FileOutputStream oFile = new FileOutputStream(yourFile, false);
        try {
        	
        	
        	//File file = File.createTempFile("output", ".txt");
            oFile = new FileOutputStream(yourFile);
            int chunk = 4096;
            byte [] result = new byte[chunk];

            int readBytes = 0;
            do {
                readBytes = stream.read(result);
                if (readBytes >= 0)
                	oFile.write(result, 0, readBytes);
            } while(readBytes != -1);

            oFile.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(oFile != null)
            	System.out.println("nao vazio");
                oFile.close();
            
        }
	}*/
}
