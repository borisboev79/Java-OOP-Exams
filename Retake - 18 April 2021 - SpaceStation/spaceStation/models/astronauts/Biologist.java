package spaceStation.models.astronauts;

public class Biologist extends BaseAstronaut{

    public Biologist(String name) {
        super(name, 70);
    }
    @Override
    public void breath() {
        setOxygen(getOxygen() - 5);
        if(getOxygen() < 0) {
            setOxygen(0);
        }
    }

}
