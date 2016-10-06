import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements Service {
		
	    public ArrayList<Request> fila = new ArrayList<>();
	
	 	public Server() {}
	 	
	    public void makeRequest(int id) {
	    	Request r= new Request(id,0);
	    	if(fila.size()>0){
	    		r.setStatus(1);
	    	}
	    	fila.add(r);
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
	    
	    public int getStatus(int id) {
	    	int size=fila.size();
	    	while(size!=0){
	    		if(fila.get(size-1).getId()==id){
	    			return fila.get(size-1).getStatus();
	    		}
	    		size--;
	    	}
	    	return -1;
	    }
	    
	    public void freeLock(int id){
	    	int size=fila.size();
	    	fila.remove(0);
	    	if(fila.size()!=0)fila.get(0).setStatus(0);
	    }
	    
	    public void getFila(){
	    	int i=0;
	    	if(fila.size()!=0){
	    		System.out.println("Fila:");
	    		while(i<fila.size()-1){
	    			System.out.print(fila.get(i).getId()+",");
	    			i++;
	    		}
	    		if(i==fila.size()-1)System.out.println(fila.get(i).getId()+",");
	    	}
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