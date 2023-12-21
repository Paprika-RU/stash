//пример пользы принципа наследования в ООП
class Animal {
    void eat() {
        System.out.println("Животное ест");
    }
}

class Cat extends Animal {
    void meow() {
        System.out.println("Мяу!");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Гав!");
    }
}

class Frog extends Animal {
    void ribbit() {
        System.out.println("Ква!");
    }
}

public class ExampleClass {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eat();  // Наследуемый метод от Animal
        cat.meow(); // Уникальный метод для класса Cat

        Dog dog = new Dog();
        dog.eat();  // Наследуемый метод от Animal
        dog.bark(); // Уникальный метод для класса Dog

        Frog frog = new Frog();
        frog.eat();  // Наследуемый метод от Animal
        frog.ribbit(); // Уникальный метод для класса Frog
    }
}
