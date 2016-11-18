
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Storage implements StorageService {
	@Override
	public String writeFile(String content, String filename) throws IOException {
		// TODO Auto-generated method stub
		BufferedWriter output = null;
        try {
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file));
            output.write(content);
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
          if ( output != null ) {
            output.close();
          }
        }
		return "write";
	}

	@Override
	public String readFile(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return "read";
	}  
	
	
	public static void main(String args[]) {
    	System.out.println("Storage id:(int[0,n])");
    	Scanner scanner = new Scanner(System.in);
    	int i= scanner.nextInt();
    	String id = "Storage"+Integer.toString(i);
    	try {
            Storage obj = new Storage();
            StorageService stub = (StorageService) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            //registry.bind("Service", stub);
            registry.rebind(id, (Remote) stub);
            System.err.println("Storage ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
