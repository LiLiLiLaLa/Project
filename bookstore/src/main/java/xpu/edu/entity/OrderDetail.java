package xpu.edu.entity;

import lombok.Data;

/**
 * 订单详情
 */
@Data
public class OrderDetail {
    //订单详情Id
    private String detail_id;
    //主订单Id
    private String master_id;
    //购买图书Id
    private String book_id;
    //购买图书数量
    private Integer detail_num;
}
