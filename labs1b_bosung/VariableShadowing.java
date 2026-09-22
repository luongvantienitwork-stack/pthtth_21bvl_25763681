class A {
    int x = 10; 
}

class B extends A {
    int x = 20; 
}

class C extends B {
    int x = 30; 

    public void setXOfA(int newValue) {
        ((A) this).x = newValue;
    }

    public void displayValues() {
        System.out.println("Gia tri x cua C (this.x): " + this.x);
        System.out.println("Gia tri x cua B (super.x): " + super.x);
        System.out.println("Gia tri x cua A (((A)this).x): " + ((A) this).x);
    }
}

public class VariableShadowing {
    public static void main(String[] args) {
        C obj = new C();
        
        System.out.println("--- Truoc khi thay doi ---");
        obj.displayValues();
        
        obj.setXOfA(100);
        
        System.out.println("\n--- Sau khi thay doi x cua A ---");
        obj.displayValues();
    }
}