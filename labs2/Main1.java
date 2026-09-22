public class Main1 {
    public static void main(String[] args) {
        SinhVien sv1 = new SinhVien("Nguyen Van A", 2003, "Ha Noi", "SV001", "Cong nghe thong tin", 8.8);
        SinhVien sv2 = new SinhVien("Tran Thi B", 2004, "Da Nang", "SV002", "Khoa hoc may tinh", 6.5);

        GiangVien gv1 = new GiangVien("Le Van C", 1985, "TP.HCM", "GV001", "Lap trinh Java", 5000000, 3.5);
        GiangVien gv2 = new GiangVien("Pham Thi D", 1990, "Can Tho", "GV002", "Co so du lieu", 4500000, 3.0);

        System.out.println("================ THONG TIN DANH SACH ================");
        
        System.out.println("\n--- Sinh Vien 1 ---");
        sv1.hienThiThongTin();

        System.out.println("\n--- Sinh Vien 2 ---");
        sv2.hienThiThongTin();

        System.out.println("\n--- Giang Vien 1 ---");
        gv1.hienThiThongTin();

        System.out.println("\n--- Giang Vien 2 ---");
        gv2.hienThiThongTin();

        System.out.println("\n================ XEP LOAI SINH VIEN ================");
        System.out.println(sv1.getHoTen() + " (" + sv1.getMaSinhVien() + ") - Xep loai: " + sv1.xepLoai());
        System.out.println(sv2.getHoTen() + " (" + sv2.getMaSinhVien() + ") - Xep loai: " + sv2.xepLoai());

        System.out.println("\n================ LUONG GIANG VIEN ================");
        System.out.println(gv1.getHoTen() + " (" + gv1.getMaGiangVien() + ") - Luong: " + String.format("%,.0f", gv1.tinhLuong()) + " VND");
        System.out.println(gv2.getHoTen() + " (" + gv2.getMaGiangVien() + ") - Luong: " + String.format("%,.0f", gv2.tinhLuong()) + " VND");
    }
}