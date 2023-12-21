package zoo;
public class ZooManagement {
    public static void main(String[] args) {
        // Создаем экземпляр класса Lion
        Lion lion = new Lion();
        lion.sleep();
        lion.makeSound();
        lion.eat();

        // Создаем экземпляр класса Dog
        Dog dog = new Dog("Buddy");
        dog.sleep(); 
        dog.makeSound();
        dog.eat();


        Animal animal1 = new Lion();
        animal1.sleep();
        animal1.makeSound();
        animal1.eat();

        Animal animal2 = new Dog("Max");
        animal2.sleep();
        animal2.makeSound();
        animal2.eat();
    }
}