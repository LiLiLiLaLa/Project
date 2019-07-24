package edu.xpu.game.controller.recreation;

import edu.xpu.game.entity.BotanyBase;
import edu.xpu.game.service.other.BotanyInfoService;
import edu.xpu.game.util.JsonUtil;
import edu.xpu.game.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @className PotanyController
 * @description
 * @date 2019-06-07 14:20
 */
@RestController
@RequestMapping("/botany")
public class BotanyInfoController {
    private final BotanyInfoService service;

    @Autowired
    public BotanyInfoController(BotanyInfoService service) {
        this.service = service;
    }


    @RequestMapping("/adopt")
    public String adopt(String botanyId, HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userInfo");
        if(userId == null)  return JsonUtil.toJson(ResultVOUtil.error(1, "用户未登录"));
        BotanyBase adoptRet = service.adoptBotany(botanyId, userId);
        if(adoptRet != null){
            return JsonUtil.toJson(ResultVOUtil.success(adoptRet));
        }
        return JsonUtil.toJson(ResultVOUtil.error(2, "已领养过该植物"));
    }


    @RequestMapping("/add")
    public String add(@ModelAttribute BotanyBase botanyInfo){
        Boolean addRet = service.add(botanyInfo);
        return JsonUtil.toJson(addRet ? ResultVOUtil.success():
                ResultVOUtil.error(1, "添加失败"));
    }


    @RequestMapping("/delete")
    public String delete(String botanyId){
        try{
            service.delete(botanyId);
            return JsonUtil.toJson(ResultVOUtil.success());
        }catch (Exception e){
            return JsonUtil.toJson(ResultVOUtil.error(1, "参数错误"));
        }
    }

    @RequestMapping("/update")
    public String update(@ModelAttribute BotanyBase botanyInfo){
        if(botanyInfo == null)
            return JsonUtil.toJson(ResultVOUtil.error(1, "表单信息为空"));
        BotanyBase info = service.find(botanyInfo.getBotanyId());
        if(info != null){
            Boolean addRet = service.save(botanyInfo);
            return addRet ? JsonUtil.toJson(ResultVOUtil.success()):
                    JsonUtil.toJson(ResultVOUtil.error(2, "更新失败"));
        }
        return JsonUtil.toJson(ResultVOUtil.error(3, "无此植物信息"));
    }

    @RequestMapping("/find")
    public String find(String botanyId){
        BotanyBase findRet = service.find(botanyId);
        return JsonUtil.toJson(findRet != null ? ResultVOUtil.success(findRet):
                ResultVOUtil.error(1, "查找失败"));
    }

    @RequestMapping("/showAll")
    public String showAll(){
        List<BotanyBase> findRet = service.findAll();
        return JsonUtil.toJson(ResultVOUtil.success(findRet));
    }

    @RequestMapping("/showMyAll")
    public String showMyAll(HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userInfo");
        if(userId == null)  return JsonUtil.toJson(ResultVOUtil.error(1, "用户未登录"));
        List<BotanyBase> findRet = service.findMyAll(userId);
        return JsonUtil.toJson(ResultVOUtil.success(findRet));
    }
}
