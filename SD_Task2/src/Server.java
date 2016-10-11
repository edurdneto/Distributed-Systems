import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Server implements Service {
		
	    public static ArrayList<Request> fila = new ArrayList<>();
	    
	    
	 	@Override
		public String toString() {
			return "Server [fila=" + fila + "]";
		}

		public Server() {}
	 	
	    public String makeRequest(String id) {
	    	Request r= new Request(id,0);
	    	if(fila.size()>0){
	    		r.setStatus(1);
	    		fila.add(r);
	    		System.out.println(fila.toString());
	    		writeToFile(fila.size()-1);
	    		return "The request has been added into the queue";
	    	} else {
	    		fila.add(r);
	    		System.out.println(fila.toString());
	    		writeToFile(0);
	    		return "The request has been made and you have the lock";
	    	}
	    	
	    }
	    
	    public void writeToFile(int index){
	    	try(FileWriter fw = new FileWriter("outfilename", true);
	    		    BufferedWriter bw = new BufferedWriter(fw);
	    		    PrintWriter out = new PrintWriter(bw))
	    		{
	    		    out.println(fila.get(index).getId());
	    		    bw.close();
	    		    out.close();
	    		} catch (IOException e) {
	    		    //exception handling left as an exercise for the reader
	    		}
	    }
	    
	    public void removeFromFile(){
	    	try {

	            File inFile = new File("outfilename");

	            if (!inFile.isFile()) {
	                System.out.println("Parameter is not an existing file");
	                return;
	            }

	            // Construct the new file that will later be renamed to the original
	            // filename.
	            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

	            BufferedReader br = new BufferedReader(new FileReader("outfilename"));
	            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

	            String line = null;

	            // Read from the original file and write to the new
	            // unless content matches data to be removed.
	            line = br.readLine();
	            while ((line = br.readLine()) != null){
	                    pw.println(line);
	                    pw.flush();
	            }
	            pw.close();
	            br.close();

	            // Delete the original file
	            if (!inFile.delete()) {
	                System.out.println("Could not delete file");
	                return;
	            }

	            // Rename the new file to the filename the original file had.
	            if (!tempFile.renameTo(inFile))
	                System.out.println("Could not rename file");

	        } catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }

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
	    
	    public int getStatus(String id) {
	    	int size=fila.size();
	    	while(size!=0){
	    		if(fila.get(size-1).getId().equals(id)){
	    			return fila.get(size-1).getStatus();
	    		}
	    		size--;
	    	}
	    	return -1;
	    }
	    
	    public void freeLock(){
	    	int size=fila.size();
	    	fila.remove(0);
	    	if(fila.size()!=0)fila.get(0).setStatus(0);
	    	removeFromFile();
	    	System.out.println(fila.toString());
	    }
	    
	    public String getFila(){
	    	return fila.toString();
	    }
	    
	    public static void recovery(){
	    	try (BufferedReader br = new BufferedReader(new FileReader("outfilename")))
			{

				String sCurrentLine;

				while ((sCurrentLine = br.readLine()) != null) {
					Request r = new Request(sCurrentLine,1);
					fila.add(r);
				}
				if(fila.size()!=0)fila.get(0).setStatus(0);

			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    public void timeout(){
	    	Timer timer = new Timer();

	    	timer.schedule( new TimerTask() {
	    	    public void run() {
	    	       if(fila.size()>0){
	    	    	   freeLock();
	    	    	   System.out.println("TimeOut!Lock released!");
	    	       }
	    	    }
	    	 }, 0, 60*1000);
	    }

	    public static void main(String args[]) {
	    	try {
	            Server obj = new Server();
	            Service stub = (Service) UnicastRemoteObject.exportObject(obj, 0);

	            // Bind the remote object's stub in the registry
	            Registry registry = LocateRegistry.getRegistry();
	            //registry.bind("Service", stub);
	            registry.rebind("Service", stub);
	            recovery();
	            obj.timeout();
	            System.err.println("Server ready");
	        } catch (Exception e) {
	            System.err.println("Server exception: " + e.toString());
	            e.printStackTrace();
	        }
	    }

}