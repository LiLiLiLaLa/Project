package edu.xpu.game.service.product;

import edu.xpu.game.dto.ProductInfoDTO;
import edu.xpu.game.entity.ProductInfo;

import java.util.List;
import java.util.Optional;

public interface ProductInfoService {
    List<ProductInfo> findAllProduct();

    boolean deleteProductInfoById(String productId);

    ProductInfo save(ProductInfo productInfo);

    ProductInfo updateProductInfo(String productId, ProductInfoDTO productInfoDTO);

    Optional<ProductInfo> findProductById(String productId);

    List<ProductInfo> findProductByCategory(Integer categoryType);

    List<ProductInfo> findProductByName(String productName);
}
