package edu.xpu.game.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName OrderDetail
 * @Description 订单详情类
 * @Date 2019-05-22 11:18
 * @Version 1.0
 */
@Data
@Entity
@DynamicUpdate
public class OrderDetail {

    @Id
    private String detailId;

    /**
     * 总订单Id
     */
    private String orderId;

    /**
     * 商品Id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productNum;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
