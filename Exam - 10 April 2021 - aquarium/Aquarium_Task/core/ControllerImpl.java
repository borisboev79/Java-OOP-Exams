package aquarium.core;

import aquarium.common.ConstantMessages;
import aquarium.common.ExceptionMessages;
import aquarium.entities.aquariums.Aquarium;
import aquarium.entities.aquariums.FreshwaterAquarium;
import aquarium.entities.aquariums.SaltwaterAquarium;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.decorations.Ornament;
import aquarium.entities.decorations.Plant;
import aquarium.entities.fish.Fish;
import aquarium.entities.fish.FreshwaterFish;
import aquarium.entities.fish.SaltwaterFish;
import aquarium.repositories.DecorationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private final DecorationRepository decorationRepository;
    private final Collection<Aquarium> aquariums;

    public ControllerImpl() {
        this.decorationRepository = new DecorationRepository();
        this.aquariums = new ArrayList<>();
    }

    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        Aquarium aquarium;

        switch (aquariumType) {
            case "FreshwaterAquarium":
                aquarium = new FreshwaterAquarium(aquariumName);
                break;
            case "SaltwaterAquarium":
                aquarium = new SaltwaterAquarium(aquariumName);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_AQUARIUM_TYPE);
        }
        aquariums.add(aquarium);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_AQUARIUM_TYPE, aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        Decoration decoration;

        switch (type) {
            case "Ornament":
                decoration = new Ornament();
                break;
            case "Plant":
                decoration = new Plant();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_DECORATION_TYPE);
        }
        decorationRepository.add(decoration);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_TYPE, type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        Aquarium targetAquarium = getAquarium(aquariumName);
        Decoration targetDecoration = decorationRepository.findByType(decorationType);

        if (targetDecoration == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_DECORATION_FOUND, decorationType));
        } else {
            assert targetAquarium != null;
            targetAquarium.addDecoration(targetDecoration);
            decorationRepository.remove(targetDecoration);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM, decorationType, aquariumName);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        Fish fish;

        switch(fishType){
            case"FreshwaterFish":
                fish = new FreshwaterFish(fishName, fishSpecies, price);
                break;
            case"SaltwaterFish":
                fish = new SaltwaterFish(fishName, fishSpecies, price);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_FISH_TYPE);
        }
        Aquarium targetAquarium = getAquarium(aquariumName);

        if((targetAquarium.getClass().getSimpleName().equals("FreshwaterAquarium") && fishType.equals("SaltwaterFish")) ||
                (targetAquarium.getClass().getSimpleName().equals("SaltwaterAquarium") && fishType.equals("FreshwaterFish"))) {
            return ConstantMessages.WATER_NOT_SUITABLE;

        }


        targetAquarium.addFish(fish);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM, fishType, aquariumName);
    }

    @Override
    public String feedFish(String aquariumName) {
        Aquarium targetAquarium = getAquarium(aquariumName);
        targetAquarium.getFish().forEach(Fish::eat);

        return String.format(ConstantMessages.FISH_FED, targetAquarium.getFish().size());

    }

    @Override
    public String calculateValue(String aquariumName) {
        Aquarium targetAquarium = getAquarium(aquariumName);
        double totalFishValue = targetAquarium.getFish().stream().mapToDouble(Fish::getPrice).sum();
        double totalDecorationValue = targetAquarium.getDecorations().stream().mapToDouble(Decoration::getPrice).sum();

        return String.format(ConstantMessages.VALUE_AQUARIUM, aquariumName, totalFishValue + totalDecorationValue);
    }

    @Override
    public String report() {
        return aquariums.stream().map(Aquarium::getInfo).collect(Collectors.joining(System.lineSeparator()));
    }

    private Aquarium getAquarium(String aquariumName) {
        return aquariums.stream().filter(a -> a.getName().equals(aquariumName)).findFirst().orElse(null);
    }
}
