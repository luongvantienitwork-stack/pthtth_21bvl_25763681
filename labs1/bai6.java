// Yêu cầu viết một chương trình để có thể giúp quản lý các vật nuôi trong vườn thú. Hiện
// tại, vườn thú có một số động vật như sư tử (lion), rắn (snake) và khỉ (monkey). Mỗi loài
// động vật đều có các thuộc tính chung bao gồm tên động vật (name) và cân nặng
// (weight). Người chủ vườn thú cần biết mỗi ngày sư tử ăn bao nhiêu thức ăn (eat), chiều
// dài của mỗi con rắn (length) và thức ăn yêu thích của khỉ. Ví dụ như sau:
// – Sư tử Leo nặng 300 cân và ăn 5 cân thịt mỗi ngày.
// – Con rắn Boa nặng 50 cân và dài 5 mét.
// – Con khỉ George nặng 150 cân và thích ăn chuối

import java.util.ArrayList;
import java.util.List;

public class bai6 {
    public static void main(String[] args) {
        List<Animal> zoo = new ArrayList<>();

        zoo.add(new Lion("Leo", 300, 5));
        zoo.add(new Snake("Boa", 50, 5));
        zoo.add(new Monkey("George", 150, "chuối"));

        System.out.println("=== THÔNG TIN ĐỘNG VẬT TRONG VƯỜN THÚ ===");
        for (Animal animal : zoo) {
            animal.displayInfo();
        }
    }
}

abstract class Animal {
    private String name;
    private double weight;

    public Animal(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public abstract void displayInfo();

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

class Lion extends Animal {
    private double dailyFood; 

    public Lion(String name, double weight, double dailyFood) {
        super(name, weight);
        this.dailyFood = dailyFood;
    }

    @Override
    public void displayInfo() {
        System.out.printf("Sư tử %s nặng %.0f cân và ăn %.0f cân thịt mỗi ngày.\n", 
                          getName(), getWeight(), dailyFood);
    }

    public double getDailyFood() {
        return dailyFood;
    }

    public void setDailyFood(double dailyFood) {
        this.dailyFood = dailyFood;
    }
}

class Snake extends Animal {
    private double length;

    public Snake(String name, double weight, double length) {
        super(name, weight);
        this.length = length;
    }

    @Override
    public void displayInfo() {
        System.out.printf("Con rắn %s nặng %.0f cân và dài %.0f mét.\n", 
                          getName(), getWeight(), length);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}

class Monkey extends Animal {
    private String favoriteFood;

    public Monkey(String name, double weight, String favoriteFood) {
        super(name, weight);
        this.favoriteFood = favoriteFood;
    }

    @Override
    public void displayInfo() {
        System.out.printf("Con khỉ %s nặng %.0f cân và thích ăn %s.\n", 
                          getName(), getWeight(), favoriteFood);
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }
}

