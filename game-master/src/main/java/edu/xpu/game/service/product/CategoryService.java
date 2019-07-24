package edu.xpu.game.service.product;

import edu.xpu.game.entity.ProductCategory;

/**
 * @version 1.0
 * @className CategoryService
 * @description
 * @date 2019-05-31 12:51
 */
public interface CategoryService {
    ProductCategory addCategoryClick(Integer category);

    public ProductCategory hotTop();
}
