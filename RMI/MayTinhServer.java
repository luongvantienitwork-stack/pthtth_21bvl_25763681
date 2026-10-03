import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MayTinhServer {
    public static void main(String[] args) {
        try {
            MayTinh mayTinh = new MayTinhImpl();

            LocateRegistry.createRegistry(1099);
            Naming.bind("rmi://localhost:1099/MayTinhObj", mayTinh);

            System.out.println("RMI Server da san sang tren cong 1099.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
