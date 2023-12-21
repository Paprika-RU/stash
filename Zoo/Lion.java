package zoo;
public class Lion extends WildAnimal {
    public Lion() {
        super("Lion");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Roar!");
    }
    
    @Override
    public void eat() {
        System.out.println("Lion is eating.");
    }
}