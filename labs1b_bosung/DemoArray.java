import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class IntArrayList {
    private ArrayList<Integer> list;

    public IntArrayList() {
        this.list = new ArrayList<>();
    }

    public void nhap(Scanner scanner) {
        System.out.print("Nhập số lượng phần tử muốn thêm: ");
        int count = scanner.nextInt();
        list.clear();
        System.out.println("Nhập các phần tử:");
        for (int i = 0; i < count; i++) {
            System.out.print("Phần tử thứ " + i + ": ");
            list.add(scanner.nextInt());
        }
    }

    public void xuat() {
        System.out.println("Mảng hiện tại: " + list);
    }

    public void themDau(int y) {
        list.add(0, y);
    }

    public void themCuoi(int y) {
        list.add(y);
    }

    public void themGiua(int i, int y) {
        if (i < 0 || i > list.size()) {
            System.out.println("Vị trí không hợp lệ!");
            return;
        }
        list.add(i, y);
    }

    public void xoaX(int x) {
        boolean removed = list.remove(Integer.valueOf(x));
        if (!removed) {
            System.out.println("Không tìm thấy phần tử " + x + " trong mảng.");
        }
    }

    public void xoaViTri(int j) {
        if (j < 0 || j >= list.size()) {
            System.out.println("Vị trí " + j + " không hợp lệ!");
            return;
        }
        list.remove(j);
    }

    public void radixSort(boolean ascending) {
        if (list.isEmpty()) return;

        int max = Collections.max(list);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(exp);
        }

        if (!ascending) {
            Collections.reverse(list);
        }
    }

    private void countSort(int exp) {
        int size = list.size();
        int[] output = new int[size];
        int[] count = new int[10];

        for (int i = 0; i < size; i++) {
            count[(list.get(i) / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = size - 1; i >= 0; i--) {
            output[count[(list.get(i) / exp) % 10] - 1] = list.get(i);
            count[(list.get(i) / exp) % 10]--;
        }

        for (int i = 0; i < size; i++) {
            list.set(i, output[i]);
        }
    }

    public int timKiemUnsorted(int b) {
        return list.indexOf(b);
    }

    public int timKiemSorted(int b) {
        return Collections.binarySearch(list, b);
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }
}

public class DemoArray {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            IntArrayList arrObj = new IntArrayList();
            
            arrObj.nhap(scanner);
            arrObj.xuat();
            
            arrObj.themDau(100);
            arrObj.themCuoi(200);
            arrObj.themGiua(2, 99);
            System.out.print("Sau khi thêm: ");
            arrObj.xuat();
            
            arrObj.xoaX(99);
            System.out.print("Sau khi xóa phần tử X (99): ");
            arrObj.xuat();
            
            arrObj.xoaViTri(0);
            System.out.print("Sau khi xóa phần tử ở vị trí 0: ");
            arrObj.xuat();
            
            System.out.print("Nhập phần tử B cần tìm (chưa sắp xếp): ");
            int b1 = scanner.nextInt();
            int pos1 = arrObj.timKiemUnsorted(b1);
            System.out.println(pos1 != -1 ? "Tìm thấy tại vị trí: " + pos1 : "Không tìm thấy");
            
            arrObj.radixSort(true);
            System.out.print("Sau khi sắp xếp tăng dần bằng Radix: ");
            arrObj.xuat();
            
            System.out.print("Nhập phần tử B cần tìm (đã sắp xếp): ");
            int b2 = scanner.nextInt();
            int pos2 = arrObj.timKiemSorted(b2);
            System.out.println(pos2 >= 0 ? "Tìm thấy tại vị trí: " + pos2 : "Không tìm thấy");
        }
    }
}