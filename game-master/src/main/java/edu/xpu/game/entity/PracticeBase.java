package edu.xpu.game.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @version 1.0
 * @className PracticeBase
 * @description 实践基地表
 * @date 2019-06-06 18:39
 */
@Entity
@Data
@DynamicUpdate
public class PracticeBase {
    @Id
    private String baseId;

    /**
     * 实践基地名称
     */
    private String baseName;

    /**
     * 实践基地地址
     */
    private String baseAddress;

    /**
     * 实践基地描述
     */
    private String baseDescription;

    /**
     * 已经加入人数
     */
    private Integer baseJoin;

    /**
     * 最大加入人数
     */
    private Integer baseMaxpeople;


    /**
     * 实践活动开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date baseStart;

    /**
     * 实践活动结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date baseEnd;

    /**
     * 实践基地宣传图
     */
    private String baseIcon;
}
