package edu.xpu.game.repository;

import edu.xpu.game.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void save(){
        ProductCategory productCategory = new ProductCategory("蔬菜", 0);
        ProductCategory save = repository.save(productCategory);
        assertNotNull(save);

        ProductCategory productCategory1 = new ProductCategory("水果", 1);
        ProductCategory save1 = repository.save(productCategory1);
        assertNotNull(save1);


        ProductCategory productCategory2 = new ProductCategory("花卉", 2);
        ProductCategory save2 = repository.save(productCategory2);
        assertNotNull(save2);

        ProductCategory productCategory3 = new ProductCategory("家禽", 3);
        ProductCategory save3 = repository.save(productCategory3);
        assertNotNull(save3);
    }

    @Test
    public void delete(){
        repository.deleteById(4);
        List<ProductCategory> all = repository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    public void update(){
        ProductCategory productCategory = new ProductCategory("花草", 2);
        productCategory.setCategoryId(3);
        repository.save(productCategory);
    }


    @Test
    public void find(){
        ProductCategory byCategoryId = repository.findByCategoryId(1);
        assertNotNull(byCategoryId);
    }

    @Test
    public void findTopByClickNum(){
        List<ProductCategory> topByClickNum = repository.findAll();
        int max = 0;
        for(ProductCategory productCategory: topByClickNum){
            if(max < productCategory.getClickNum()){
                max = productCategory.getClickNum();
            }
        }
        System.out.println("max = "+max);
        assertNotNull(topByClickNum);
    }

    @Test
    public void add(){
        List<ProductCategory> allByCategoryType = repository.findAllByCategoryType(3);
        System.out.println("size:" + allByCategoryType.size());
    }

}