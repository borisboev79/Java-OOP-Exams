package restaurant.entities.healthyFoods;

import restaurant.common.enums.HealthyFoodType;

public class Salad extends Food{
    private static final double InitialSaladPortion = 150;
    private static final HealthyFoodType type = HealthyFoodType.Salad;

    public Salad(String name, double price) {
        super(name, InitialSaladPortion, price);
    }
}
