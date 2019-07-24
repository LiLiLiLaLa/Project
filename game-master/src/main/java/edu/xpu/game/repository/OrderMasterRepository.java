package edu.xpu.game.repository;

import edu.xpu.game.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    List<OrderMaster> findAllByOrderStatus(Integer orderStatus);
    List<OrderMaster> findAllByBuyerId(String buyerId);
}
