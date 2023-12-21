
package zoo;
public abstract class WildAnimal implements Animal {
    private final String species;
    
    public WildAnimal(String species) {
        this.species = species;
    }
    
    public String getSpecies() {
        return species;
    }

    @Override
    public void sleep() {
        System.out.println(species + " is sleeping.");
    }
}