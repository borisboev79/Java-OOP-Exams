package aquarium;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AquariumTests {
    private static final String FISH1_NAME = "Pesho";
    private static final String FISH2_NAME = "Vanko";
    private static final int CAPACITY = 2;
    private static final int CHANGE_CAPACITY = 3;
    private static final int NEGATIVE_CAPACITY = -2;
    private static final String AQUARIUM1_NAME = "Ralevitsa";
    private static final String AQUARIUM2_NAME = "Penka";
    private static final String REPORT_MESSAGE = "Fish available at Ralevitsa: Pesho, Vanko";

    Aquarium aquarium;
    Fish fish1;
    Fish fish2;

    @Before
    public void setUp() {
        this.aquarium = new Aquarium(AQUARIUM1_NAME, CAPACITY);
        this.fish1 = new Fish(FISH1_NAME);
        this.fish2 = new Fish(FISH2_NAME);


        this.aquarium.add(fish1);
    }

    @Test
    public void getAquariumName() {
        assertEquals(AQUARIUM1_NAME, aquarium.getName());
    }

    @Test
    public void setAquariumNameSuccessfully() {
        Aquarium aquarium = new Aquarium(AQUARIUM2_NAME, CAPACITY);
        assertEquals(AQUARIUM2_NAME, aquarium.getName());
    }

    @Test(expected = NullPointerException.class)
    public void setNullNameThrowsException() {
        Aquarium aquarium = new Aquarium(null, CAPACITY);
    }

    @Test(expected = NullPointerException.class)
    public void setEmptyNameThrowsException() {
        Aquarium aquarium = new Aquarium("  ", CAPACITY);
    }

    @Test
    public void getCapacitySuccessfully() {
        assertEquals(CAPACITY, aquarium.getCapacity());
    }

    @Test
    public void setCapacitySuccessfuly() {
        Aquarium aquarium = new Aquarium(AQUARIUM2_NAME, CHANGE_CAPACITY);
        assertEquals(CHANGE_CAPACITY, aquarium.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCapacityNegativeThrowsException() {
        Aquarium aquarium = new Aquarium(AQUARIUM2_NAME, NEGATIVE_CAPACITY);
    }

    @Test
    public void getFishCount() {
        assertEquals(1, aquarium.getCount());
    }

    @Test
    public void addFishToAquariumSuccessfully() {
        assertEquals(1, aquarium.getCount());
        aquarium.add(fish2);
        assertEquals(2, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addFishToFullAquariumThrowsException() {
        aquarium.add(fish2);
        assertEquals(2, aquarium.getCount());
        aquarium.add(fish1);
    }

    @Test
    public void removeFishSuccessfully() {
        aquarium.remove(FISH1_NAME);
        assertEquals(0, aquarium.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeFishFromEmptyAquariumThrows() {
        aquarium.remove(FISH2_NAME);
    }

    @Test
    public void sellFishSuccessfully() {
        Fish fishToSell = aquarium.sellFish(FISH1_NAME);
        assertEquals(fish1, fishToSell);
        assertFalse(fish1.isAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void sellNonExistentFish() {
        aquarium.sellFish(FISH2_NAME);
    }

    @Test
    public void testReport() {
        aquarium.add(fish2);
        assertEquals(REPORT_MESSAGE, aquarium.report());

    }

}

