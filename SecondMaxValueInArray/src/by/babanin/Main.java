package by.babanin;

public class Main {
    public static void main(String[] args) {
        Parent parent = new Child();
        parent.b();
    }
}

class Parent {
    public void a() {
        System.out.println("A PARENT");
    }

    public void b() {
        this.a();
        System.out.println("B PARENT");
    }
}

class Child extends Parent {
    @Override
    public void a() {
        System.out.println("A CHILD");
    }

    @Override
    public void b() {
        System.out.println("B CHILD");
        super.b();
    }
}