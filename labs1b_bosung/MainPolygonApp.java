import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }


}

interface Polygon {
    double area();
    double perimeter();
    List<Point> getVertices();
}

abstract class BasePolygon implements Polygon {
    protected List<Point> vertices;

    public BasePolygon(List<Point> vertices) {
        this.vertices = vertices;
    }

    @Override
    public List<Point> getVertices() {
        return vertices;
    }

    @Override
    public double perimeter() {
        double p = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Point p1 = vertices.get(i);
            Point p2 = vertices.get((i + 1) % n);
            p += Math.hypot(p2.x - p1.x, p2.y - p1.y);
        }
        return p;
    }

    @Override
    public double area() {
        double a = 0;
        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Point p1 = vertices.get(i);
            Point p2 = vertices.get((i + 1) % n);
            a += (p1.x * p2.y - p2.x * p1.y);
        }
        return Math.abs(a) / 2.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Polygon)) return false;
        Polygon other = (Polygon) obj;
        List<Point> v1 = this.getVertices();
        List<Point> v2 = other.getVertices();
        if (v1.size() != v2.size()) return false;
        int n = v1.size();
        for (int offset = 0; offset < n; offset++) {
            boolean matchForward = true;
            boolean matchBackward = true;
            for (int i = 0; i < n; i++) {
                if (!v1.get(i).equals(v2.get((i + offset) % n))) {
                    matchForward = false;
                }
                if (!v1.get(i).equals(v2.get((n - i + offset) % n))) {
                    matchBackward = false;
                }
            }
            if (matchForward || matchBackward) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.vertices);
        return hash;
    }

    
}

class Triangle extends BasePolygon {
    public Triangle(List<Point> vertices) {
        super(vertices);
    }
}

class Quadrilateral extends BasePolygon {
    public Quadrilateral(List<Point> vertices) {
        super(vertices);
    }
}

class Pentagon extends BasePolygon {
    public Pentagon(List<Point> vertices) {
        super(vertices);
    }
}

class Hexagon extends BasePolygon {
    public Hexagon(List<Point> vertices) {
        super(vertices);
    }
}

class Octagon extends BasePolygon {
    public Octagon(List<Point> vertices) {
        super(vertices);
    }
}

class IsoscelesTriangle extends Triangle {
    public IsoscelesTriangle(List<Point> vertices) {
        super(vertices);
    }
}

class EquilateralTriangle extends IsoscelesTriangle {
    public EquilateralTriangle(List<Point> vertices) {
        super(vertices);
    }
}

class Rectangle extends Quadrilateral {
    public Rectangle(List<Point> vertices) {
        super(vertices);
    }
}

class Square extends Rectangle {
    public Square(List<Point> vertices) {
        super(vertices);
    }
}

public class MainPolygonApp {
    private static List<Point> inputVertices(Scanner scanner, int count) {
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            System.out.print("  Toa do dinh " + (i + 1) + " (x y): ");
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            list.add(new Point(x, y));
        }
        return list;
    }

    private static Polygon createPolygon(Scanner scanner, int type) {
        List<Point> v;
        switch (type) {
            case 1 -> {
                System.out.println("Nhap 3 dinh cho tam giac:");
                v = inputVertices(scanner, 3);
                return new Triangle(v);
            }
            case 2 -> {
                System.out.println("Nhap 3 dinh cho tam giac can:");
                v = inputVertices(scanner, 3);
                return new IsoscelesTriangle(v);
            }
            case 3 -> {
                System.out.println("Nhap 3 dinh cho tam giac deu:");
                v = inputVertices(scanner, 3);
                return new EquilateralTriangle(v);
            }
            case 4 -> {
                System.out.println("Nhap 4 dinh cho hinh chu nhat:");
                v = inputVertices(scanner, 4);
                return new Rectangle(v);
            }
            case 5 -> {
                System.out.println("Nhap 4 dinh cho hinh vuong:");
                v = inputVertices(scanner, 4);
                return new Square(v);
            }
            case 6 -> {
                System.out.println("Nhap 4 dinh cho tu giac:");
                v = inputVertices(scanner, 4);
                return new Quadrilateral(v);
            }
            case 7 -> {
                System.out.println("Nhap 5 dinh cho ngu giac:");
                v = inputVertices(scanner, 5);
                return new Pentagon(v);
            }
            case 8 -> {
                System.out.println("Nhap 6 dinh cho luc giac:");
                v = inputVertices(scanner, 6);
                return new Hexagon(v);
            }
            case 9 -> {
                System.out.println("Nhap 8 dinh cho bat giac:");
                v = inputVertices(scanner, 8);
                return new Octagon(v);
            }
            default -> {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== TAO DA GIAC THU NHAT ===");
            System.out.println("1. Tam giac thuong");
            System.out.println("2. Tam giac can");
            System.out.println("3. Tam giac deu");
            System.out.println("4. Hinh chu nhat");
            System.out.println("5. Hinh vuong");
            System.out.println("6. Tu giac");
            System.out.println("7. Ngu giac");
            System.out.println("8. Luc giac");
            System.out.println("9. Bat giac");
            System.out.print("Chon loai (1-9): ");
            int t1 = scanner.nextInt();
            
            Polygon p1 = createPolygon(scanner, t1);
            if (p1 != null) {
                System.out.printf("Chu vi P1: %.2f\n", p1.perimeter());
                System.out.printf("Dien tich P1: %.2f\n", p1.area());
            }
            
            System.out.println("\n=== TAO DA GIAC THU HAI DE SO SANH ===");
            System.out.print("Chon loai (1-9): ");
            int t2 = scanner.nextInt();
            
            Polygon p2 = createPolygon(scanner, t2);
            if (p2 != null) {
                System.out.printf("Chu vi P2: %.2f\n", p2.perimeter());
                System.out.printf("Dien tich P2: %.2f\n", p2.area());
            }
            
            if (p1 != null && p2 != null) {
                boolean isEqual = p1.equals(p2);
                System.out.println("\nHai da giac co giong nhau khong? " + (isEqual ? "Co" : "Khong"));
            }
        }
    }
}