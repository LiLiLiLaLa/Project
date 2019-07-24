package edu.xpu.game.controller.shopping;

import com.alibaba.fastjson.JSONObject;
import edu.xpu.game.dto.OrderInfoDTO;
import edu.xpu.game.service.shopping.ShoppingService;
import edu.xpu.game.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName ShoppingCartController
 * @Description 购物车控制器
 * @Date 2019-05-26 14:46
 * @Version 1.0
 */
@RequestMapping("/shopping")
@RestController
public class ShoppingCartController {

    private final ShoppingService shoppingService;

    @Autowired
    public ShoppingCartController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    /**
     * 购物车添加商品
     * @param productId 添加商品的Id
     * @param count 添加商品的数量
     * @return 是否添加成功
     */
    @RequestMapping("/add")
    public String add(String productId, Integer count, HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        String userId = (String)request.getSession().getAttribute("userInfo");
        if (userIdIsNull(resultVO, userId)) return JSONObject.toJSONString(resultVO, true);
        boolean addRet = shoppingService.addShopping(productId, count, userId);
        if(addRet){
            resultVO.setCode(0);
            resultVO.setMsg("添加购物车成功");
        }else{
            resultVO.setCode(1);
            resultVO.setMsg("添加购物车失败");
        }
        return JSONObject.toJSONString(resultVO, true);
    }

    /**
     * 购物车删除商品
     * @param productId 商品Id
     * @return 删除是否成功
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam("productId") String productId, HttpServletRequest request){
        ResultVO resultVO = new ResultVO();
        String userId = (String)request.getSession().getAttribute("userInfo");
        if (userIdIsNull(resultVO, userId)) return JSONObject.toJSONString(resultVO, true);
        boolean deleteRet = shoppingService.delete(productId, userId);
        if(deleteRet){
            resultVO.setCode(0);
            resultVO.setMsg("从购物车删除成功");
        }else{
            resultVO.setCode(1);
            resultVO.setMsg("从购物车删除失败");
        }
        return JSONObject.toJSONString(resultVO, true);
    }


    /**
     * 购物车商品展示
     * @return 要展示的数据
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request){
        ResultVO<List<OrderInfoDTO>> retList = new ResultVO<>();
        String userId = (String)request.getSession().getAttribute("userInfo");
        if (userIdIsNull(retList, userId)) return JSONObject.toJSONString(retList, true);

        List<OrderInfoDTO> list = shoppingService.list(userId);
        if(list.size() > 0){
            retList.setCode(0);
            retList.setMsg("展示成功");
            retList.setData(list);
        }else{
            retList.setCode(1);
            retList.setMsg("无数据");
        }
        return JSONObject.toJSONString(retList, true);
    }

    private boolean userIdIsNull(ResultVO retList, String userId) {
        if (userId == null) {
            retList.setCode(2);
            retList.setMsg("用户未登录");
            return true;
        }
        return false;
    }

    /**
     * 获取购物车商品总价格
     * @return 商品总价格
     */
    @RequestMapping("/amount")
    public String amount(HttpServletRequest request){
        ResultVO<Double> ret = new ResultVO<>();
        String userId = (String)request.getSession().getAttribute("userInfo");
        if(userIdIsNull(ret,userId)) return JSONObject.toJSONString(ret, true);
        BigDecimal amount = shoppingService.amount(userId);
        if(amount.doubleValue() > 0){
            ret.setMsg("成功");
            ret.setCode(0);
            ret.setData(amount.doubleValue());
        }else{
            ret.setMsg("失败");
            ret.setCode(1);
        }
        return JSONObject.toJSONString(ret, true);
    }
}
