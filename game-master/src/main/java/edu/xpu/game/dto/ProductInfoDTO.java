package edu.xpu.game.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName ProductInfoDTO
 * @Description
 * @Date 2019-05-25 16:06
 * @Version 1.0
 */
@Data
public class ProductInfoDTO {
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

    /**
     * 商品类型
     */
    private Integer categoryType;

    /**
     * 商品评分
     */
    private String productGrade;
}
