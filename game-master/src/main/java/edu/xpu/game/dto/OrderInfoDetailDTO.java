package edu.xpu.game.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName OrderInfoDetailDTO
 * @Description
 * @Date 2019-05-28 22:00
 * @Version 1.0
 */
@Data
public class OrderInfoDetailDTO {
    private String masterOrderId;
    private List<OrderInfoDTO> orderInfoDTOList;
    private BigDecimal orderPrice;
}
