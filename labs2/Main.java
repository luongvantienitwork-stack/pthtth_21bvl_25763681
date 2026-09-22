public class Main {
    public static void main(String[] args) {
        System.out.println("================= KHOI TAO SAN PHAM =================");
        SanPham sp1 = new SanPham("SP01", "Laptop Dell", 15000000, 10);
        SanPham sp2 = new SanPham("SP02", "Chuot Logitech", 500000, 20);

        sp1.hienThiThongTin();
        sp2.hienThiThongTin();

        System.out.println("\n================= THUC HIEN THAO TAC =================");
        
        System.out.println("\n[1] Nhap them 5 Laptop Dell:");
        sp1.nhapHang(5);
        sp1.hienThiThongTin();

        System.out.println("\n[2] Ban 8 Chuot Logitech (Thanh cong):");
        sp2.banHang(8);
        sp2.hienThiThongTin();

        System.out.println("\n[3] Ban 50 Chuot Logitech (That bai - Vuot ton kho):");
        boolean ketQua = sp2.banHang(50);
        System.out.println("Ket qua giao dich: " + (ketQua ? "Thanh cong" : "That bai"));
        sp2.hienThiThongTin();
    }
}