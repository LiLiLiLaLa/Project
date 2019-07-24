package edu.xpu.game.service.product.impl;

import edu.xpu.game.entity.ProductCategory;
import edu.xpu.game.repository.ProductCategoryRepository;
import edu.xpu.game.service.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @className CategoryServiceImpl
 * @description
 * @date 2019-05-31 12:52
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final ProductCategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductCategory addCategoryClick(Integer category) {
        List<ProductCategory> byCategoryTypeList = repository.findAllByCategoryType(category);
        ProductCategory byCategoryType = byCategoryTypeList.get(0);
        byCategoryType.setClickNum(byCategoryType.getClickNum() + 1);
        repository.save(byCategoryType);

        return hotTop();
    }

    public ProductCategory hotTop() {
        List<ProductCategory> topByClickNum = repository.findAll();
        ProductCategory max = new ProductCategory();
        max.setClickNum(-1);
        for(ProductCategory productCategory: topByClickNum){
            if(max.getClickNum() < productCategory.getClickNum()){
                max = productCategory;
            }
        }
        return max;
    }
}
