package edu.xpu.game.repository;

import edu.xpu.game.entity.PracticeBase;
import edu.xpu.game.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PracticeBaseRepositoryTest {

    @Autowired
    private PracticeBaseRepository repository;
    @Test
    public void save(){
        PracticeBase practiceBase = new PracticeBase();
        practiceBase.setBaseId(KeyUtil.genUniqueKey());
        practiceBase.setBaseAddress("陕西省西安市临潼区");
        practiceBase.setBaseName("临潼XX小学指教活动");
        practiceBase.setBaseDescription("临潼XX小学指教活动 临潼XX小学指教活动 临潼XX小学指教活动 临潼XX小学指教活动");
        practiceBase.setBaseJoin(2);
        practiceBase.setBaseMaxpeople(100);
        practiceBase.setBaseIcon("http:....png");
        practiceBase.setBaseStart(new Date());
        practiceBase.setBaseEnd(new Date());

        PracticeBase save = repository.save(practiceBase);
        assertNotNull(save);
    }
}