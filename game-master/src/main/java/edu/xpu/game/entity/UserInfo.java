package edu.xpu.game.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName UserInfo
 * @Description 买家信息描述
 * @Date 2019-05-22 10:56
 * @Version 1.0
 */
@Entity
@Data
@DynamicUpdate
public class UserInfo {
    @Id
    private String userId;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 用户信息注册时间
     */
    private Date createTime;

    /**
     * 信息修改时间
     */
    private Date updateTime;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 是否是管理员 0 是、1 不是
     */
    private Integer userIsman;
}
