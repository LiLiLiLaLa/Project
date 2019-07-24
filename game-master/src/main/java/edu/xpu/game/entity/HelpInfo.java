package edu.xpu.game.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName HelpInfo
 * @Description 帮扶信息
 * @Date 2019-05-22 11:20
 * @Version 1.0
 */
@Data
@Entity
public class HelpInfo {
    @Id
    private String helpId;

    /**
     * 帮扶信息标题
     */
    private String helpTitle;

    /**
     * 帮扶信息描述
     */
    private String helpDescribe;

    /**
     * 帮扶Logo
     */
    private String helpIcon;

    /**
     * 0、请求帮扶 1、提供帮扶
     */
    private Integer helpType;

}
