package edu.xpu.game.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @version 1.0
 * @className BotanyInfo
 * @description
 * @date 2019-06-07 15:15
 */
@Entity
@Data
@DynamicUpdate
public class BotanyInfo {

    @Id
    private String adoptId;

    private String adoptUser;

    private String adoptBotany;

    private Date adoptCreateTime;
}
