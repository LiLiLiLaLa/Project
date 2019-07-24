package edu.xpu.game.service.shopping;


import edu.xpu.game.dto.OrderInfoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingService {
    boolean addShopping(String productId, Integer count, String userId);

    boolean delete(String productId, String userId);


    List<OrderInfoDTO> list(String userId);

    BigDecimal amount(String userId);
}
