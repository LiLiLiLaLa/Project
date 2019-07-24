package edu.xpu.game.vo;

import edu.xpu.game.enums.PickCheckStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @version 1.0
 * @className PickVO
 * @description
 * @date 2019-06-07 18:04
 */
@Data
public class PickVO {
    private String pickId;

    private String pickUser;

    private Date pickTime;

    private Integer pickCheck = PickCheckStatus.NEW.getCode();

    private String productId;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;
    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品Logo
     */
    private String productIcon;
}
