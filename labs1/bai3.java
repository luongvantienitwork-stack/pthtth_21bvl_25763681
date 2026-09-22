// Viết chương trình nhập vào 2 số A và B, sau đó in ra màn hình kết quả tính tổng.
import java.util.Scanner;

public class bai3 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Vui lòng nhập số hạng thứ nhất: ");
            int soA = scanner.nextInt();
            System.out.print("Vui lòng nhập số hạng thứ hai: ");
            int soB = scanner.nextInt();
            int kq = soA + soB;
            System.out.println("Tính tổng [" + soA + " + " + soB + " = " + kq);
        }
    }
}