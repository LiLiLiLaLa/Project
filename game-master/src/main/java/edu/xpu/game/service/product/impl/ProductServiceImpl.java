package edu.xpu.game.service.product.impl;

import edu.xpu.game.dto.ProductInfoDTO;
import edu.xpu.game.entity.ProductInfo;
import edu.xpu.game.repository.ProductInfoRepository;
import edu.xpu.game.service.product.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ProductServiceImpl
 * @Description
 * @Date 2019-05-25 15:25
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductInfoService {
    private final ProductInfoRepository repository;

    @Autowired
    public ProductServiceImpl(ProductInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductInfo> findAllProduct() {
        return repository.findAll();
    }

    @Override
    public boolean deleteProductInfoById(String productId) {
        if(repository.findById(productId).isPresent()){
            repository.deleteById(productId);
            return true;
        }
        return false;
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo updateProductInfo(String productId, ProductInfoDTO productInfoDTO) {
        Optional<ProductInfo> byId = repository.findById(productId);
        if(byId.isPresent()){
            ProductInfo newProductInfo = new ProductInfo();
            newProductInfo.setProductId(productId);
            BeanUtils.copyProperties(productInfoDTO, newProductInfo);
            return repository.save(newProductInfo);
        }
        return null;
    }

    @Override
    public Optional<ProductInfo> findProductById(String productId) {
        return repository.findById(productId);
    }

    @Override
    public List<ProductInfo> findProductByCategory(Integer categoryType) {
        return repository.findAllByCategoryType(categoryType);
    }

    @Override
    public List<ProductInfo> findProductByName(String productName) {
        return repository.findAllByProductNameLike("%" + productName + "%");
    }
}
