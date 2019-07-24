package edu.xpu.game.controller.other;

import edu.xpu.game.entity.MsgQueue;
import edu.xpu.game.entity.UserInfo;
import edu.xpu.game.service.other.impl.MsgQueueServiceImpl;
import edu.xpu.game.service.user.UserInfoService;
import edu.xpu.game.util.JsonUtil;
import edu.xpu.game.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @className MsgServerController
 * @description 消息池控制器
 * @date 2019-06-03 17:29
 */
@Slf4j
@RestController
@RequestMapping("/msg")
public class MsgServerController {
    private final MsgQueueServiceImpl service;

    private final UserInfoService userInfoService;

    @Autowired
    public MsgServerController(MsgQueueServiceImpl service, UserInfoService userInfoService) {
        this.service = service;
        this.userInfoService = userInfoService;
    }

    @RequestMapping("/send")
    public String send(String msgTo, String msgMsg, HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userInfo");
        if(userId == null)
            return JsonUtil.toJson(ResultVOUtil.error(2, "用户未登录"));
        Optional<UserInfo> byId = userInfoService.findById(msgTo);
        if(!byId.isPresent())
            return JsonUtil.toJson(ResultVOUtil.error(3, "不存在此用户"));
        if(service.sendMsg(userId, msgTo, msgMsg))
            return JsonUtil.toJson(ResultVOUtil.success());
        return JsonUtil.toJson(ResultVOUtil.error(1, "发送失败"));
    }


    @RequestMapping("/accept")
    public String accept(HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userInfo");
        if(userId == null)
            return JsonUtil.toJson(ResultVOUtil.error(2, "用户未登录"));

        List<MsgQueue> msgQueues = service.acceptMsg(userId);
        if(msgQueues.size() > 0){
            return JsonUtil.toJson(ResultVOUtil.success(msgQueues));
        }
        return JsonUtil.toJson(ResultVOUtil.error(1, "无消息可以接收"));
    }
}
