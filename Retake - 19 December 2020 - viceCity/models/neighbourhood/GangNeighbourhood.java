package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GangNeighbourhood implements Neighbourhood {

    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        List<Gun> mainPlayerGuns = mainPlayer.getGunRepository().getModels().stream().filter(Gun::canFire).collect(Collectors.toList());
        List<Player> alivePlayers = civilPlayers.stream().filter(Player::isAlive).collect(Collectors.toList());

        if (!mainPlayerGuns.isEmpty()) {
            for (Gun gun : mainPlayerGuns) {
                while (gun.canFire() && !alivePlayers.isEmpty()) {
                    for (Player player : alivePlayers) {
                        while (player.isAlive() && gun.canFire()) {
                            player.takeLifePoints(gun.fire());
                        }
                        alivePlayers = civilPlayers.stream().filter(Player::isAlive).collect(Collectors.toList());
                    }
                }
            }
        }

        for (Player player : alivePlayers) {
            List<Gun> playerGuns = player.getGunRepository().getModels().stream().filter(Gun::canFire).collect(Collectors.toList());

            for (Gun gun : playerGuns) {
                while (mainPlayer.isAlive() && gun.canFire()) {
                    mainPlayer.takeLifePoints(gun.fire());
                }
            }
        }
    }
}





