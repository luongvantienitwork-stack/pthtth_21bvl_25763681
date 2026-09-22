public class SanPham {
    private String maSP;
    private String tenSP;
    private double donGia;
    private int soLuongTon;

    public SanPham(String maSP, String tenSP, double donGia, int soLuongTon) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuongTon = Math.max(soLuongTon, 0);
    }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public int getSoLuongTon() { return soLuongTon; }

    public double tinhThanhTien() {
        return donGia * soLuongTon;
    }

    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap > 0) {
            this.soLuongTon += soLuongNhap;
            System.out.println("-> Nhap hang thanh cong: +" + soLuongNhap);
        } else {
            System.out.println("-> Loi: So luong nhap phai lon hon 0!");
        }
    }

    public boolean banHang(int soLuongBan) {
        if (soLuongBan <= 0) {
            System.out.println("-> Loi: So luong ban phai lon hon 0!");
            return false;
        }
        if (soLuongBan > this.soLuongTon) {
            System.out.println("-> Loi: Khong du hang ton kho de ban!");
            return false;
        }

        this.soLuongTon -= soLuongBan;
        System.out.println("-> Ban hang thanh cong: -" + soLuongBan);
        return true;
    }

    public void hienThiThongTin() {
        System.out.printf("Ma SP: %-8s | Ten SP: %-15s | Don gia: %,10.0f VND | Ton kho: %-3d | Thanh tien: %,12.0f VND\n", 
                maSP, tenSP, donGia, soLuongTon, tinhThanhTien());
    }
}