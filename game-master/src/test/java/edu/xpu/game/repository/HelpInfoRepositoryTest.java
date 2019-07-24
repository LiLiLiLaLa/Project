package edu.xpu.game.repository;

import edu.xpu.game.entity.HelpInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelpInfoRepositoryTest {

    @Autowired
    private HelpInfoRepository repository;

    @Test
    public void save(){
        HelpInfo helpInfo = new HelpInfo();
        helpInfo.setHelpId(String.valueOf(System.currentTimeMillis()));
        helpInfo.setHelpDescribe("正文");
        helpInfo.setHelpTitle("标题");
        helpInfo.setHelpIcon("");
        helpInfo.setHelpType(1);
        HelpInfo save = repository.save(helpInfo);
        assertNotNull(save);


        HelpInfo helpInfo2 = new HelpInfo();
        helpInfo2.setHelpId(String.valueOf(System.currentTimeMillis()));
        helpInfo2.setHelpDescribe("正文2");
        helpInfo2.setHelpTitle("标题2");
        helpInfo2.setHelpIcon("");
        helpInfo2.setHelpType(2);
        HelpInfo save2 = repository.save(helpInfo2);
        assertNotNull(save2);
    }

    @Test
    public void delete(){
        repository.deleteById("1558580871542");
    }
}