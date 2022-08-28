package spaceStation.models.astronauts;

import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.bags.Backpack;
import spaceStation.models.bags.Bag;

import java.util.stream.Collectors;

public abstract class BaseAstronaut implements Astronaut {
    private String name;
    private double oxygen;
    private Bag bag;

    public BaseAstronaut(String name, double oxygen) {
        setName(name);
        setOxygen(oxygen);
        this.bag = new Backpack();
    }

    protected void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.ASTRONAUT_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    protected void setOxygen(double oxygen) {
        if (oxygen < 0) {
            throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_OXYGEN_LESS_THAN_ZERO);
        }
        this.oxygen = oxygen;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getOxygen() {
        return oxygen;
    }

    @Override
    public boolean canBreath() {
        if (oxygen > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Bag getBag() {
        return bag;
    }

    @Override
    public void breath() {
        setOxygen(getOxygen() - 10);
        if (getOxygen() < 0) {
            setOxygen(0);
        }
    }

    @Override
    public String toString() {
        return String.format(ConstantMessages.REPORT_ASTRONAUT_NAME, this.name) + System.lineSeparator() +
               String.format(ConstantMessages.REPORT_ASTRONAUT_OXYGEN, this.oxygen) + System.lineSeparator() +
               String.format(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS, (this.bag.getItems().isEmpty() ? "none" :
                       String.join(ConstantMessages.REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, this.bag.getItems())));
    }
}
