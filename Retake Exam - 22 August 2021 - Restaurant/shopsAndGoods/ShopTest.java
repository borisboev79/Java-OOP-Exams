package shopAndGoods;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.*;

public class ShopTest {
    public static final String GOODS_NAME = "Sneakers";
    public static final String GOODS2_NAME = "Sandals";
    public static final String GOODS_CODE = "SGC231";
    public static final String GOODS2_CODE = "BGR789";

    public static final String VALID_SHELF1 = "Shelves1";
    public static final String VALID_SHELF2 = "Shelves2";
    public static final String INVALID_SHELF = "Shelves15";

    public static final String SUCCESSFUL_ADDITION = "Goods: BGR789 is placed successfully!";
    public static final String SUCCESSFUL_REMOVAL = "Goods: SGC231 is removed successfully!";

    private Shop shop;
    private Goods goods1;
    private Goods goods2;

    @Before
    public void setUp() throws OperationNotSupportedException {
        this.shop = new Shop();
        this.goods1 = new Goods(GOODS_NAME, GOODS_CODE);
        this.goods2 = new Goods(GOODS2_NAME, GOODS2_CODE);

        shop.addGoods(VALID_SHELF1, goods1);
    }

    @Test
    public void addShelvesSuccessfully() throws OperationNotSupportedException {
//        this.shop.addGoods(VALID_SHELF2, goods2);
//        assertEquals(goods2.getName(), shop.getShelves().get(VALID_SHELF2).getName());
//        assertEquals(goods2.getGoodsCode(), shop.getShelves().get(VALID_SHELF2).getGoodsCode());
        assertEquals(SUCCESSFUL_ADDITION, this.shop.addGoods(VALID_SHELF2, goods2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addShelvesThrowsExceptionNoSuchShelf() throws OperationNotSupportedException {
        this.shop.addGoods(INVALID_SHELF, goods2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addShelvesThrowsExceptionShelfOccupied() throws OperationNotSupportedException {
        this.shop.addGoods(VALID_SHELF1, goods2);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void addShelvesThrowsExceptionGoodsAlreadyThere() throws OperationNotSupportedException {
        this.shop.addGoods(VALID_SHELF2, goods1);
    }

    @Test
    public void removeGoodsSuccessfully() {
//        shop.removeGoods(VALID_SHELF1, goods1);
//        assertFalse(shop.getShelves().containsValue(goods1));
        assertEquals(SUCCESSFUL_REMOVAL, shop.removeGoods(VALID_SHELF1, goods1));
        assertNull(shop.getShelves().get(VALID_SHELF1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeGoodsThrowsNoSuchGoods() {
        shop.removeGoods(VALID_SHELF1, goods2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeGoodsThrowsNoSuchShelf() {
        shop.removeGoods(INVALID_SHELF, goods1);
    }

}