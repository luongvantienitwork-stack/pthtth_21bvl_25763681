// Giải quyết vấn đề này rất đơn giản bạn chỉ cần biết ở bài trước mình dùng stream nào ghi thì chỉ cần
// dùng stream tương ứng mà đọc ra. Sau đây là phương thức load sinh viên từ file nhị phân và stream
// được dùng ở đây là DataInputStream. Sau đây là đoạn code mô tả chi tiết cách đọc file nhị phân
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoadSV {
    public static void loadSV(String src) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(new File(
                src)))) {
            int size = dis.readInt();
            var listSV = new ArrayList<SinhVien>();
            for (int i = 0; i < size; i++) {
                String mssv = dis.readUTF();
                String name = dis.readUTF();
                int age = dis.readInt();
                int sizemh = dis.readInt();
                ArrayList<MonHoc> listMH = new ArrayList<>();
                for (int j = 0; j < sizemh; j++) {
                    String tenMonHoc = dis.readUTF();
                    int tinChi = dis.readInt();
                    double diem = dis.readDouble();
                    MonHoc mh1 = new MonHoc(tenMonHoc, tinChi, diem);
                    listMH.add(mh1);
                }
                listSV.add(new SinhVien(mssv, name, age, listMH));
            }
            for(SinhVien sv : listSV){
                System.out.println(sv.toString());
            }
        }
    }
    public static void main(String[] args) throws IOException {
        loadSV("/home/van-tien/eclipse-workspace/pthtth/labs1/a.txt");
    }

    public static class MonHoc {
        private String tenMonHoc;
        private int tinChi;
        private double diem;

        public MonHoc(String tenMonHoc, int tinChi, double diem) {
            this.tenMonHoc = tenMonHoc;
            this.tinChi = tinChi;
            this.diem = diem;
        }

        public String getTenMonHoc() {
            return tenMonHoc;
        }

        public int getTinChi() {
            return tinChi;
        }

        public double getDiem() {
            return diem;
        }

        public void setTenMonHoc(String tenMonHoc) {
            this.tenMonHoc = tenMonHoc;
        }

        public void setTinChi(int tinChi) {
            this.tinChi = tinChi;
        }

        public void setDiem(double diem) {
            this.diem = diem;
        }

        @Override
        public String toString() {
            return tenMonHoc + " (" + tinChi + " tín, " + diem + " điểm)";
        }
    }

    public static class SinhVien {
        private String mssv;
        private String ten;
        private int tuoi;
        private ArrayList<MonHoc> listMH;

        public SinhVien(String mssv, String ten, int tuoi, ArrayList<MonHoc> listMH) {
            this.mssv = mssv;
            this.ten = ten;
            this.tuoi = tuoi;
            this.listMH = listMH;
        }

        public String getMssv() {
            return mssv;
        }

        public String getTen() {
            return ten;
        }

        public int getTuoi() {
            return tuoi;
        }

        public ArrayList<MonHoc> getListMH() {
            return listMH;
        }

        public void setMssv(String mssv) {
            this.mssv = mssv;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public void setTuoi(int tuoi) {
            this.tuoi = tuoi;
        }

        public void setListMH(ArrayList<MonHoc> listMH) {
            this.listMH = listMH;
        }

        @Override
        public String toString() {
            return "MSSV: " + mssv + " | Tên: " + ten + " | Tuổi: " + tuoi + "\n   Danh sách môn: " + listMH;
        }
    }
}