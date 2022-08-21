package halfLife;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTests {
    private static final String NAME1 = "Walter";
    private static final String NAME2 = "Berreta";
    private static final int BULLETS1 = 9;
    private static final int BULLETS2 = 11;
    private static final String PLAYER1 = "Pesho";
    private static final int HEALTH1 = 100;
    private static final int DAMAGE = 90;

    private Gun walter;
    private Gun berreta;
    private Player player;

    @Before
    public void setUp() {
        this.walter = new Gun(NAME1, BULLETS1);
        this.berreta = new Gun(NAME2, BULLETS2);
        this.player = new Player(PLAYER1, HEALTH1);

        this.player.addGun(this.walter);
    }

    @Test
    public void testGetUsername() {
        assertEquals("Pesho", player.getUsername());
    }

    @Test(expected = NullPointerException.class)
    public void testSetUserNameNullThrows(){
        Player player = new Player(null, 199);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUserHealthNegativeThrows(){
        Player player = new Player("Vanko", -1);
    }

    @Test
    public void testSetUserHealthCorrectly(){
        Player player2 = new Player("Vanko", 1);
        assertEquals(1, player2.getHealth());
    }

    @Test
    public void testGetGunsCollection(){
        player.addGun(berreta);
        Gun[] expected = {walter, berreta};
        Gun[] actual = player.getGuns().toArray(new Gun[0]);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testTakeDamage(){
        player.takeDamage(DAMAGE);
        assertEquals(10, player.getHealth());
    }

    @Test(expected = IllegalStateException.class)
    public void testTakeDamageRendersHealthBelowZeroThrows(){
        player.takeDamage(DAMAGE + 10);
        player.takeDamage(DAMAGE);
    }

    @Test
    public void testGetHealth_EqualsZero_AfterDamageThatMakesHealthNegative(){
        player.takeDamage(DAMAGE + 11);
        assertEquals(0, player.getHealth());
    }

    @Test
    public void testGetHealth_EqualsZero_AfterDamageThatMakesHealthZero(){
        player.takeDamage(DAMAGE + 10);
        assertEquals(0, player.getHealth());
    }

    @Test(expected = NullPointerException.class)
    public void testAddGun_Null_Throws(){
        player.addGun(null);
    }

    @Test
    public void testRemoveGun_Successfully(){
        player.removeGun(walter);
        assertNull(player.getGun("Walter"));
    }

    @Test
    public void testGetGunByName_Successfully(){
        assertEquals(walter, player.getGun("Walter"));
    }
}
