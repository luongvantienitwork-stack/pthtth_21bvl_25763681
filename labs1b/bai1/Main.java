package bai1;

// Chương trình chính Main.java cho Bài 1
public class Main {
    public static void main(String[] args) {
        // Tạo ít nhất hai sản phẩm
        SanPham sp1 = new SanPham("SP01", "Laptop Dell", 15000000, 10);
        SanPham sp2 = new SanPham("SP02", "Bàn phím cơ", 1200000, 25);

        System.out.println("--- THÔNG TIN BAN ĐẦU ---");
        sp1.hienThiThongTin();
        sp2.hienThiThongTin();

        // Nhập thêm hàng cho một sản phẩm
        System.out.println("\nNhập thêm 5 sản phẩm cho SP01...");
        sp1.nhapHang(5);
        sp1.hienThiThongTin();

        // Thử bán hàng thành công
        System.out.println("\nBán 3 sản phẩm SP01 (Thành công)...");
        boolean ban1 = sp1.banHang(3);
        System.out.println("Kết quả bán: " + ban1);
        sp1.hienThiThongTin();

        // Thử bán số lượng lớn hơn tồn kho
        System.out.println("\nBán 50 sản phẩm SP01 (Vượt quá tồn kho)...");
        boolean ban2 = sp1.banHang(50);
        System.out.println("Kết quả bán: " + ban2);
        sp1.hienThiThongTin();
    }
}