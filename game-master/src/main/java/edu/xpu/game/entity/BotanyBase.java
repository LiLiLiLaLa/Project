package edu.xpu.game.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @version 1.0
 * @className BotanyInfo
 * @description
 * @date 2019-06-07 14:00
 */
@Entity
@Data
public class BotanyBase {

    @Id
    private String botanyId;
    /**
     * 植物名称
     */
    private String botanyName;

    /**
     * 植物图片
     */
    private String botanyIcon;

    /**
     * 植物库存
     */
    private Integer botanyNum;

    /**
     * 植物描述
     */
    private String botanyDescription;
}
