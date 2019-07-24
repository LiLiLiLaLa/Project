package edu.xpu.game.controller.shopping;

import com.alibaba.fastjson.JSONObject;
import edu.xpu.game.dto.OrderInfoDetailDTO;
import edu.xpu.game.entity.OrderDetail;
import edu.xpu.game.entity.OrderMaster;
import edu.xpu.game.service.shopping.OrderService;
import edu.xpu.game.util.JsonUtil;
import edu.xpu.game.util.ResultVOUtil;
import edu.xpu.game.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName OrderController
 * @Description 订单控制器
 * @Date 2019-05-26 13:50
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 根据购物车里面的商品创建订单
     * @return 创建订单的结果
     */
    @RequestMapping("/create")
    public String createOrder(HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userInfo");
        OrderInfoDetailDTO orderInfo = orderService.createOrder(userId ,request);

        ResultVO<OrderInfoDetailDTO> resultVO = new ResultVO<>();
        if(orderInfo.getOrderInfoDTOList().size() > 0){
            resultVO.setCode(0);
            resultVO.setMsg("创建新订单成功");
            resultVO.setData(orderInfo);
        }else{
            resultVO.setCode(1);
            resultVO.setMsg("创建新订单失败");
        }
        return JSONObject.toJSONString(resultVO, true);
    }


    /**
     * 取消订单
     * @param orderMasterId 订单Id
     * @return 取消订单结果
     */
    @RequestMapping("/cancel")
    public String cancel(String orderMasterId, HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userInfo");
        OrderMaster cancelRet = orderService.cancelOrderForUser(orderMasterId, userId);
        if(cancelRet != null)
            return JsonUtil.toJson(ResultVOUtil.success(cancelRet));
        return JsonUtil.toJson(ResultVOUtil.error(1, "取消订单失败"));
    }


    /**
     * 取消订单（这个是管理员接口）
     * @param orderMasterId 订单Id
     * @return 取消订单结果
     */
    @RequestMapping("/cancelOrder")
    public String cancelOrder(String orderMasterId, HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userInfo");
        OrderMaster cancelRet = orderService.cancelOrder(orderMasterId, userId);
        if(cancelRet != null)
            return JsonUtil.toJson(ResultVOUtil.success(cancelRet));
        return JsonUtil.toJson(ResultVOUtil.error(1, "取消订单失败"));
    }



    /**
     * 查看所有订单
     * @return 所有订单
     */
    @RequestMapping("/allList")
    public String showAllOrder(){
        List<OrderMaster> orderMasterList = orderService.showAllOrder();
        ResultVO<List<OrderMaster>> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("查询所有订单成功");
        resultVO.setData(orderMasterList);
        return JSONObject.toJSONString(resultVO, true);
    }

    /**
     * 查询订单详情
     * @param orderMasterId 主订单Id
     * @return 订单详情
     */
    @RequestMapping("/orderDetail")
    public String showOrderDetail(@RequestParam("orderMasterId") String orderMasterId){
        List<OrderDetail> orderMasterList = orderService.showOrderDetail(orderMasterId);

        ResultVO<List<OrderDetail>> resultVO = new ResultVO<>();

        if(orderMasterList.size() <= 0){
            resultVO.setCode(1);
            resultVO.setMsg("查询订单失败");
        }else{
            resultVO.setCode(0);
            resultVO.setMsg("查询订单详情成功");
            resultVO.setData(orderMasterList);
        }
        return JSONObject.toJSONString(resultVO, true);
    }


    /**
     * 根据订单状态查询订单
     * @param orderStatus 订单状态
     * @return 符合条件的订单
     */
    @RequestMapping("/allListByOrderStatus")
    public String showAllOrderByStatus(@RequestParam("orderStatus") Integer orderStatus){
        List<OrderMaster> orderMasterList = orderService.showAllOrderByStatus(orderStatus);
        ResultVO<List<OrderMaster>> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("查询对应状态订单成功");
        resultVO.setData(orderMasterList);
        return JSONObject.toJSONString(resultVO, true);
    }

    @RequestMapping("/allListByBuyerId")
    public String showAllOrderByBuyerId(@RequestParam("buyerId") String buyerId){
        List<OrderMaster> orderMasterList = orderService.showAllOrderByBuyerId(buyerId);

        ResultVO<List<OrderMaster>> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("查询对应用户订单成功");
        resultVO.setData(orderMasterList);
        return JSONObject.toJSONString(resultVO, true);
    }

    @RequestMapping("/finish")
    public String finishOrder(@RequestParam("orderMasterId") String orderMasterId){
        ResultVO<Boolean> ret = new ResultVO<>();
        Boolean finishOrder = orderService.finishOrder(orderMasterId);
        ret.setCode(finishOrder ? 0:1);
        ret.setData(finishOrder);
        ret.setMsg("完结订单"+ (finishOrder ? "成功":"失败"));
        return JSONObject.toJSONString(ret, true);
    }
}
