package edu.xpu.game.repository;

import edu.xpu.game.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName ProductInfoRepository
 * @Description
 * @Date 2019-05-22 11:28
 * @Version 1.0
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findAllByCategoryType(Integer categoryType);

    List<ProductInfo> findAllByProductNameLike(String productName);

    List<ProductInfo> findAllByCategoryTypeNot(Integer type);
}
