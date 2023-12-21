package zoo;
public class Dog extends DomesticAnimal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
    
    @Override
    public void eat() {
        System.out.println("Dog is eating.");
    }
}