import java.rmi.Naming;

public class MayTinhClient {
    public static void main(String[] args) {
        try {
            MayTinh mt = (MayTinh) Naming.lookup("rmi://localhost:1099/MayTinhObj");

            int ketQua = mt.cong(8, 5);
            System.out.println("Ket qua tu server: " + ketQua);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
