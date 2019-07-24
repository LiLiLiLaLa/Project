package edu.xpu.game.repository;

import edu.xpu.game.entity.MsgQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @version 1.0
 * @className MsgQueueRepository
 * @description
 * @date 2019-06-05 13:39
 */
public interface MsgQueueRepository extends JpaRepository<MsgQueue, String> {
    List<MsgQueue> findAllByMsgTo(String msgTo);
    List<MsgQueue> findAllByMsgToAndMsgRead(String msgTo, Integer msgRead);
}
