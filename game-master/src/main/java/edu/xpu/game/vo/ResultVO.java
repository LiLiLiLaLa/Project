package edu.xpu.game.vo;

import lombok.Data;

/**
 * @ClassName ResultVO
 * @Description
 * @Date 2019-05-24 22:18
 * @Version 1.0
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;
}
