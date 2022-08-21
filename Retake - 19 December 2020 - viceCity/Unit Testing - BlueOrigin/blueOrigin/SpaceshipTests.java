package blueOrigin;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceshipTests {
    private static final String NAME1 = "Pesho";
    private static final String NAME2 = "Vanko";
    private static final String SPACESHIP = "Rosinante";
    private static final int CAPACITY = 2;
    private static final double OXYGEN1 = 50.0;
    private static final double OXYGEN2 = 80.0;

    Astronaut pesho;
    Astronaut vanko;
    Spaceship spaceship;

    @Before
    public void setUp(){
    this.pesho = new Astronaut(NAME1, OXYGEN1);
    this.vanko = new Astronaut(NAME2, OXYGEN2);
    this.spaceship = new Spaceship(SPACESHIP, CAPACITY);

    this.spaceship.add(this.pesho);
    }

    @Test
    public void testGetCount() {
        assertEquals(1, spaceship.getCount());
        spaceship.add(vanko);
        assertEquals(2, spaceship.getCount());
    }

    @Test
    public void testGetShipName(){
        assertEquals(SPACESHIP, spaceship.getName());
    }

    @Test
    public void testGetShipCapacity(){
        assertEquals(CAPACITY, spaceship.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAstronautToFullSpaceshipThrows(){
        spaceship.add(vanko);
        spaceship.add(new Astronaut("Gosho", OXYGEN2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddExistingAstronautThrows() {
        spaceship.add(pesho);
    }

    @Test
    public void testAddAstronautSuccessfullyToSpaceship(){
        spaceship.add(vanko);
        assertEquals(2, spaceship.getCount());
    }

    @Test
    public void testRemoveExistingAstronautFromShip(){
        assertTrue(spaceship.remove(NAME1));
        assertEquals(0, spaceship.getCount());
    }

    @Test
    public void testRemoveNonExistentAstronautFromShip(){
        assertFalse(spaceship.remove(NAME2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetZeroCapacityThrows(){
        Spaceship spaceship2  = new Spaceship("Ariana", -1);
    }

    @Test
    public void testSetCapacity(){
        Spaceship spaceship2  = new Spaceship("Ariana", 10);
    }

    @Test(expected = NullPointerException.class)
    public void testSetEmptyShipName(){
        Spaceship spaceship2  = new Spaceship(" ", 10);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNullShipName(){
        Spaceship spaceship2  = new Spaceship(null, 10);
    }

    @Test
    public void testSetShipNameSuccessfully(){
        Spaceship spaceship2  = new Spaceship("Ariana", 10);
    }

}
