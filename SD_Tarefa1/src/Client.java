
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o primeiro numero:");
        float i = scanner.nextFloat();
        System.out.println("Digite o segundo numero:");
        float j = scanner.nextFloat();
        System.out.println("Digite a operacao:('*','/','+','-'):");
        char o = scanner.next().charAt(0);
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Service stub = (Service) registry.lookup("Service");
            String response = stub.operation(i,j,o);
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
