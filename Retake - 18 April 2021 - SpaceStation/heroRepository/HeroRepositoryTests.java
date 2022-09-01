package heroRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HeroRepositoryTests {
    private static final String NAME1 = "Pesho";
    private static final String NAME2 = "Vanko";
    private static final int HERO1_LEVEL = 11;
    private static final int HERO2_LEVEL = 15;

    private static final int EXPECTED_COUNT = 1;
    private static final String SUCCESSFUL_ADDITION_MESSAGE = "Successfully added hero Vanko with level 15";

    private Hero hero1;
    private Hero hero2;
    private Hero nullHero;
    private HeroRepository repository;


    @Before
    public void setUp() {
        this.hero1 = new Hero(NAME1, HERO1_LEVEL);
        this.hero2 = new Hero(NAME2, HERO2_LEVEL);
        this.repository = new HeroRepository();

        repository.create(this.hero1);
    }

    @Test
    public void getCount(){
        assertEquals(EXPECTED_COUNT, repository.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void createHeroNull(){
       repository.create(nullHero);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createExistingHero(){
        repository.create(hero1);
    }

    @Test
    public void createHeroSuccessfully(){
        assertEquals(SUCCESSFUL_ADDITION_MESSAGE,repository.create(hero2));
        assertEquals(EXPECTED_COUNT + 1, repository.getCount());
    }

    @Test(expected = NullPointerException.class)
    public void removeNullHero() {
        repository.remove(nullHero.getName());
    }

    @Test(expected = NullPointerException.class)
    public void removeHeroWithNullNameThrowsException() {
        repository.remove(null);
    }

    @Test
    public void removeSuccessfully() {
        repository.create(hero2);
        assertEquals(EXPECTED_COUNT + 1, repository.getCount());
        assertTrue(repository.remove(hero2.getName()));
        assertEquals(EXPECTED_COUNT, repository.getCount());

    }

    @Test
    public void getHeroWithHighestLevel() {
        repository.create(hero2);
        assertEquals(hero2, repository.getHeroWithHighestLevel());
    }

    @Test
    public void getHeroWithHighestLevel_When_AllAreEven() {
        Hero hero3 = new Hero("Gosho", HERO1_LEVEL);
        repository.create(hero3);
        assertEquals(hero1, repository.getHeroWithHighestLevel());
    }

    @Test
    public void getHeroByName(){
        assertEquals(hero1, repository.getHero(NAME1));
    }

    @Test
    public void getNonExistentHeroByName(){
        assertNull(repository.getHero(NAME2));
    }

    @Test
    public void getAllHeroes() {
        repository.create(hero2);
        List<Hero> expected = new ArrayList<>();
        expected.add(hero1);
        expected.add(hero2);
        List<Hero> actual = new ArrayList<>(repository.getHeroes());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }
}
