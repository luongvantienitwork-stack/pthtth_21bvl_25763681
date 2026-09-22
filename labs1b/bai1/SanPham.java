package bai1;
// Lớp SanPham.java
public class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private double donGia;
    private int soLuongTonKho;

    // Constructor đầy đủ tham số
    public SanPham(String maSanPham, String tenSanPham, double donGia, int soLuongTonKho) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuongTonKho = soLuongTonKho;
    }

    // Phương thức tính thành tiền
    public double tinhThanhTien() {
        return this.donGia * this.soLuongTonKho;
    }

    // Phương thức nhập hàng
    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap > 0) {
            this.soLuongTonKho += soLuongNhap;
        }
    }

    // Phương thức bán hàng
    public boolean banHang(int soLuongBan) {
        if (soLuongBan > 0 && soLuongBan <= this.soLuongTonKho) {
            this.soLuongTonKho -= soLuongBan;
            return true;
        }
        return false;
    }

    // Phương thức hiển thị thông tin
    public void hienThiThongTin() {
        System.out.println("Mã SP: " + maSanPham + " | Tên SP: " + tenSanPham + 
                           " | Đơn giá: " + donGia + " | Tồn kho: " + soLuongTonKho + 
                           " | Thành tiền: " + tinhThanhTien());
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}