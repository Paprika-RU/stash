package zoo;
public class ZooManagement {
    public static void main(String[] args) {
        // Создаем экземпляр класса Lion
        Lion lion = new Lion();
        lion.sleep();
        lion.makeSound();
        lion.eat();

        // Создаем экземпляр класса Panther
        Panther panther = new Panther();
        panther.sleep();
        panther.makeSound();
        panther.eat();

        // Создаем экземпляр класса Dog
        Dog dog = new Dog("Fluffy");
        dog.sleep(); 
        dog.makeSound();
        dog.eat();

        // Создаем экземпляр класса Cat
        Cat cat = new Cat("Fiona");
        cat.sleep(); 
        cat.makeSound();
        cat.eat();

        Animal animal1 = new Lion();
        animal1.sleep();
        animal1.makeSound();
        animal1.eat();

        Animal animal2 = new Dog("Puppy");
        animal2.sleep();
        animal2.makeSound();
        animal2.eat();
    }
}
