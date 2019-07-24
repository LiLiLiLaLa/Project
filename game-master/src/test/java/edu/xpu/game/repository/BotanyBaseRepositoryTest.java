package edu.xpu.game.repository;

import edu.xpu.game.entity.BotanyBase;
import edu.xpu.game.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BotanyBaseRepositoryTest {
    @Autowired
    private BotanyBaseRepository repository;

    @Test
    public void save(){
        BotanyBase botanyInfo = new BotanyBase();
        botanyInfo.setBotanyId(KeyUtil.genUniqueKey());
        botanyInfo.setBotanyName("西双版纳黑蕉");
        botanyInfo.setBotanyDescription("西双版纳黑蕉的描述信息");
        botanyInfo.setBotanyNum(99);
        botanyInfo.setBotanyIcon("http:wwsw.sw.sw.png");
        BotanyBase save = repository.save(botanyInfo);
        assertNotNull(save);
    }
}