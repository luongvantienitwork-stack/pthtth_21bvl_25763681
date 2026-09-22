package bai2;

// Lớp SinhVien.java
public class SinhVien extends Nguoi {
    private String maSinhVien;
    private String nganhHoc;
    private double diemTrungBinh;

    public SinhVien(String hoTen, int namSinh, String diaChi, String maSinhVien, String nganhHoc, double diemTrungBinh) {
        super(hoTen, namSinh, diaChi);
        this.maSinhVien = maSinhVien;
        this.nganhHoc = nganhHoc;
        this.diemTrungBinh = diemTrungBinh;
    }

    public String xepLoai() {
        if (diemTrungBinh >= 8.5) return "Giỏi";
        if (diemTrungBinh >= 7.0) return "Khá";
        if (diemTrungBinh >= 5.0) return "Trung bình";
        return "Yếu";
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println(" | MSSV: " + maSinhVien + " | Ngành: " + nganhHoc + 
                           " | ĐTB: " + diemTrungBinh + " | Xếp loại: " + xepLoai());
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getNganhHoc() {
        return nganhHoc;
    }

    public void setNganhHoc(String nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }
}