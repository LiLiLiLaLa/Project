package edu.xpu.game.dto;

import edu.xpu.game.entity.ProductInfo;
import lombok.Data;

/**
 * @ClassName OrderDTO
 * @Description
 * @Date 2019-05-27 09:09
 * @Version 1.0
 */
@Data
public class OrderInfoDTO {
    private ProductInfo productInfo;
    private Integer productNum;
}
