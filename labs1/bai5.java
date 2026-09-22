// Viết chương trình nhập vào một tháng trong năm, sau đó bạn in ra màn hình tháng vừa
// nhập bằng tiếng Anh
import java.util.Scanner;
public class bai5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isrun = true;
        while (isrun) {
            System.out.print("Vui lòng nhập tháng: ");
            int so = scanner.nextInt();
            switch (so) {
                case 1 -> System.out.println("January");
                case 2 -> System.out.println("February");
                case 3 -> System.out.println("March");
                case 4 -> System.out.println("April");
                case 5 -> System.out.println("May");
                case 6 -> System.out.println("June");
                case 7 -> System.out.println("July");
                case 8 -> System.out.println("August");
                case 9 -> System.out.println("September");
                case 10 -> System.out.println("October");
                case 11 -> System.out.println("November");
                case 12 -> System.out.println("December");
                default -> {
                    isrun = false;
                    scanner.close();
                    System.out.println("STOP");
                }
            }
        }
    }
}