package edu.xpu.game.controller.user;

import com.alibaba.fastjson.JSONObject;
import edu.xpu.game.dto.UserInfoDTO;
import edu.xpu.game.entity.UserInfo;
import edu.xpu.game.service.user.UserInfoService;
import edu.xpu.game.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @ClassName UserInfoController
 * @Description 用户登录注册控制器
 * @Date 2019-05-23 11:49
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserInfoController {
    private final UserInfoService userInfoservice;

    @Autowired
    public UserInfoController(UserInfoService userInfoservice) {
        this.userInfoservice = userInfoservice;
    }

    @RequestMapping("/login")
    public String userLogin(@RequestParam("userId") String userId,
                            @RequestParam("userPassword") String userPassword,
                            HttpServletRequest request){

        UserInfo userInfo = userInfoservice.userLogin(userId, userPassword);
        ResultVO<UserInfoDTO> resultVO = new ResultVO<>();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        if(userInfo == null){

            resultVO.setCode(1);
            resultVO.setMsg("用户名或密码错误");
            resultVO.setData(null);
        }else{
            BeanUtils.copyProperties(userInfo, userInfoDTO);
            resultVO.setCode(0);
            resultVO.setMsg("登陆成功");
            resultVO.setData(userInfoDTO);
            request.getSession().setAttribute("userInfo", userInfoDTO.getUserId());
        }
        return JSONObject.toJSONString(resultVO, true);
    }

    @RequestMapping("/logout")
    public String userLogout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        ResultVO<String> resultVO = new ResultVO<>();
        if(session == null){
            resultVO.setCode(1);
            resultVO.setMsg("没有需要移除的session");
        }else{
            session.removeAttribute("userInfo");
            resultVO.setCode(0);
            resultVO.setMsg("移除session成功");
        }
        return JSONObject.toJSONString(resultVO, true);
    }

    @RequestMapping("/register")
    public String userRegister(@ModelAttribute UserInfoDTO userInfoDTO){
        String userId = userInfoDTO.getUserId();
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        Optional<UserInfo> byId = userInfoservice.findById(userId);
        ResultVO<UserInfoDTO> vo = new ResultVO<>();

        if(userInfoDTO.getUserIsman() == 0){
            vo.setCode(3);
            vo.setMsg("不允许注册管理员");
            return JSONObject.toJSONString(vo, true);
        }

        if(byId.isPresent()){
            vo.setCode(1);
            vo.setMsg("此账号已存在");
            vo.setData(null);
        }else{
            vo.setCode(0);
            vo.setMsg("注册成功");
            vo.setData(userInfoDTO);
            UserInfo save = userInfoservice.save(userInfo);
            log.info("[user.register] = {}", save);
        }
        return JSONObject.toJSONString(vo, true);
    }


    @RequestMapping("/wordCount")
    public String userWordCount(@RequestParam("word") String word){
        ResultVO<Integer> ret = new ResultVO<>();
        ret.setCode(0);
        ret.setMsg("统计成功");
        ret.setData(word.length());
        return JSONObject.toJSONString(ret, true);
    }

    @RequestMapping("/userCount")
    public String userCount(){
        ResultVO<Integer> ret = new ResultVO<>();
        Integer userCount = userInfoservice.getUserCount();
        log.info("[user.userCount] = {}", userCount);
        ret.setCode(0);
        ret.setMsg("统计成功");
        ret.setData(userCount);
        return JSONObject.toJSONString(ret, true);
    }
}