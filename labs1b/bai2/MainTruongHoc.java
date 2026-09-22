package bai2;

// Chương trình chính Main.java cho Bài 2
public class MainTruongHoc {
    public static void main(String[] args) {
        // Tạo hai sinh viên
        SinhVien sv1 = new SinhVien("Nguyễn Văn A", 2004, "Hà Nội", "SV01", "Công nghệ thông tin", 8.8);
        SinhVien sv2 = new SinhVien("Trần Thị B", 2005, "Đà Nẵng", "SV02", "Kinh tế", 6.5);

        // Tạo hai giảng viên
        GiangVien gv1 = new GiangVien("Dr. John Doe", 1980, "Hồ Chí Minh", "GV01", "Trí tuệ nhân tạo", 5000000, 3.2);
        GiangVien gv2 = new GiangVien("Jane Smith", 1985, "Hải Phòng", "GV02", "Hệ thống thông tin", 4500000, 2.5);

        System.out.println("--- DANH SÁCH SINH VIÊN ---");
        sv1.hienThiThongTin();
        sv2.hienThiThongTin();

        System.out.println("\n--- DANH SÁCH GIẢNG VIÊN ---");
        gv1.hienThiThongTin();
        gv2.hienThiThongTin();
    }
}
