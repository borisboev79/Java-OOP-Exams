package CounterStriker.repositories;

import CounterStriker.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;

import static CounterStriker.common.ExceptionMessages.INVALID_PLAYER_REPOSITORY;

public class PlayerRepository implements Repository<Player> {
    private final Collection<Player> models;

    public PlayerRepository() {
        this.models = new ArrayList<>();
    }

    @Override
    public Collection<Player> getModels() {
        return models;
    }

    @Override
    public void add(Player model) {
        if (model == null) {
            throw new NullPointerException(INVALID_PLAYER_REPOSITORY);
        }
        models.add(model);
    }

    @Override
    public boolean remove(Player model) {
        return models.remove(model);
    }

    @Override
    public Player findByName(String name) {
        return models.stream().filter(m -> m.getUsername().equals(name)).findFirst().orElse(null);
    }
}
