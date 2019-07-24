package edu.xpu.game.controller.recreation;

import edu.xpu.game.entity.PracticeBase;
import edu.xpu.game.service.recreation.impl.PracticeBaseServiceImpl;
import edu.xpu.game.service.recreation.impl.PracticeInfoServiceImpl;
import edu.xpu.game.util.JsonUtil;
import edu.xpu.game.util.KeyUtil;
import edu.xpu.game.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @className PracticeBaseController
 * @description 实践基地控制器
 * @date 2019-06-06 18:57
 */
@RestController
@Slf4j
@RequestMapping("/practice")
public class PracticeBaseController {
    private final PracticeInfoServiceImpl practiceInfoService;
    private final PracticeBaseServiceImpl practiceBaseService;


    @Autowired
    public PracticeBaseController(PracticeBaseServiceImpl service,
                                  PracticeInfoServiceImpl practiceInfoService) {
        this.practiceBaseService = service;
        this.practiceInfoService = practiceInfoService;
    }

    //TODO 时区相差8小时的BUG
    //-----------------------用户接口---------------------
    /**
     * 用户报名实践基地
     * @param practiceBaseId 实践基地Id
     * @return 是否报名成功
     */
    @RequestMapping("/addPractice")
    public String userAdd(String practiceBaseId, HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userInfo");
        if(userId == null)
            return JsonUtil.toJson(ResultVOUtil.error(1, "未登录或信息不完善"));
        String addRet = practiceInfoService.userAdd(userId, practiceBaseId);
        if(addRet == null)
            return JsonUtil.toJson(ResultVOUtil.success());
        return JsonUtil.toJson(ResultVOUtil.error(2, addRet));
    }

    /**
     * 查看已经预约的实践基地
     * @return 预约的实践基地List
     */
    @RequestMapping("/showAllPractice")
    public String showAllPractice(HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userInfo");
        if(userId == null)
            return JsonUtil.toJson(ResultVOUtil.error(1, "未登录或信息不完善"));
        List<PracticeBase> findAllList = practiceInfoService.userAll(userId);
        return JsonUtil.toJson(ResultVOUtil.success(findAllList));
    }


    //-----------------------管理员接口---------------------
    @RequestMapping("/add")
    public String add(@RequestParam(value = "baseId", required = false) String baseId,
                      @RequestParam("baseName") String baseName,
                      @RequestParam("baseAddress") String baseAddress,
                      @RequestParam("baseDescription") String baseDescription,
                      @RequestParam("baseJoin") Integer baseJoin,
                      @RequestParam("baseMaxpeople") Integer baseMaxpeople,
                      @RequestParam("baseStart") Long baseStart,
                      @RequestParam("baseEnd") Long baseEnd,
                      @RequestParam("baseIcon") String baseIcon){
        if(baseId == null){
            baseId = KeyUtil.genUniqueKey();
        }
        PracticeBase base = practiceBaseService.find(baseId);
        if(base == null){
            base = new PracticeBase();
            base.setBaseId(KeyUtil.genUniqueKey());
        }
        base.setBaseName(baseName);
        base.setBaseAddress(baseAddress);
        base.setBaseDescription(baseDescription);
        base.setBaseJoin(baseJoin);
        base.setBaseMaxpeople(baseMaxpeople);
        base.setBaseStart(new Date(baseStart));
        base.setBaseEnd(new Date(baseEnd));
        base.setBaseIcon(baseIcon);

        Boolean addRet = practiceBaseService.add(base);
        return JsonUtil.toJson(addRet ? ResultVOUtil.success():
                ResultVOUtil.error(2,"失败"));
    }

    @RequestMapping("/find")
    public String find(String practiceBaseId){
        PracticeBase findRet = practiceBaseService.find(practiceBaseId);
        if(findRet != null)
            return JsonUtil.toJson(ResultVOUtil.success(findRet));
        return JsonUtil.toJson(ResultVOUtil.error(1, "未查到信息"));
    }


    @RequestMapping("/delete")
    public String delete(String practiceBaseId){
        practiceBaseService.delete(practiceBaseId);
        return JsonUtil.toJson(ResultVOUtil.success());
    }


    @RequestMapping("/showAll")
    public String showAll(){
        List<PracticeBase> findAllList = practiceBaseService.findAll();
        return JsonUtil.toJson(ResultVOUtil.success(findAllList));
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder bin){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor cust = new CustomDateEditor(sdf,true);
        bin.registerCustomEditor(Date.class,cust);
    }
}
