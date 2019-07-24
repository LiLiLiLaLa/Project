package edu.xpu.game.enums;

import lombok.Getter;

@Getter
public enum PickCheckStatus implements CodeEnum {

    NEW(0, "新预约"),
    SUCCESS(1, "审核通过"),
    FILED(2, "未通过"),
    CANCEL(3, "已取消")
    ;

    private Integer code;

    private String message;

    PickCheckStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public static Boolean contain(Integer status){
        PickCheckStatus[] values = PickCheckStatus.values();
        for(PickCheckStatus v: values){
            if(v.code.equals(status)) return true;
        }
        return false;
    }
}