package zoo;
public abstract class DomesticAnimal implements Animal {
    private final String name;
    
    public DomesticAnimal(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
}