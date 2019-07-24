package edu.xpu.game.service.user.impl;

import edu.xpu.game.entity.UserInfo;
import edu.xpu.game.repository.UserInfoRepository;
import edu.xpu.game.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName UserInfoServiceImpl
 * @Description
 * @Date 2019-05-24 22:24
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository repository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserInfo> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public UserInfo userLogin(String id, String password) {
        Optional<UserInfo> retInfo = repository.findById(id);
        if(retInfo.isPresent() && password.equals(retInfo.get().getUserPassword())){
            return retInfo.get();
        }
        return null;
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return repository.save(userInfo);
    }

    @Override
    public Integer getUserCount() {
        return repository.findAll().size();
    }
}
