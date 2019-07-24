package edu.xpu.game.entity;

import edu.xpu.game.enums.OrderStatusEnum;
import edu.xpu.game.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName OrderMaster
 * @Description 订单描述类
 * @Date 2019-05-22 11:12
 * @Version 1.0
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;

    /**
     * 购买者Id
     */
    private String buyerId;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态：默认新下单
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();;

    /**
     * 支付状态：默认未支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单更新时间
     */
    private Date updateTime;
}
