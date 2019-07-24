package edu.xpu.game.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @version 1.0
 * @className MsgQueue
 * @description
 * @date 2019-06-05 13:28
 */
@Data
@Entity
public class MsgQueue {
    @Id
    private String msgId;

    /**
     * 消息内容
     */
    private String msgMsg;

    /**
     * 谁发的 UserId
     */
    private String msgFrom;

    /**
     * 发给谁 UserId
     */
    private String msgTo;

    /**
     * 下一条消息的Id
     */
    private String msgNext;

    /**
     * 是否已读
     */
    private Integer msgRead;
}
