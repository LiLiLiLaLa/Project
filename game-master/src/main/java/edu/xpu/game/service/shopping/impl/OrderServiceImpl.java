package edu.xpu.game.service.shopping.impl;

import edu.xpu.game.dto.OrderInfoDTO;
import edu.xpu.game.dto.OrderInfoDetailDTO;
import edu.xpu.game.entity.OrderDetail;
import edu.xpu.game.entity.OrderMaster;
import edu.xpu.game.entity.ShoppingCart;
import edu.xpu.game.entity.UserInfo;
import edu.xpu.game.enums.OrderStatusEnum;
import edu.xpu.game.enums.PayStatusEnum;
import edu.xpu.game.repository.OrderDetailRepository;
import edu.xpu.game.repository.OrderMasterRepository;
import edu.xpu.game.repository.ShoppingCartRepository;
import edu.xpu.game.service.shopping.OrderService;
import edu.xpu.game.service.user.UserInfoService;
import edu.xpu.game.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ClassName OrderServiceImpl
 * @Description  订单管理的Service
 * @Date 2019-05-26 14:02
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderMasterRepository masterRepository;
    private final OrderDetailRepository detailRepository;
    private final ShoppingCartRepository shoppingRepository;
    private final ShoppingServiceImpl shoppingServiceImpl;
    private final UserInfoService userService;

    @Autowired
    public OrderServiceImpl(OrderMasterRepository masterRepository,
                            OrderDetailRepository detailRepository,
                            ShoppingCartRepository shoppingRepository,

                            ShoppingServiceImpl shoppingServiceImpl, UserInfoService userService) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
        this.shoppingRepository = shoppingRepository;
        this.shoppingServiceImpl = shoppingServiceImpl;
        this.userService = userService;
    }

    /**
     * 创建订单
     */
    @Override
    public OrderInfoDetailDTO createOrder(String userId, HttpServletRequest request) {
        OrderInfoDetailDTO retOrderInfoDetailDTO = new OrderInfoDetailDTO();

        OrderMaster orderMaster = new OrderMaster();
        String masterKey = KeyUtil.genUniqueKey();
        orderMaster.setOrderId(masterKey);
        orderMaster.setBuyerId(userId);
        orderMaster.setOrderAmount(shoppingServiceImpl.amount(userId));

        List<OrderInfoDTO> list = shoppingServiceImpl.list(userId);

        OrderDetail orderDetail = new OrderDetail();
        for(OrderInfoDTO orderInfoDTO: list){
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(masterKey);
            orderDetail.setProductNum(orderInfoDTO.getProductNum());
            orderDetail.setProductId(orderInfoDTO.getProductInfo().getProductId());
            detailRepository.save(orderDetail);
        }

        masterRepository.save(orderMaster);

        //清空购物车
        List<ShoppingCart> allByUserId = shoppingRepository.findAllByUserId(userId);
        for(ShoppingCart cart: allByUserId){
            shoppingRepository.deleteById(cart.getShoppingId());
        }

        retOrderInfoDetailDTO.setMasterOrderId(masterKey);
        retOrderInfoDetailDTO.setOrderInfoDTOList(list);
        retOrderInfoDetailDTO.setOrderPrice(orderMaster.getOrderAmount());
        return retOrderInfoDetailDTO;
    }

    @Override
    public List<OrderMaster> showAllOrder() {
        return masterRepository.findAll();
    }

    @Override
    public List<OrderDetail> showOrderDetail(String orderMasterId) {
        return detailRepository.findAllByOrderId(orderMasterId);
    }

    @Override
    public List<OrderMaster> showAllOrderByStatus(Integer orderStatus) {
        return masterRepository.findAllByOrderStatus(orderStatus);
    }

    @Override
    public List<OrderMaster> showAllOrderByBuyerId(String buyerId) {
        return masterRepository.findAllByBuyerId(buyerId);
    }

    @Override
    public Boolean finishOrder(String orderMasterId) {
        Optional<OrderMaster> byId = masterRepository.findById(orderMasterId);
        if(byId.isPresent()){
            OrderMaster orderMaster = byId.get();
            orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
            return masterRepository.save(orderMaster) != null;
        }
        return false;
    }

    @Override
    public Optional<OrderMaster> findOrderById(String masterOrderId) {
        return masterRepository.findById(masterOrderId);
    }

    @Override
    public OrderMaster change(OrderMaster orderMaster) {
        return masterRepository.save(orderMaster);
    }


    /**
     * 用户自己取消订单
     * @param orderMasterId 订单Id
     * @param userId 用户Id
     * @return 取消是否成功
     */
    @Override
    public OrderMaster cancelOrderForUser(String orderMasterId, String userId) {
        Optional<OrderMaster> byId = masterRepository.findById(orderMasterId);
        if(byId.isPresent()){
            OrderMaster master = byId.get();
            //看看是否是自己的订单
            if(master.getBuyerId().equalsIgnoreCase(userId)){
                Integer payStatus = master.getPayStatus();
                if(PayStatusEnum.SUCCESS.getCode().equals(payStatus)){
                    //在已经支付的情况下取消订单
                    master.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
                    //TODO 支付宝退款相关API
                }else{
                    //在未支付的情况下取消订单
                    master.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
                }
                return masterRepository.save(master);
            }
        }
        return null;
    }

    /**
     * 管理员取消订单的接口
     * @param orderMasterId 订单Id
     * @return 取消订单的结果
     */
    @Override
    public OrderMaster cancelOrder(String orderMasterId, String userId) {
        Optional<UserInfo> byId = userService.findById(userId);
        if(byId.isPresent() && (byId.get().getUserIsman() == 0)){
            Optional<OrderMaster> orderMaster = masterRepository.findById(orderMasterId);
            if(orderMaster.isPresent()){
                OrderMaster master = orderMaster.get();
                master.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
                return masterRepository.save(master);
            }
        }
        return null;
    }
}