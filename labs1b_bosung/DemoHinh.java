class HCN {
    private double dai;
    private double rong;

    public HCN() {
    };

    public HCN(double dai, double rong) {
        this.dai = dai;
        this.rong = rong;
    }

    public double getDai() {
        return dai;
    }

    public void setDai(double dai) {
        this.dai = dai;
    }

    public double getRong() {
        return rong;
    }

    public void setRong(double rong) {
        this.rong = rong;
    }

    public double tinhChuVi() {
        return (dai + rong) * 2;
    }

    public double tinhDienTich() {
        return dai * rong;
    }

    public void xuatThongTin() {
        System.out.println("HCN [Dài: " + dai + ", Rộng: " + rong + "] | Chu vi: " + tinhChuVi() + " | Diện tích: "
                + tinhDienTich());
    }

}

class HVuong {
    private double canh;

    public HVuong() {
    }

    public HVuong(double canh) {
        this.canh = canh;
    }

    public double getCanh() {
        return canh;
    }

    public void setCanh(double canh) {
        this.canh = canh;
    }

    public double tinhChuVi() {
        return canh * 4;
    }

    public double tinhDienTich() {
        return canh * canh;
    }

    public void xuatThongTin() {
        System.out.println(
                "Hình Vuông [Cạnh: " + canh + "] | Chu vi: " + tinhChuVi() + " | Diện tích: " + tinhDienTich());
    }
}

class HTG {
    private double a, b, c;

    public HTG() {
    }

    public HTG(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double tinhChuVi() {
        return a + b + c;
    }

    public double tinhDienTich() {
        double p = tinhChuVi() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public void xuatThongTin() {
        System.out.println("Tam giác [Cạnh: " + a + ", " + b + ", " + c + "] | Chu vi: " + tinhChuVi()
                + " | Diện tích: " + tinhDienTich());
    }
}

public class DemoHinh {
    public static void main(String[] args) {
        HCN hcn = new HCN(5, 3);
        HVuong hv = new HVuong(4);
        HTG htg = new HTG(3, 4, 5);

        hcn.xuatThongTin();
        hv.xuatThongTin();
        htg.xuatThongTin();
    }
}
