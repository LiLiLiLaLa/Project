package edu.xpu.game.service.shopping.impl;

import edu.xpu.game.dto.OrderInfoDTO;
import edu.xpu.game.entity.ProductInfo;
import edu.xpu.game.entity.ShoppingCart;
import edu.xpu.game.repository.ProductInfoRepository;
import edu.xpu.game.repository.ShoppingCartRepository;
import edu.xpu.game.service.shopping.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName ShoppingServiceImpl
 * @Description
 * @Date 2019-05-26 14:57
 * @Version 1.0
 */
@Service
@Slf4j
public class ShoppingServiceImpl implements ShoppingService {
    private ShoppingCartRepository shopRepository;
    private ProductInfoRepository productRepository;

    @Autowired
    public ShoppingServiceImpl(ShoppingCartRepository shopRepository, ProductInfoRepository productRepository) {
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    @Override
    public boolean addShopping(String productId, Integer count, String userId) {
        Optional<ProductInfo> byId = productRepository.findById(productId);

        if (byId.isPresent()) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);

            //添加商品的时候查看库存
            if(byId.get().getProductStock() > count) {
                shoppingCart.setProductNum(count);
            }else{
                return false;
            }

            //如果已经存在，则商品数量增加
            ShoppingCart findRet = shopRepository.findByProductIdAndUserId(productId, userId);
            if(findRet != null){
                findRet.setProductNum(findRet.getProductNum() + count);
                ShoppingCart save = shopRepository.save(findRet);
                return save != null;
            }

            shoppingCart.setProductId(productId);
            shoppingCart.setShoppingId(String.valueOf(System.currentTimeMillis()));
            ShoppingCart save = shopRepository.save(shoppingCart);
            return save != null;
        }
        return false;
    }

    @Override
    public boolean delete(String productId, String userId) {
        Optional<ProductInfo> byId = productRepository.findById(productId);
        if(byId.isPresent()){
            ShoppingCart findRet = shopRepository.findByProductIdAndUserId(productId, userId);
            if(findRet != null && findRet.getProductNum() >= 1){
                int i = findRet.getProductNum() - 1;
                findRet.setProductNum(i);
                if(i <= 0){
                    shopRepository.deleteById(findRet.getShoppingId());
                }else{
                    ShoppingCart save = shopRepository.save(findRet);
                    assert save!= null;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 购物车展示
     * @param userId 用户Id
     * @return 购物车商品以及对应数量
     */
    @Override
    public List<OrderInfoDTO> list(String userId) {
        List<OrderInfoDTO> ret = new ArrayList<>();
        OrderInfoDTO orderInfoDTO;
        List<ShoppingCart> allByUserId = shopRepository.findAllByUserId(userId);
        for(ShoppingCart cart: allByUserId){
            orderInfoDTO = new OrderInfoDTO();
            Optional<ProductInfo> byId = productRepository.findById(cart.getProductId());
            byId.ifPresent(orderInfoDTO::setProductInfo);
            orderInfoDTO.setProductNum(cart.getProductNum());
            ret.add(orderInfoDTO);
        }
        return ret;
    }

    @Override
    public BigDecimal amount(String userId) {
        List<OrderInfoDTO> list = list(userId);
        BigDecimal bigDecimal = new BigDecimal("0.0");
        for(OrderInfoDTO info:list){
            bigDecimal = bigDecimal.add(info.getProductInfo().getProductPrice()
                    .multiply(new BigDecimal(info.getProductNum())));
        }
        return bigDecimal;
    }
}
