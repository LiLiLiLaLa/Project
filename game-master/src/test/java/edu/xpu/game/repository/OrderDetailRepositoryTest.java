package edu.xpu.game.repository;

import edu.xpu.game.entity.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1558575338397");
        orderDetail.setProductNum(2);
        orderDetail.setOrderId("1558579155954");
        orderDetail.setDetailId(String.valueOf(System.currentTimeMillis()));
        OrderDetail save = repository.save(orderDetail);
        assertNotNull(save);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("1558575338397");
        orderDetail1.setProductNum(5);
        orderDetail1.setOrderId("1558579155954");
        orderDetail1.setDetailId(String.valueOf(System.currentTimeMillis()));
        OrderDetail save1 = repository.save(orderDetail1);
        assertNotNull(save1);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("1558575338397");
        orderDetail2.setProductNum(2);
        orderDetail2.setOrderId("1558579155954");
        orderDetail2.setDetailId(String.valueOf(System.currentTimeMillis()));
        OrderDetail save2 = repository.save(orderDetail2);
        assertNotNull(save2);
    }

    @Test
    public void other(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1558575338397");
        orderDetail.setProductNum(200);
        orderDetail.setOrderId("1558579155954");
        orderDetail.setDetailId("1558579853862");
        OrderDetail save = repository.save(orderDetail);
        assertNotNull(save);

        List<OrderDetail> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    public void delete(){
        repository.deleteById("1558579854029");
    }

}