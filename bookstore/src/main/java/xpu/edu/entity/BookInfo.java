package xpu.edu.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 书籍
 */
@Data
public class BookInfo {
    //书籍编号
    private String book_id;
    //书籍分类
    private Integer book_category;
    //书籍作者
    private String book_author;
    //书籍单价
    private BigDecimal book_price;
    //书籍名字
    private String book_name;
}
