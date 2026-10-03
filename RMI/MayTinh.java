import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MayTinh extends Remote {
    int cong(int a, int b) throws RemoteException;
}
