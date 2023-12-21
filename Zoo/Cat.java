package zoo;
public class Cat extends DomesticAnimal {
    public Cat(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
    
    @Override
    public void eat() {
        System.out.println("Cat is eating.");
    }
}
