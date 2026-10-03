import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MayTinhImpl extends UnicastRemoteObject implements MayTinh {
    public MayTinhImpl() throws RemoteException {
        super();
    }

    @Override
    public int cong(int a, int b) throws RemoteException {
        System.out.println("Dang tinh: " + a + " + " + b);
        return a + b;
    }
}
