package xpu.edu.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 主订单
 */
@Data
public class OrderMaster {
    //订单ID
    private String order_id;
    //订单总价
    private BigDecimal order_price;
    //买家ID
    private String user_id;
}
