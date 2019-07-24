package edu.xpu.game.dto;

import lombok.Data;

/**
 * @ClassName UserInfoDTO
 * @Description
 * @Date 2019-05-24 23:16
 * @Version 1.0
 */

@Data
public class UserInfoDTO {
    private String userId;

    /**
     * 用户密码
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
     * 用户头像
     */
    private String userIcon;


    /**
     * 是否是管理员
     */
    private Integer userIsman;
}
