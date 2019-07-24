package edu.xpu.game.service.shopping;

import edu.xpu.game.dto.OrderInfoDetailDTO;
import edu.xpu.game.entity.OrderDetail;
import edu.xpu.game.entity.OrderMaster;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName OrderService
 * @Description
 * @Date 2019-05-26 14:02
 * @Version 1.0
 */
public interface OrderService {

    OrderInfoDetailDTO createOrder(String userId, HttpServletRequest request);

    List<OrderMaster> showAllOrder();

    List<OrderDetail> showOrderDetail(String orderMasterId);

    List<OrderMaster> showAllOrderByStatus(Integer orderStatus);

    List<OrderMaster> showAllOrderByBuyerId(String buyerId);

    Boolean finishOrder(String orderMasterId);

    Optional<OrderMaster> findOrderById(String masterOrderId);

    OrderMaster change(OrderMaster orderMaster);

    OrderMaster cancelOrderForUser(String orderMasterId, String userId);

    OrderMaster cancelOrder(String orderMasterId, String userId);
}
