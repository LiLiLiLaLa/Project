package edu.xpu.game.controller.shopping;

import com.alipay.api.AlipayApiException;
import edu.xpu.game.entity.AlipayBean;
import edu.xpu.game.entity.OrderMaster;
import edu.xpu.game.enums.PayStatusEnum;
import edu.xpu.game.service.shopping.impl.OrderServiceImpl;
import edu.xpu.game.service.shopping.impl.PayServiceImpl;
import edu.xpu.game.util.JsonUtil;
import edu.xpu.game.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

/**
 * @version 1.0
 * @className PayController
 * @description 支付相关控制器
 * @date 2019-05-31 08:18
 */


@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {
    private final OrderServiceImpl orderService;
    private final PayServiceImpl payService;

    @Autowired
    public PayController(OrderServiceImpl orderService, PayServiceImpl payService) {
        this.orderService = orderService;
        this.payService = payService;
    }

    @ResponseBody
    @RequestMapping("/affirmPay")
    public String payOrder(@RequestParam("masterOrderId") String masterOrderId){
        Optional<OrderMaster> byId = orderService.findOrderById(masterOrderId);

        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(masterOrderId);
        alipayBean.setSubject("农家商城购物");

        if(byId.isPresent()){
            alipayBean.setTotal_amount(new StringBuffer().append(byId.get().getOrderAmount()));
            try {
                String aliPay = payService.aliPay(alipayBean);
                log.info("[pay.affirmPay] aliPay = {}", aliPay);
                return aliPay;
            } catch (AlipayApiException e) {
                return JsonUtil.toJson(ResultVOUtil.error(2, "支付过程出错"));
            }
        }else{
            return JsonUtil.toJson(ResultVOUtil.error(1, "不存在此订单"));
        }
    }


    /**
     * 修复再次支付的BUG
     * @param masterOrderId 订单Id
     */
    @ResponseBody
    @RequestMapping("/affirmPayAgain")
    public String payOrderAgain(@RequestParam("masterOrderId") String masterOrderId){
        return masterOrderId;
    }


    /**
     * 支付成功的回调
     * @param trade_status 交易状态
     * @param out_trade_no 商户订单号
     */
    @RequestMapping("/payFinish")
    public void payFinishCallback(String trade_status, String out_trade_no){
        log.info("支付回调：trade_status = "+trade_status);
        if("TRADE_SUCCESS".equals(trade_status)){
            //支付成功
            Optional<OrderMaster> orderById = orderService.findOrderById(out_trade_no);
            if(orderById.isPresent()){
                OrderMaster orderMaster = orderById.get();
                //修改支付状态
                orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                OrderMaster changeRet = orderService.change(orderMaster);
                log.info("changeRet", changeRet);
            }
        }else{
            //支付失败
            Optional<OrderMaster> orderById = orderService.findOrderById(out_trade_no);
            if(orderById.isPresent()){
                OrderMaster orderMaster = orderById.get();
                //修改支付状态
                orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
                orderService.change(orderMaster);
            }
        }
    }


    @RequestMapping("/paySyncFinish")
    public String paySyncFinishCallback(){
        return "redirect:http://ahojcn.natapp1.cc/#/pay";
    }


    @ResponseBody
    @RequestMapping("/payStatus")
    public String payStatus(@RequestParam("masterOrderId") String masterOrderId){
        Optional<OrderMaster> orderById = orderService.findOrderById(masterOrderId);
        return orderById.map(orderMaster -> JsonUtil.toJson(ResultVOUtil.success(orderMaster.getPayStatus()))).orElseGet(() -> JsonUtil.toJson(ResultVOUtil.error(1, "无此订单")));
    }
}
