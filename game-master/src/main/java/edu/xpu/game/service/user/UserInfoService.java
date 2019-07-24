package edu.xpu.game.service.user;

import edu.xpu.game.entity.UserInfo;

import java.util.Optional;

/**
 * @ClassName UserInfoService
 * @Description
 * @Date 2019-05-23 11:50
 * @Version 1.0
 */
public interface UserInfoService {
    Optional<UserInfo> findById(String id);

    UserInfo userLogin(String id, String password);

    UserInfo save(UserInfo userInfo);

    Integer getUserCount();
}
