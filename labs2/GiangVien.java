public class GiangVien extends Nguoi {
    private String maGiangVien;
    private String chuyenMon;
    private double luongCoBan;
    private double heSoLuong;

    public GiangVien(String hoTen, int namSinh, String diaChi, String maGiangVien, String chuyenMon, double luongCoBan, double heSoLuong) {
        super(hoTen, namSinh, diaChi);
        this.maGiangVien = maGiangVien;
        this.chuyenMon = chuyenMon;
        this.luongCoBan = luongCoBan;
        this.heSoLuong = heSoLuong;
    }

    public String getMaGiangVien() { return maGiangVien; }
    public void setMaGiangVien(String maGiangVien) { this.maGiangVien = maGiangVien; }

    public String getChuyenMon() { return chuyenMon; }
    public void setChuyenMon(String chuyenMon) { this.chuyenMon = chuyenMon; }

    public double getLuongCoBan() { return luongCoBan; }
    public void setLuongCoBan(double luongCoBan) { this.luongCoBan = luongCoBan; }

    public double getHeSoLuong() { return heSoLuong; }
    public void setHeSoLuong(double heSoLuong) { this.heSoLuong = heSoLuong; }

    public double tinhLuong() {
        return luongCoBan * heSoLuong;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Mã giảng viên: " + maGiangVien);
        System.out.println("Chuyên môn: " + chuyenMon);
        System.out.println("Lương cơ bản: " + String.format("%,.0f", luongCoBan) + " VNĐ");
        System.out.println("Hệ số lương: " + heSoLuong);
        System.out.println("Lương thực nhận: " + String.format("%,.0f", tinhLuong()) + " VNĐ");
    }
}