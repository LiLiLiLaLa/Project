package edu.xpu.game.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName ActivityInfo
 * @Description
 * @Date 2019-05-22 11:23
 * @Version 1.0
 */
@Entity
@Data
public class ActivityInfo {
    @Id
    private String activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动描述
     */
    private String activityDescribe;

    /**
     * 活动宣传图
     */
    private String activityIcon;
}
