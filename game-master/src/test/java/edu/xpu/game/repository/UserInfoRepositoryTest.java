package edu.xpu.game.repository;

import edu.xpu.game.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository repository;

    @Test
    public void save(){
        for (int i = 0; i <= 5; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId("0000"+i);
            userInfo.setUserPassword("123456");
            userInfo.setUserName("张三");
            userInfo.setUserPhone("1377727182");
            userInfo.setUserAddress("8号楼A120");
            UserInfo save = repository.save(userInfo);
            assertNotNull(save);
        }
    }

    @Test
    public void delete(){
        repository.deleteById("00005");
    }

    @Test
    public void update(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("00000");
        userInfo.setUserPassword("123456");
        userInfo.setUserName("李四");
        userInfo.setUserPhone("1377727182");
        userInfo.setUserAddress("8号楼A120");
        UserInfo save = repository.save(userInfo);
        assertNotNull(save);
    }

    @Test
    public void find(){
        List<UserInfo> all = repository.findAll();
        assertEquals(all.size(), 5);
    }
}