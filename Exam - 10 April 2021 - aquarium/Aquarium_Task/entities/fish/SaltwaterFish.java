package aquarium.entities.fish;

public class SaltwaterFish extends BaseFish{
    private int size;
    public SaltwaterFish(String name, String species, double price) {
        super(name, species, price);
        this.size = 5;
    }

    public void eat() {
        size += 2;
    }
}
