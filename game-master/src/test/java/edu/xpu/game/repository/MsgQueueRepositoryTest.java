package edu.xpu.game.repository;

import edu.xpu.game.entity.MsgQueue;
import edu.xpu.game.util.KeyUtil;
import edu.xpu.game.util.MathUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MsgQueueRepositoryTest {

    @Autowired
    private MsgQueueRepository repository;
    @Test
    public void save(){
        MsgQueue msgQueue = new MsgQueue();
        msgQueue.setMsgId(KeyUtil.genUniqueKey());
        msgQueue.setMsgMsg("我的用户信息为什么是空？");
        msgQueue.setMsgNext(KeyUtil.genUniqueKey());
        msgQueue.setMsgFrom("00001");
        msgQueue.setMsgTo("00000");
        msgQueue.setMsgRead(0);
        MsgQueue save = repository.save(msgQueue);
        assertNotNull(save);
    }
}