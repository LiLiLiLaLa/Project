package edu.xpu.game.repository;


import edu.xpu.game.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByCategoryId(Integer categoryId);

    List<ProductCategory> findAllByCategoryType(Integer categoryId);
}
