package CounterStriker.core;

import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static CounterStriker.common.ExceptionMessages.*;
import static CounterStriker.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private final GunRepository guns;
    private final PlayerRepository players;
    private final Field field;

    public ControllerImpl() {
        this.guns = new GunRepository();
        this.players = new PlayerRepository();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun;
        switch (type) {
            case "Pistol":
                gun = new Pistol(name, bulletsCount);
                break;
            case "Rifle":
                gun = new Rifle(name, bulletsCount);
                break;
            default:
                throw new IllegalArgumentException(INVALID_GUN_TYPE);
        }
        guns.add(gun);
        return String.format(SUCCESSFULLY_ADDED_GUN, name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        Gun gun = guns.findByName(gunName);
        if (gun == null) {
            throw new NullPointerException(GUN_CANNOT_BE_FOUND);
        }
        Player player;
        switch (type) {
            case "Terrorist":
                player = new Terrorist(username, health, armor, gun);
                break;
            case "CounterTerrorist":
                player = new CounterTerrorist(username, health, armor, gun);
                break;
            default:
                throw new IllegalArgumentException(INVALID_PLAYER_TYPE);
        }
        players.add(player);

        return String.format(SUCCESSFULLY_ADDED_PLAYER, username);
    }

    @Override
    public String startGame() {
        List<Player> alivePlayers = players.getModels().stream().filter(Player::isAlive).collect(Collectors.toList());
        return field.start(alivePlayers);
    }

    @Override
    public String report() {
        return playersSortedByType("CounterTerrorist") + System.lineSeparator() +
                playersSortedByType("Terrorist");
    }

    private String playersSortedByType(String type) {
        return players.getModels().stream().filter(p -> p.getClass().getSimpleName().equals(type))
                .sorted(Comparator.comparing(Player::getHealth).reversed()
                        .thenComparing(Player::getUsername))
                .map(Player::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}
