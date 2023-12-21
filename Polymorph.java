class Animal {
    void makeSound() {
        System.out.println("Животное издает звук");
    }
}

class Cat extends Animal {
    @Override
    void makeSound() {
        System.out.println("Мяу!");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Гав!");
    }
}

class Frog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Ква!");
    }
}

public class Polymorph {
    public static void main(String[] args) {
        Animal animal1 = new Cat();
        Animal animal2 = new Dog();
        Animal animal3 = new Frog();

        animal1.makeSound(); // Вывод: "Мяу!"
        animal2.makeSound(); // Вывод: "Гав!"
        animal3.makeSound(); // Вывод: "Ква!"
    }
}
