import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Client {
	
	
	private Client() {}
		public static void main(String[] args) {
		String uniqueID = UUID.randomUUID().toString();
		
        String host = (args.length < 1) ? null : args[0];
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Digite o primeiro numero:");
        //float i = scanner.nextFloat();
        //System.out.println("Digite o segundo numero:");
        //float j = scanner.nextFloat();
        //System.out.println("Digite a operacao:('*','/','+','-'):");
        //char o = scanner.next().charAt(0);
        Boolean keep = true;
        while (keep==true){
	        try {
	            Registry registry = LocateRegistry.getRegistry(host);
	            Service stub = (Service) registry.lookup("Service");
	            //String response = stub.sayHello(id);
	            //System.out.println("response: " + response);
	            //stub.makeRequest(id);
	            boolean loop = true;
	            while (loop) {
	            	
	            	int j=1;
	            	int res=-1;
	            	System.out.println("Choose the action:");
	            	System.out.println("1.MakeRequest");
	            	System.out.println("2.getStatus");
	            	System.out.println("3.freeLock");
	            	System.out.println("4.Exit");
	            	j=scanner.nextInt();
	            	switch (j){
	            		case 1:
	            			registry = LocateRegistry.getRegistry(host);
	        	            stub = (Service) registry.lookup("Service");
	            			res=stub.getStatus(uniqueID);
	            			if(res==-1){
	            				System.out.println(stub.makeRequest(uniqueID));
	            			} else {
	            				System.out.println("You already has a request");
	            					
	            			}
	            			break;
	            		case 2:
	            			registry = LocateRegistry.getRegistry(host);
	        	            stub = (Service) registry.lookup("Service");
	            			res=stub.getStatus(uniqueID);
	            			if(res==-1){
	            				System.out.println("The request doesnt exist");
	            			} else {
	            				if(res==0){
	            					System.out.println("The request has the lock");
	            				} else {
	            					System.out.println("The request is waiting");
	            				}
	            			}
	            			break;
	            		case 3:
	            			registry = LocateRegistry.getRegistry(host);
	        	            stub = (Service) registry.lookup("Service");
	            			res=stub.getStatus(uniqueID);
	            			if(res==0){
	            				stub.freeLock();
	            				System.out.println("The lock has been released!");
	            			} else {
	            				System.out.println("You dont have the lock");
	            			}
	            			
	            			break;
	            		case 4:
	            			loop=false;
	            			keep=false;
	            			break;
	            	}
	            }
	            //int res=-1;
	            
	            //REQUISITAR STATUS DE REQUISICAO
	            //res=stub.getStatus(id);
	            //System.out.println(res);
	            
	            //LIBERAR LOCK
	            //stub.freeLock(2);
	            //REQUISITAR FILA
	            //stub.getFila();
	           
	        } catch (Exception e) {
	            System.err.println("Client exception: " + e.toString());
	            e.printStackTrace();
	        }
        }
    }

}

