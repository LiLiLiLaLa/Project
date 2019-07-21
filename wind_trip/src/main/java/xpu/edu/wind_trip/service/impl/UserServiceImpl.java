package xpu.edu.wind_trip.service.impl;

import xpu.edu.wind_trip.dao.UserDao;
import xpu.edu.wind_trip.dao.impl.UserDaoImpl;
import xpu.edu.wind_trip.domain.User;
import xpu.edu.wind_trip.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    //注册用户
    @Override
    public boolean regist(User user) {
        //根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //判断u是否为null才保存
        if(u != null){
            return false;
        }
        //保存用户信息
        userDao.save(user);
        return true;
    }
}
