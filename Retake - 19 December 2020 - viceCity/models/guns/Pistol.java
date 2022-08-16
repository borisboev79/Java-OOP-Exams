package viceCity.models.guns;

public class Pistol extends BaseGun{
    private static final int BULLETS_PER_BARREL = 10;
    private static final int TOTAL_BULLETS = 100;
    private static final int COUNT_OF_FIRED_BULLETS = 1;

    public Pistol(String name) {
        super(name, BULLETS_PER_BARREL, TOTAL_BULLETS);
    }

    @Override
    public int fire() {

        setBulletsPerBarrel(getBulletsPerBarrel() - COUNT_OF_FIRED_BULLETS);

         if (getBulletsPerBarrel() == 0 && getTotalBullets() - BULLETS_PER_BARREL >= 0 ) {
                setBulletsPerBarrel(BULLETS_PER_BARREL);
                setTotalBullets(getTotalBullets() - BULLETS_PER_BARREL);
            } else {
               setCanFire(false);
            }

        return COUNT_OF_FIRED_BULLETS;
    }
}
