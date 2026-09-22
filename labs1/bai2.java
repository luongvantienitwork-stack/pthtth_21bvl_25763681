// Viết chương trình nhập vào tên của bạn, sau đó in ra màn hình với nội dung “Hi, I am”
// cộng với tên bạn vừa nhập.
import java.util.Scanner;

public class bai2 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("What's your name?");
            String str = scanner.nextLine();
            System.out.println("Hi, I am "+str);
        }
    }
}