package edu.xpu.game.exception;

import edu.xpu.game.enums.ResultEnum;

/**
 * @ClassName SellException
 * @Description
 * @Date 2019-05-26 14:52
 * @Version 1.0
 */
public class SellException extends RuntimeException{
    private Integer code;
    String

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
