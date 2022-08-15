package viceCity.core;

import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.*;
import java.util.stream.Collectors;

import static viceCity.common.ConstantMessages.*;

public class ControllerImpl implements Controller {
    private final Collection<Player> civilPlayers;
    private final Player mainPlayer;
    private final Deque<Gun> guns;
    private final Neighbourhood gangNeighbourhood;

    public ControllerImpl() {
        this.civilPlayers = new ArrayList<>();
        this.mainPlayer = new MainPlayer();
        this.guns = new ArrayDeque<Gun>();
        this.gangNeighbourhood = new GangNeighbourhood();
    }

    @Override
    public String addPlayer(String name) {
        Player toBeAdded = new CivilPlayer(name);
        civilPlayers.add(toBeAdded);
        return String.format(PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun;
        switch (type) {
            case "Pistol":
                gun = new Pistol(name);
                break;
            case "Rifle":
                gun = new Rifle(name);
                break;
            default:
                return GUN_TYPE_INVALID;
        }
        guns.offer(gun);
        return String.format(GUN_ADDED, name, type);
    }

    @Override
    public String addGunToPlayer(String name) {

        Gun gunToAdd = guns.peek();
        Player targetPlayer;

        if (gunToAdd == null) {
            return GUN_QUEUE_IS_EMPTY;
        }
        if (name.equals("Vercetti")) {
            mainPlayer.getGunRepository().add(gunToAdd);
            guns.poll();
            return String.format(GUN_ADDED_TO_MAIN_PLAYER, gunToAdd.getName(), mainPlayer.getName());
        }
        targetPlayer = civilPlayers.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
        if (targetPlayer == null) {
            return CIVIL_PLAYER_DOES_NOT_EXIST;
        }
        targetPlayer.getGunRepository().add(gunToAdd);
        guns.poll();
        return String.format(GUN_ADDED_TO_CIVIL_PLAYER, gunToAdd.getName(), targetPlayer.getName());
    }


    @Override
    public String fight() {
        gangNeighbourhood.action(mainPlayer, civilPlayers);
        List<Player> alivePlayers = civilPlayers.stream().filter(Player::isAlive).collect(Collectors.toList());
        List<Player> deadPlayers = civilPlayers.stream().filter(p -> !p.isAlive()).collect(Collectors.toList());

        if (mainPlayer.isAlive() && deadPlayers.isEmpty()) {
            return FIGHT_HOT_HAPPENED;
        }
        return FIGHT_HAPPENED + System.lineSeparator() +
                String.format(MAIN_PLAYER_LIVE_POINTS_MESSAGE, mainPlayer.getLifePoints()) + System.lineSeparator() +
                String.format(MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, deadPlayers.size()) + System.lineSeparator() +
                String.format(CIVIL_PLAYERS_LEFT_MESSAGE, alivePlayers.size());
    }
}
