package spaceStation.core;

import spaceStation.common.AstronautType;
import spaceStation.common.ConstantMessages;
import spaceStation.common.ExceptionMessages;
import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private final AstronautRepository astronautRepository;
    private final PlanetRepository planetRepository;
    private int exploredPlanetsCounter = 0;

    public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
    }

    @Override
    public String addAstronaut(String type, String astronautName) {

        if(Arrays.stream(AstronautType.values()).noneMatch(a -> a.name().equals(type))){
            throw new IllegalArgumentException(ExceptionMessages.ASTRONAUT_INVALID_TYPE);
        }
        AstronautType astronautType = AstronautType.valueOf(type);

        Astronaut astronaut = null;

        switch (astronautType){
            case Biologist:
                astronaut = new Biologist(astronautName);
                break;
            case Geodesist:
                astronaut = new Geodesist(astronautName);
                break;
            case Meteorologist:
                astronaut = new Meteorologist(astronautName);
                break;

        }
        astronautRepository.add(astronaut);
        return String.format(ConstantMessages.ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {

        Planet planet = new PlanetImpl(planetName);
        Arrays.stream(items).forEach(planet.getItems()::add);
        planetRepository.add(planet);

        return String.format(ConstantMessages.PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {

        Astronaut toBeRemoved = astronautRepository.findByName(astronautName);

        if (toBeRemoved == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }
        astronautRepository.remove(toBeRemoved);

        return String.format(ConstantMessages.ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        Planet toBeExplored = planetRepository.findByName(planetName);

        List<Astronaut> fitForExploration = astronautRepository.getModels().stream()
                .filter(a -> a.getOxygen() > 60).collect(Collectors.toList());

        if (fitForExploration.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }

        Mission mission = new MissionImpl();
        mission.explore(toBeExplored, fitForExploration);
        exploredPlanetsCounter++;

        List<Astronaut> deadAstronauts = fitForExploration.stream().filter(a -> a.getOxygen() == 0).collect(Collectors.toList());

        return String.format(ConstantMessages.PLANET_EXPLORED, planetName, deadAstronauts.size());
    }

    @Override
    public String report() {

        return String.format(ConstantMessages.REPORT_PLANET_EXPLORED, exploredPlanetsCounter) + System.lineSeparator() +
                ConstantMessages.REPORT_ASTRONAUT_INFO + System.lineSeparator() +
                astronautRepository.getModels().stream().map(Astronaut::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}
