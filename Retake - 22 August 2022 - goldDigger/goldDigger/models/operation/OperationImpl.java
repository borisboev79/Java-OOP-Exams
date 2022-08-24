package goldDigger.models.operation;

import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.spot.Spot;

import java.util.ArrayDeque;
import java.util.Collection;

public class OperationImpl implements Operation {
    @Override
    public void startOperation(Spot spot, Collection<Discoverer> discoverers) {

        ArrayDeque<String> exhibits = new ArrayDeque<>(spot.getExhibits());

        for (Discoverer discoverer : discoverers) {
            while (discoverer.canDig() && !exhibits.isEmpty()) {
                String collectedExhibit = exhibits.poll();

                discoverer.getMuseum().getExhibits().add(collectedExhibit);
                discoverer.dig();
                if(!discoverer.canDig()){
                    break;
                }
                spot.getExhibits().remove(collectedExhibit);
            }
        }
    }
}

