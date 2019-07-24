package edu.xpu.game.repository;

import edu.xpu.game.entity.ShoppingCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository repository;

    @Test
    public void save(){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setShoppingId(String.valueOf(System.currentTimeMillis()));
        shoppingCart.setProductId("1558575338537");
        shoppingCart.setProductNum(20);
        shoppingCart.setUserId("000010");

        ShoppingCart save = repository.save(shoppingCart);
        assertNotNull(save);

        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setShoppingId(String.valueOf(System.currentTimeMillis()));
        shoppingCart1.setProductId("1558575338537");
        shoppingCart1.setProductNum(3);
        shoppingCart1.setUserId("000010");

        ShoppingCart save1 = repository.save(shoppingCart1);
        assertNotNull(save1);

        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setShoppingId(String.valueOf(System.currentTimeMillis()));
        shoppingCart2.setProductId("1558575338537");
        shoppingCart2.setProductNum(20);
        shoppingCart2.setUserId("00009");

        ShoppingCart save2 = repository.save(shoppingCart2);
        assertNotNull(save2);
    }


    @Test
    public void findAllByUserId(){
        List<ShoppingCart> list = repository.findAllByUserId("000010");
        assertEquals(list.size(), 2);
    }

}