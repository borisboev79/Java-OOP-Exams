package goldDigger.core;

import goldDigger.models.discoverer.Anthropologist;
import goldDigger.models.discoverer.Archaeologist;
import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.discoverer.Geologist;
import goldDigger.models.operation.Operation;
import goldDigger.models.operation.OperationImpl;
import goldDigger.models.spot.Spot;
import goldDigger.models.spot.SpotImpl;
import goldDigger.repositories.DiscovererRepository;
import goldDigger.repositories.Repository;
import goldDigger.repositories.SpotRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static goldDigger.common.ConstantMessages.*;
import static goldDigger.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private final Repository<Discoverer> discoverers;
    private final Repository<Spot> spots;
    private Operation operation;

    private int inspectedSpotsCount = 0;

    public ControllerImpl() {
        this.discoverers = new DiscovererRepository();
        this.spots = new SpotRepository();
        this.operation = new OperationImpl();
    }

    @Override
    public String addDiscoverer(String kind, String discovererName) {
        Discoverer discoverer;

        switch (kind) {
            case "Anthropologist":
                discoverer = new Anthropologist(discovererName);
                break;
            case "Archaeologist":
                discoverer = new Archaeologist(discovererName);
                break;
            case "Geologist":
                discoverer = new Geologist(discovererName);
                break;
            default:
                throw new IllegalArgumentException(DISCOVERER_INVALID_KIND);
        }
        discoverers.add(discoverer);
        return String.format(DISCOVERER_ADDED, kind, discovererName);
    }

    @Override
    public String addSpot(String spotName, String... exhibits) {
        Spot spotToAdd = new SpotImpl(spotName);
        List<String> exhibitList = Arrays.asList(exhibits);
        if (!exhibitList.isEmpty()) {
            spotToAdd.getExhibits().addAll(exhibitList);
        }
        this.spots.add(spotToAdd);
        return String.format(SPOT_ADDED, spotName);
    }

    @Override
    public String excludeDiscoverer(String discovererName) {
        Discoverer discoverer = discoverers.byName(discovererName);
        if (discoverer == null) {
            throw new IllegalArgumentException(String.format(DISCOVERER_DOES_NOT_EXIST, discovererName));
        }
        discoverers.remove(discoverer);
        return String.format(DISCOVERER_EXCLUDE, discovererName);
    }

    @Override
    public String inspectSpot(String spotName) {
        Spot spot = spots.byName(spotName);
        List<Discoverer> eligibleDiscoverers = discoverers.getCollection().stream().filter(d -> d.getEnergy() > 45).collect(Collectors.toList());
        if (eligibleDiscoverers.isEmpty()) {
            throw new IllegalArgumentException(SPOT_DISCOVERERS_DOES_NOT_EXISTS);
        }
        operation = new OperationImpl();
        operation.startOperation(spot, eligibleDiscoverers);
        inspectedSpotsCount++;
        return String.format(INSPECT_SPOT, spotName, (int) eligibleDiscoverers.stream().filter(d -> d.getEnergy() == 0).count());
    }

    @Override
    public String getStatistics() {
        return String.format(FINAL_SPOT_INSPECT, inspectedSpotsCount) + System.lineSeparator() +
                FINAL_DISCOVERER_INFO + System.lineSeparator() +
                discoverers.getCollection()
                        .stream()
                        .map(Discoverer::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
    }
}
