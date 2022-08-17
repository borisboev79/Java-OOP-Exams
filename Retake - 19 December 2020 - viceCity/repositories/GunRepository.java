package viceCity.repositories.interfaces;

import viceCity.models.guns.Gun;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

public class GunRepository implements Repository<Gun>{
    private final ArrayDeque<Gun> models;

    public GunRepository() {
        this.models = new ArrayDeque<>();
    }

    @Override
    public ArrayDeque<Gun> getModels() {
        return models;
    }

    @Override
    public void add(Gun model) {
        models.offer(model);

    }

    @Override
    public boolean remove(Gun model) {
        if(models.isEmpty()) {
            return false;
        }
        Gun toBeRemoved = models.poll();
        return true;
    }


    @Override
    public Gun find(String name) {
        return models.stream().filter(g -> g.getName().equals(name)).findFirst().orElse(null);
    }
}
