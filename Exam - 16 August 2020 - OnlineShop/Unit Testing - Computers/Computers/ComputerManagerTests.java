package computers;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ComputerManagerTests {
    private static final String MANUFACTURER1 = "Asus";
    private static final String MANUFACTURER2 = "HP";
    private static final String MODEL1 = "ROG";
    private static final String MODEL2 = "Pavilion";
    private static final double PRICE1 = 100.00;
    private static final double PRICE2 = 200.00;

    private ComputerManager computerManager;
    private Computer asus;
    private Computer hp;

    @Before
    public void setUp() {
        this.computerManager = new ComputerManager();
        this.asus = new Computer(MANUFACTURER1, MODEL1, PRICE1);
        this.hp = new Computer(MANUFACTURER2, MODEL2, PRICE2);

        this.computerManager.addComputer(this.asus);
    }

    @Test
    public void testGetComputers() {
        Computer[] expected = {asus};
        Computer[] actual = computerManager.getComputers().toArray(Computer[]::new);
        assertArrayEquals(expected, actual);

    }

    @Test
    public void testGetCount() {
        assertEquals(1, computerManager.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddComputerThatAlreadyExistsThrows() {
        computerManager.addComputer(asus);
    }

    @Test
    public void TestAddComputerSuccessfully() {
        computerManager.addComputer(hp);
        assertEquals(2, computerManager.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullComputer(){
        computerManager.addComputer(null);
    }

    @Test
    public void testRemoveComputer() {
        computerManager.removeComputer(MANUFACTURER1, MODEL1);
        assertEquals(0, computerManager.getCount());
    }

    @Test
    public void testGetComputerSuccessfully(){
        Computer expected = asus;
        Computer actual = computerManager.getComputer(MANUFACTURER1, MODEL1);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerWithFalseManufacturerThrows(){
        assertNull(computerManager.getComputer(MANUFACTURER2, MODEL1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetComputerWithFalseModelThrows(){
        assertNull(computerManager.getComputer(MANUFACTURER1, MODEL2));
    }

    @Test
    public void testGetComputerByManufacturer(){
        Computer[] expected = {asus};
        Computer[] actual = computerManager.getComputersByManufacturer(MANUFACTURER1).toArray(Computer[]::new);
        assertArrayEquals(expected, actual);
    }

}