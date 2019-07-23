package xpu.edu.wind_trip.dao.impl;

import org.junit.Test;
import xpu.edu.wind_trip.domain.User;

public class UserDaoImplTest {
    private UserDaoImpl userDao = new UserDaoImpl();
    @Test
    public void findByUsername() {

    }

    @Test
    public void save() {
        User user = new User();
        user.setUid(2);
        user.setName("啦啦啦");
        user.setStatus("sss");
        user.setEmail("12312321@qq.com");
        user.setBirthday("sas");
        user.setCode("dsewd");
        user.setSex("男");
        user.setUsername("12345678");
        user.setPassword("23465678");

        userDao.save(user);
    }
}
