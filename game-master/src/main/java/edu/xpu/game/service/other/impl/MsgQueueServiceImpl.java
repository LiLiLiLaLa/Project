package edu.xpu.game.service.other.impl;

import edu.xpu.game.entity.MsgQueue;
import edu.xpu.game.repository.MsgQueueRepository;
import edu.xpu.game.service.other.MsgQueueService;
import edu.xpu.game.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @version 1.0
 * @className MsgQueueImpl
 * @description
 * @date 2019-06-05 13:51
 */
@Slf4j
@Service
public class MsgQueueServiceImpl implements MsgQueueService {
    private final MsgQueueRepository msgRepository;

    @Autowired
    public MsgQueueServiceImpl(MsgQueueRepository msgRepository) {
        this.msgRepository = msgRepository;
    }


    @Override
    public Boolean sendMsg(String fromUserId, String toUserId, String msg) {
        List<MsgQueue> findRetList = msgRepository.findAllByMsgToAndMsgRead(fromUserId, 1);
        MsgQueue msgQueue = new MsgQueue();
        if(findRetList.isEmpty()){
            msgQueue.setMsgId(KeyUtil.genUniqueKey());
        }else{
            String msgNext = findRetList.get(findRetList.size() - 1).getMsgNext();
            Optional<MsgQueue> byId = msgRepository.findById(msgNext);
            if(byId.isPresent()){
                msgQueue.setMsgId(KeyUtil.genUniqueKey());
            }else{
                msgQueue.setMsgId(msgNext == null ? KeyUtil.genUniqueKey() : msgNext);
            }
        }
        msgQueue.setMsgFrom(fromUserId);
        msgQueue.setMsgTo(toUserId);
        msgQueue.setMsgMsg(msg);
        msgQueue.setMsgRead(0);
        msgQueue.setMsgNext(KeyUtil.genUniqueKey());
        return msgRepository.save(msgQueue) != null;
    }

    @Override
    public List<MsgQueue> acceptMsg(String userId) {
        List<MsgQueue> allByMsgTo = msgRepository.findAllByMsgTo(userId);
        log.info("allByMsgTo", allByMsgTo.toString());
        for(MsgQueue msgQueue: allByMsgTo){
            msgQueue.setMsgRead(1);
            MsgQueue save = msgRepository.save(msgQueue);
            assert save != null;
        }
        return allByMsgTo;
    }
}
