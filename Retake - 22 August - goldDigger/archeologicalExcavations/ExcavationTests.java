package archeologicalExcavations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExcavationTests {
    private static final String NAME1 = "Pesho";
    private static final String NAME2 = "Gosho";
    private static final double ENERGY1 = 10;
    private static final double ENERGY2 = 20;
    private static final int CAPACITY = 1;


    private Archaeologist pesho;
    private Archaeologist gosho;
    private Excavation excavation;

    @Before
    public void setUp(){
        this.pesho = new Archaeologist(NAME1, ENERGY1);
        this.gosho = new Archaeologist(NAME2, ENERGY2);
        this.excavation = new Excavation("Pit", CAPACITY);

        this.excavation.addArchaeologist(pesho);
    }

    @Test
    public void testGetCount(){
        Assert.assertEquals(1, excavation.getCount());
    }

    @Test
    public void testGetName(){
        Assert.assertEquals("Pit", excavation.getName());
    }

    @Test
    public void testGetCapacity(){
        Assert.assertEquals(CAPACITY, excavation.getCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCapacityOverflowThrows(){
        excavation.addArchaeologist(gosho);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidNameThrows(){
        Excavation test = new Excavation("Test", 2);
        Archaeologist ivan = new Archaeologist("Ivan", ENERGY2);
        excavation.addArchaeologist(ivan);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateNameThrows(){
        Excavation test = new Excavation("Test", 2);
        test.addArchaeologist(pesho);
        test.addArchaeologist(pesho);
    }

    @Test
    public void testAddNameSuccessfully(){
        Excavation test = new Excavation("Test", 2);
        Archaeologist ivan = new Archaeologist("Ivan", ENERGY2);
        test.addArchaeologist(ivan);
        test.addArchaeologist(pesho);
       Assert.assertEquals(2, test.getCount());
    }

    @Test
    public void testRemoveSuccessfully(){
        Assert.assertTrue(excavation.removeArchaeologist("Pesho"));
    }

    @Test
    public void testRemoveWrongNameFalse(){
        Assert.assertFalse(excavation.removeArchaeologist("Ivan"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCapacityNegativeThrows(){
        Excavation test = new Excavation("Test", -1);
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullName(){
        Excavation test = new Excavation(null, 2);
    }

    @Test(expected = NullPointerException.class)
    public void testAddEmptyName(){
        Excavation test = new Excavation("  ", 2);
    }





}
