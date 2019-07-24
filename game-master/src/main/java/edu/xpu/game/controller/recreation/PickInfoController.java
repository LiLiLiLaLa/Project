package edu.xpu.game.controller.recreation;

import edu.xpu.game.entity.ProductInfo;
import edu.xpu.game.service.recreation.impl.PickInfoServiceImpl;
import edu.xpu.game.util.JsonUtil;
import edu.xpu.game.util.ResultVOUtil;
import edu.xpu.game.vo.PickVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @className PickInfoController
 * @description
 * @date 2019-06-07 17:43
 */
@RestController
@RequestMapping("/pick")
@Slf4j
public class PickInfoController {
    private final PickInfoServiceImpl pickInfoService;

    @Autowired
    public PickInfoController(PickInfoServiceImpl pickInfoService) {
        this.pickInfoService = pickInfoService;
    }


    /**
     * 查看所有预约采摘请求
     * @return 采摘请求
     */
    @RequestMapping("/checkShow")
    public String checkShow(){
        List<PickVO> pickVOList = pickInfoService.checkShow();
        return JsonUtil.toJson(ResultVOUtil.success(pickVOList));
    }


    /**
     * @param pickId 预约订单的Id
     * @param checkStatus 要修改的状态
     * @return 是否修改成功
     */
    @RequestMapping("/checkOne")
    public String checkOne(String pickId, Integer checkStatus){
        if(pickId == null || checkStatus == null)
            return JsonUtil.toJson(ResultVOUtil.error(1 ,"参数不正确"));

        Boolean checkRet = pickInfoService.checkOne(pickId, checkStatus);
        return checkRet ? JsonUtil.toJson(ResultVOUtil.success()):
                JsonUtil.toJson(ResultVOUtil.error(2 ,"状态错误"));
    }


    /**
     * 用户预约采摘
     * PickInfo的productId必传
     * PickInfo的pickTime必传
     */
    @RequestMapping("/addMyPick")
    //public String addPick(@ModelAttribute PickInfo pickInfo, HttpServletRequest request){
    public String addPick(String pickProduct, Long pickTime, HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userInfo");
        if(userId == null) return JsonUtil.toJson(ResultVOUtil.error(1, "用户未登录"));

        Boolean pickRet = pickInfoService.userAddPick(pickProduct,pickTime, userId);
        return pickRet ? JsonUtil.toJson(ResultVOUtil.success()):
                JsonUtil.toJson(ResultVOUtil.error(2, "重复预约"));
    }

    /**
     * 用户查看预约采摘信息
     */
    @RequestMapping("/findMyPick")
    public String findMyPick(HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userInfo");
        if(userId == null) return JsonUtil.toJson(ResultVOUtil.error(1, "用户未登录"));
        List<PickVO> pickVOList = pickInfoService.findMyPick(userId);
        return JsonUtil.toJson(ResultVOUtil.success(pickVOList));
    }


    /**
     * 用户取消预约
     * @param pickId 预约号码
     */
    @RequestMapping("/cancelMyPick")
    public String cancelPick(String pickId, HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userInfo");
        if(userId == null) return JsonUtil.toJson(ResultVOUtil.error(1, "用户未登录"));
        if(pickId == null) return JsonUtil.toJson(ResultVOUtil.error(2, "参数不正确"));
        Boolean ret = pickInfoService.cancelPick(pickId);
        return ret ? JsonUtil.toJson(ResultVOUtil.success()) :
                JsonUtil.toJson(ResultVOUtil.error(3, "取消预约失败"));
    }

    /**
     * 查看可采摘的农作物
     * @return 可采摘的农作物信息
     */
    @RequestMapping("/show")
    public String showAllProduct(){
        List<ProductInfo> ret = pickInfoService.showAllProduct();
        return JsonUtil.toJson(ResultVOUtil.success(ret));
    }
}
