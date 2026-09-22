import java.util.Scanner;

class Person {
    private String name;
    private String gender;
    private String dob;
    private String address;

    public Person() {
    }

    public Person(String name, String gender, String dob, String address) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public void inputInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên: ");
        name = scanner.nextLine();
        System.out.print("Nhập giới tính: ");
        gender = scanner.nextLine();
        System.out.print("Nhập ngày sinh (dd/mm/yyyy): ");
        dob = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        address = scanner.nextLine();
    }

    public void printInfo() {
        System.out.println("Tên: " + name + ", Giới tính: " + gender + ", Ngày sinh: " + dob + ", Địa chỉ: " + address);
    }
}

class Student extends Person {
    private float gpa;
    private String email;

    public Student() {
        super();
    }

    public Student(String name, String gender, String dob, String address, float gpa, String email) {
        super(name, gender, dob, address);
        this.gpa = gpa;
        this.email = email;
    }

    public float getGpa() { return gpa; }
    public void setGpa(float gpa) { this.gpa = gpa; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public void inputInfo() {
        super.inputInfo();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Nhập điểm trung bình (0.0 - 10.0): ");
            gpa = scanner.nextFloat();
            if (gpa >= 0.0 && gpa <= 10.0) {
                break;
            }
            System.out.println("Điểm không hợp lệ! Vui lòng nhập lại.");
        }
        scanner.nextLine();

        while (true) {
            System.out.print("Nhập email (phải chứa '@' và không có khoảng trắng): ");
            email = scanner.nextLine();
            if (email.contains("@") && !email.contains(" ")) {
                break;
            }
            System.out.println("Email không hợp lệ! Vui lòng nhập lại.");
        }
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Điểm trung bình: " + gpa + ", Email: " + email + ", Học bổng: " + (hasScholarship() ? "Có" : "Không"));
    }

    public boolean hasScholarship() {
        return gpa > 8.0;
    }
}

public class StudentDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số lượng sinh viên n: ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Số lượng sinh viên không hợp lệ!");
            return;
        }

        Student[] students = new Student[n];

        System.out.println("\n--- NHẬP THÔNG TIN SINH VIÊN ---");
        for (int i = 0; i < n; i++) {
            System.out.println("Nhập thông tin sinh viên thứ " + (i + 1) + ":");
            students[i] = new Student();
            students[i].inputInfo();
        }

        System.out.println("\n--- DANH SÁCH TẤT CẢ SINH VIÊN ---");
        for (int i = 0; i < n; i++) {
            System.out.print("SV " + (i + 1) + " -> ");
            students[i].printInfo();
        }

        // Tìm điểm cao nhất và thấp nhất
        float maxGpa = students[0].getGpa();
        float minGpa = students[0].getGpa();
        for (int i = 1; i < n; i++) {
            if (students[i].getGpa() > maxGpa) {
                maxGpa = students[i].getGpa();
            }
            if (students[i].getGpa() < minGpa) {
                minGpa = students[i].getGpa();
            }
        }

        // Hiển thị sinh viên có điểm trung bình cao nhất
        System.out.println("\n--- SINH VIÊN CÓ ĐIỂM TRUNG BÌNH CAO NHẤT ---");
        for (int i = 0; i < n; i++) {
            if (students[i].getGpa() == maxGpa) {
                students[i].printInfo();
            }
        }

        // Hiển thị sinh viên có điểm trung bình thấp nhất
        System.out.println("\n--- SINH VIÊN CÓ ĐIỂM TRUNG BÌNH THẤP NHẤT ---");
        for (int i = 0; i < n; i++) {
            if (students[i].getGpa() == minGpa) {
                students[i].printInfo();
            }
        }

        // Hiển thị tất cả sinh viên được học bổng
        System.out.println("\n--- DANH SÁCH SINH VIÊN ĐƯỢC HỌC BỔNG ---");
        boolean hasAnyScholarship = false;
        for (int i = 0; i < n; i++) {
            if (students[i].hasScholarship()) {
                students[i].printInfo();
                hasAnyScholarship = true;
            }
        }
        if (!hasAnyScholarship) {
            System.out.println("Không có sinh viên nào đạt điều kiện nhận học bổng.");
        }
    }
}