import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:JDBC_21AVL/CSDL/sinhvien.db");

            Statement stmt = conn.createStatement();

            // String sql_TaoBang = "CREATE TABLE IF NOT EXISTS sinhvien (mssv INTEGER PRIMARY KEY NOT NULL, hoten TEXT NOT NULL, nganh TEXT)";
            // stmt.executeUpdate(sql_TaoBang);

            // String sql_themSV1 = "INSERT INTO sinhvien(mssv,hoten,nganh) VALUES (1, 'ho dinh an', 'cntt')";
            // stmt.executeUpdate(sql_themSV1);
            // String sql_themSV2 = "INSERT INTO sinhvien(mssv,hoten,nganh) VALUES (2, 'ngo thanh hau', 'cntt')";
            // stmt.executeUpdate(sql_themSV2);
            // String sql_themSV3 = "INSERT INTO sinhvien(mssv,hoten,nganh) VALUES (3, 'nguyen manh cuong', 'cntt')";
            // stmt.executeUpdate(sql_themSV3);

            // String sql_update = "UPDATE sinhvien SET nganh='khmt' WHERE mssv=3";
            // stmt.executeUpdate(sql_update);

            String sql_query = "SELECT * FROM sinhvien";
            ResultSet rs = stmt.executeQuery(sql_query);

            while(rs.next()) {
                int mssv = rs.getInt("mssv");
                String hoten = rs.getString("hoten");
                String nganh = rs.getString("nganh");
                System.out.println(mssv + ", " + hoten + ", nganh: " + nganh);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}