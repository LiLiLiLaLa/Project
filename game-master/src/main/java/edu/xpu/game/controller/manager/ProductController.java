package edu.xpu.game.controller.manager;

import com.alibaba.fastjson.JSONObject;
import edu.xpu.game.dto.ProductInfoDTO;
import edu.xpu.game.entity.ProductInfo;
import edu.xpu.game.service.product.ProductInfoService;
import edu.xpu.game.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ProductController
 * @Description 商品信息控制器
 * @Date 2019-05-25 15:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    private final ProductInfoService productInfoService;

    @Autowired
    public ProductController(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }


    /**
     * 商品展示
     * @return JSON格式的商品信息
     */
    @PostMapping("/list")
    public String productList(){
        List<ProductInfo> infoList = productInfoService.findAllProduct();
        ResultVO<List<ProductInfo>> ret = new ResultVO<>();
        ret.setCode(0);
        ret.setMsg("已经获得全部产品数据");
        ret.setData(infoList);
        return JSONObject.toJSONString(ret, true);
    }

    /**
     * 删除商品
     * @param productId 要删除商品Id
     * @return 删除结果
     */
    @PostMapping("/delete")
    public String delete(@RequestParam("productId") String productId){
        boolean deleteRet = productInfoService.deleteProductInfoById(productId);
        ResultVO<String> ret = new ResultVO<>();
        if(deleteRet){
            ret.setCode(0);
            ret.setMsg("删除成功");
        }else{
            ret.setCode(1);
            ret.setMsg("对应商品不存在");
        }
        return JSONObject.toJSONString(ret, true);
    }


    /**
     * 添加商品
     * @param productInfoDTO 商品描述表单信息
     * @return 添加的商品信息
     */
    @PostMapping("/add")
    public String add(@ModelAttribute ProductInfoDTO productInfoDTO){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(String.valueOf(System.currentTimeMillis()));
        BeanUtils.copyProperties(productInfoDTO, productInfo);
        log.info("[product.add] = {}", productInfoDTO);

        ProductInfo save = productInfoService.save(productInfo);
        ResultVO<ProductInfo> ret = new ResultVO<>();
        if(save != null){
            ret.setCode(0);
            ret.setMsg("添加成功");
            ret.setData(save);
        }else{
            ret.setCode(1);
            ret.setMsg("添加失败");
        }
        return JSONObject.toJSONString(ret, true);
    }

    /**
     * 修改商品信息
     * @param productId 修改商品的Id
     * @param productInfoDTO 新商品信息
     * @return 新商品信息
     */
    @RequestMapping("/update")
    public String update(@RequestParam("productId") String productId,
                         @ModelAttribute ProductInfoDTO productInfoDTO){
        ProductInfo updateRet = productInfoService.updateProductInfo(productId, productInfoDTO);
        ResultVO<ProductInfo> ret = new ResultVO<>();
        if(updateRet != null){
            ret.setCode(0);
            ret.setMsg("商品信息更新成功");
            ret.setData(updateRet);
        }else{
            ret.setCode(1);
            ret.setMsg("无对应商品");
        }
        return JSONObject.toJSONString(ret, true);
    }

    /**
     * 根据商品Id查详情
     * @param productId 商品Id
     * @return 商品详情
     */
    @RequestMapping("/findById")
    public String findById(@RequestParam("productId") String productId){
        Optional<ProductInfo> findRet = productInfoService.findProductById(productId);
        ResultVO<ProductInfo> ret = new ResultVO<>();
        if(findRet.isPresent()){
            ret.setCode(0);
            ret.setMsg("已查到商品信息");
            ret.setData(findRet.get());
        }else{
            ret.setCode(1);
            ret.setMsg("无商品信息");
        }
        return JSONObject.toJSONString(ret, true);
    }

    /**
     * 根据类别查询查询商品
     * @param categoryType 类型（参考数据库表SQL说明）
     * @return 该类商品集合
     */
    @RequestMapping("/findByCategory")
    public String findByCategory(@RequestParam("categoryType") Integer categoryType){
        List<ProductInfo> retList = productInfoService.findProductByCategory(categoryType);
        ResultVO<List<ProductInfo>> ret = new ResultVO<>();
        if(retList.size() >= 1){
            ret.setCode(0);
            ret.setMsg("已查到该类商品信息");
            ret.setData(retList);
        }else{
            ret.setCode(1);
            ret.setMsg("无该类商品信息");
        }
        return JSONObject.toJSONString(ret, true);
    }

    /**
     * 根据商品名称查询（相似查询）
     * @param productName 商品名称
     * @return 查询结果集合
     */
    @RequestMapping("/findByName")
    public String findByName(@RequestParam("productName") String productName){
        List<ProductInfo> findRetList = productInfoService.findProductByName(productName);
        ResultVO<List<ProductInfo>> ret = new ResultVO<>();
        if(findRetList.size() >= 1){
            ret.setCode(0);
            ret.setMsg("已查到符合名称的商品");
            ret.setData(findRetList);
        }else{
            ret.setCode(1);
            ret.setMsg("无符合名称的商品");
        }
        return JSONObject.toJSONString(ret, true);
    }
}
