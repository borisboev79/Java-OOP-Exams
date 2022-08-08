package CounterStriker.models.field;

import static CounterStriker.common.OutputMessages.*;

import CounterStriker.models.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldImpl implements Field {

    @Override
    public String start(Collection<Player> players) {
        List<Player> terrorists = players.stream().filter(p -> p.getClass().getSimpleName().equals("Terrorist")).collect(Collectors.toList());
        List<Player> counterTerrorists = players.stream().filter(p -> p.getClass().getSimpleName().equals("CounterTerrorist")).collect(Collectors.toList());

        while (!terrorists.isEmpty() && !counterTerrorists.isEmpty()) {
            //FIRST Terrorists attack
            fight(terrorists, counterTerrorists);
            //THEN CounterTerrorists attack
            fight(counterTerrorists, terrorists);
        }
        if (counterTerrorists.isEmpty()) {
            return TERRORIST_WINS;
        }
        return COUNTER_TERRORIST_WINS;
    }

    private void fight(List<Player> attackers, List<Player> defenders) {

        for (Player attacker : attackers) {
            for (Player defender : defenders) {
                if (attacker.getGun().getBulletsCount() > 0) {
                    defender.takeDamage(attacker.getGun().fire());
                    if (!defender.isAlive()) {
                        defenders.remove(defender);
                        break;
                    }
                }
            }
        }
    }
}

