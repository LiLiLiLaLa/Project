package edu.xpu.game.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName ShoppingCart
 * @Description 购物车条目
 * @Date 2019-05-25 15:11
 * @Version 1.0
 */
@Entity
@Data
public class ShoppingCart {
    @Id
    private String shoppingId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 商品Id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productNum;
}
