package edu.xpu.game.repository;

import edu.xpu.game.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(String.valueOf(System.currentTimeMillis()-1000));
        orderMaster.setBuyerId("123456");
        orderMaster.setOrderAmount(new BigDecimal(120.5));
        OrderMaster save = repository.save(orderMaster);
        assertNotNull(save);

        OrderMaster orderMaster1 = new OrderMaster();
        orderMaster1.setOrderId(String.valueOf(System.currentTimeMillis()));
        orderMaster1.setBuyerId("123457");
        orderMaster1.setOrderAmount(new BigDecimal(220.5));
        OrderMaster save1 = repository.save(orderMaster1);
        assertNotNull(save1);

        OrderMaster orderMaster2 = new OrderMaster();
        orderMaster2.setOrderId(String.valueOf(System.currentTimeMillis()+1000));
        orderMaster2.setBuyerId("123456");
        orderMaster2.setOrderAmount(new BigDecimal(0.5));
        OrderMaster save2 = repository.save(orderMaster2);
        assertNotNull(save2);
    }


    @Test
    public void delete(){
        //repository.deleteById("1558579156961");
    }


    @Test
    public void update(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1558579155954");
        orderMaster.setBuyerId("123456");
        orderMaster.setOrderAmount(new BigDecimal(1000));
        OrderMaster save = repository.save(orderMaster);
        assertNotNull(save);
    }
}