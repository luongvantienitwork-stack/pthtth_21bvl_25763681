// Bây giờ mình có một danh sách sinh viên với sinh viên có những thuộc tính là mssv, tên, tuổi, và
// danh sách môn học, môn học có những thuộc tính như là tên môn học, tín chỉ, điểm. Làm sao để lưu
// danh sách sinh viên xuống file nhị phân?
// Giải quyết vấn đề ghi file nhị phân trong Java:
// Với vấn đề đặt ra thì mình xác định sẽ có một class SinhVien, class MonHoc và một class xử lý để lưu
// danh sách sinh viên xuống file nhị phân, mình gọi class này là WriteBinaryFile.
// Class SinhVien với các thuộc tính như mã số sinh viên, tên, tuổi và danh sách môn học.
// Class MonHoc với các thuộc tính như tên môn học, số tín chỉ và điểm.
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WriteBinaryFile {
    public static void saveSV(String src, ArrayList<SinhVien> listSV)
        throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(
                new File(src)))) {
            dos.writeInt(listSV.size());
            for (SinhVien sv : listSV) {
                dos.writeUTF(sv.getMssv());
                dos.writeUTF(sv.getTen());
                dos.writeInt(sv.getTuoi());
                dos.writeInt(sv.getListMH().size());
                for (MonHoc mh : sv.getListMH()) {
                    dos.writeUTF(mh.getTenMonHoc());
                    dos.writeInt(mh.getTinChi());
                    dos.writeDouble(mh.getDiem());
                }
            }
            dos.flush();
        }
    }
    public static void main(String[] args) throws IOException {
        MonHoc mh = new MonHoc("ltcb", 3, 6.7);
        MonHoc mh1 = new MonHoc("ltw", 3, 6.7);
        MonHoc mh2 = new MonHoc("tkhdt", 3, 6.7);
        ArrayList<MonHoc> listMH = new ArrayList<>();
        listMH.add(mh2);
        listMH.add(mh1);
        listMH.add(mh);
        ArrayList<SinhVien> listSV = new ArrayList<>();
        SinhVien sv = new SinhVien("11329078", "nguyen van A", 23, listMH);
        SinhVien sv1 = new SinhVien("11329078", "nguyen Van B", 23, listMH);
        listSV.add(sv);
        listSV.add(sv1);
        saveSV("/home/van-tien/eclipse-workspace/pthtth/labs1/a.txt", listSV);
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
    }
}
