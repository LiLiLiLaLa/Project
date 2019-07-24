package edu.xpu.game.repository;

import edu.xpu.game.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;
    @Test
    public void save(){
        ProductInfo product = new ProductInfo();
        product.setProductId(String.valueOf(System.currentTimeMillis()));
        product.setProductName("紫藤萝");
        product.setProductPrice(new BigDecimal(35.0));
        product.setProductStock(1024);
        product.setProductDescription("优良的观花藤木植物，缠绕茎，羽状复叶，小叶长椭圆形。总状花序，春季开花，花淡紫色，具有优美的姿态和迷人的风采。");
        product.setProductIcon("https://s2.ax1x.com/2019/05/23/VCMZzd.png");
        product.setCategoryType(2);
        ProductInfo save = repository.save(product);
        assertNotNull(save);

        ProductInfo product2 = new ProductInfo();
        product2.setProductId(String.valueOf(System.currentTimeMillis()));
        product2.setProductName("康乃馨");
        product2.setProductPrice(new BigDecimal(80.0));
        product2.setProductStock(500);
        product2.setProductDescription("叶片线状披针形，顶端长渐尖，基部稍成短鞘，中脉明显，上面下凹，下面稍凸起。花常单生枝端有香气，粉红、紫红或白色。");
        product2.setProductIcon("https://s2.ax1x.com/2019/05/23/VC1PZq.png");
        product2.setCategoryType(2);
        ProductInfo save2 = repository.save(product2);
        assertNotNull(save2);

        ProductInfo product3 = new ProductInfo();
        product3.setProductId(String.valueOf(System.currentTimeMillis()));
        product3.setProductName("风信子");
        product3.setProductPrice(new BigDecimal(25.0));
        product3.setProductStock(2000);
        product3.setProductDescription("原产地中海沿岸及小亚细亚一带，是研究发现的会开花的植物中最香的一个品种。喜阳光充足和比较湿润的生长环境，要求排水良好和肥沃的沙壤土等。");
        product3.setProductIcon("https://s2.ax1x.com/2019/05/23/VC1FoV.png");
        product3.setCategoryType(2);
        ProductInfo save3 = repository.save(product3);
        assertNotNull(save3);


        ProductInfo product4 = new ProductInfo();
        product4.setProductId(String.valueOf(System.currentTimeMillis()));
        product4.setProductName("农家乌鸡");
        product4.setProductPrice(new BigDecimal(180.0));
        product4.setProductStock(100);
        product4.setProductDescription("放在油里面煎一下，然后加我在里面做出来的味道才会特别的好，可以放一些土豆，也可以在里面多加上一些金针菇，做出来的味道也是非常的好吃哦。");
        product4.setProductIcon("https://s2.ax1x.com/2019/05/23/VC1AiT.png");
        product4.setCategoryType(3);
        ProductInfo save4 = repository.save(product4);
        assertNotNull(save4);


        ProductInfo product5 = new ProductInfo();
        product5.setProductId(String.valueOf(System.currentTimeMillis()));
        product5.setProductName("测试");
        product5.setProductPrice(new BigDecimal(250));
        product5.setProductStock(100);
        product5.setProductDescription("xxxx");
        product5.setProductIcon("https://photo.16pic.com/00/63/04/16pic_6304973_b.jpg");
        product5.setCategoryType(3);
        ProductInfo save5 = repository.save(product5);
        assertNotNull(save5);
    }


    @Test
    public void update(){
        ProductInfo product = new ProductInfo();
        product.setProductId("1558575338542");
        product.setProductName("测试2");
        product.setProductPrice(new BigDecimal(250));
        product.setProductStock(100);
        product.setProductDescription("xxxx");
        product.setProductIcon("https://photo.16pic.com/00/63/04/16pic_6304973_b.jpg");
        product.setCategoryType(3);
        ProductInfo save = repository.save(product);
        assertNotNull(save);
    }

    @Test
    public void delete(){
        repository.deleteById("1558575338542");
    }

    @Test
    public void findOther(){
        List<ProductInfo> allByCategoryTypeNot = repository.findAllByCategoryTypeNot(3);
        for(ProductInfo productInfo: allByCategoryTypeNot){
            System.out.println(productInfo);
        }
        assertNotNull(allByCategoryTypeNot);
    }
}