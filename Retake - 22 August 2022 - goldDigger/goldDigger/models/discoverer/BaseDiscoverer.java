package goldDigger.models.discoverer;

import goldDigger.common.ConstantMessages;
import goldDigger.common.ExceptionMessages;
import goldDigger.models.museum.BaseMuseum;
import goldDigger.models.museum.Museum;

import java.util.stream.Collectors;

import static goldDigger.common.ConstantMessages.*;
import static goldDigger.common.ExceptionMessages.*;

public abstract class BaseDiscoverer implements Discoverer{
    private String name;
    private double energy;
    private final Museum museum;

    public BaseDiscoverer(String name, double energy) {
        setName(name);
        setEnergy(energy);
        this.museum = new BaseMuseum();
    }

    protected void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(DISCOVERER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    protected void setEnergy(double energy) {
        if (energy < 0) {
            throw new IllegalArgumentException(DISCOVERER_ENERGY_LESS_THAN_ZERO);
        }
        this.energy = energy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public boolean canDig() {
        return energy > 0;
    }

    @Override
    public Museum getMuseum() {
        return museum;
    }

    @Override
    public void dig() {
        energy = Math.max(0, getEnergy() - 15);
        }

    @Override
    public String toString() {
        return String.format(FINAL_DISCOVERER_NAME, this.name) + System.lineSeparator() +
                String.format(FINAL_DISCOVERER_ENERGY, this.energy) + System.lineSeparator() +
                String.format(FINAL_DISCOVERER_MUSEUM_EXHIBITS, (this.museum.getExhibits().isEmpty() ? "None" :
                        this.museum.getExhibits().stream().collect(Collectors.joining(FINAL_DISCOVERER_MUSEUM_EXHIBITS_DELIMITER))));
    }
}
