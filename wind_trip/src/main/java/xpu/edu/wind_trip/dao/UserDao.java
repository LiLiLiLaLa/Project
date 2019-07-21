package xpu.edu.wind_trip.dao;

import xpu.edu.wind_trip.domain.User;

public interface UserDao {
    //根据用户名查询用户信息
    public User findByUsername(String username);
    //用户保存
    public void save(User user);
}
