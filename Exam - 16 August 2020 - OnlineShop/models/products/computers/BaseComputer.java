package onlineShop.models.products.computers;

import onlineShop.models.products.BaseProduct;
import onlineShop.models.products.Product;
import onlineShop.models.products.components.Component;
import onlineShop.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.*;

public abstract class BaseComputer extends BaseProduct implements Computer {
    private List<Component> components;
    private List<Peripheral> peripherals;


    public BaseComputer(int id, String manufacturer, String model, double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }

    @Override
    public double getOverallPerformance() {

        if (components.isEmpty()) {
            return super.getOverallPerformance();
        } else {
            return super.getOverallPerformance() + getAvgComponentPerformance();
        }
    }

    @Override
    public double getPrice() {
        return super.getPrice() + getSumComponentsPrice();
    }


    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return peripherals;
    }

    @Override
    public void addComponent(Component component) {
        Component toBeAdded = components.stream().filter(c -> c.getClass().getSimpleName().equals(component.getClass().getSimpleName()))
                .findFirst().orElse(null);
        if (toBeAdded != null) {
//            throw new IllegalArgumentException(String.format(EXISTING_COMPONENT, component.getClass().getSimpleName(),
//                    getClass().getSimpleName(), getId()));
            throw new IllegalArgumentException(EXISTING_COMPONENT_ID);
        }
        components.add(component);

    }

    @Override
    public Component removeComponent(String componentType) {
        Component toBeRemoved = components.stream().filter(c -> c.getClass().getSimpleName().equals(componentType))
                .findFirst().orElse(null);
        if (toBeRemoved == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_COMPONENT, componentType, getClass().getSimpleName(), getId()));
        }
        components.remove(toBeRemoved);
        return toBeRemoved;
    }

    @Override
    public void addPeripheral(Peripheral peripheral) {
        Peripheral toBeAdded = peripherals.stream().filter(p -> p.getClass().getSimpleName().equals(peripheral.getClass().getSimpleName()))
                .findFirst().orElse(null);
        if (toBeAdded != null) {
//            throw new IllegalArgumentException(String.format(EXISTING_PERIPHERAL, peripheral.getClass().getSimpleName(), getClass().getSimpleName(), getId()));

            throw new IllegalArgumentException(EXISTING_PERIPHERAL_ID);
        }
        peripherals.add(peripheral);

    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        Peripheral toBeRemoved = peripherals.stream().filter(p -> p.getClass().getSimpleName().equals(peripheralType))
                .findFirst().orElse(null);
        if (toBeRemoved == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_PERIPHERAL, peripheralType, getClass().getSimpleName(), getId()));
        }
        peripherals.remove(toBeRemoved);
        return toBeRemoved;
    }

    @Override
    public String toString() {

        return String.format(PRODUCT_TO_STRING, getOverallPerformance(), getPrice(), getClass().getSimpleName(), getManufacturer(),
                getModel(), getId()) + System.lineSeparator() + " " + String.format(COMPUTER_COMPONENTS_TO_STRING, components.size()) + System.lineSeparator() +
                ((components.size() == 0) ? "" :
                        "  " + components.stream().map(Component::toString).collect(Collectors.joining(System.lineSeparator() + "  ")) + System.lineSeparator()) +
                " " + String.format(COMPUTER_PERIPHERALS_TO_STRING, peripherals.size(), getAvgPeripheralPerformance()) +
                ((peripherals.size() == 0) ? "" : System.lineSeparator() + "  " + peripherals.stream().map(Peripheral::toString)
                        .collect(Collectors.joining(System.lineSeparator() + "  ")));


//        return super.toString() + System.lineSeparator() +
//                String.format(COMPUTER_COMPONENTS_TO_STRING, components.size()) + System.lineSeparator() +
//                components.stream()
//                        .map(Component::toString)
//                        .collect(Collectors.joining("  " + System.lineSeparator())) + System.lineSeparator() +
//                String.format(COMPUTER_PERIPHERALS_TO_STRING, peripherals.size(), getAvgPeripheralPerformance()) + System.lineSeparator() +
//                peripherals.stream()
//                        .map(Peripheral::toString)
//                        .collect(Collectors.joining("  " + System.lineSeparator()));
    }

    private double getAvgComponentPerformance() {
        return components.stream()
                .mapToDouble(Product::getOverallPerformance)
                .average()
                .orElse(0.00);
    }

    private double getAvgPeripheralPerformance() {
        return peripherals.stream()
                .mapToDouble(Peripheral::getOverallPerformance)
                .average()
                .orElse(0.00);
    }

    private double getSumComponentsPrice() {
        return components.stream().mapToDouble(Component::getPrice).sum()
                + peripherals.stream().mapToDouble(Peripheral::getPrice).sum();
    }
}
