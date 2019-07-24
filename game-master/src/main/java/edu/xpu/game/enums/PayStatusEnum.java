package edu.xpu.game.enums;

import lombok.Getter;

/**
 * @ClassName PayStatusEnum
 * @Description 支付状态描述
 * @Date 2019-05-22 11:12
 * @Version 1.0
 */
@Getter
public enum PayStatusEnum implements CodeEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

