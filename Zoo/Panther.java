package zoo;
public class Panther extends WildAnimal {
    public Panther() {
        super("Panther");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Growl");
    }
    
    @Override
    public void eat() {
        System.out.println("Panther is eating.");
    }
}
