package xpu.edu.entity;

import lombok.Data;

/**
 * 评论
 */
@Data
public class Comment {
    //评论Id
    private String commit_id;
    //评论内容
    private String comment_content;
    //被评论书籍Id
    private String book_id;
    //评论者Id
    private String user_id;
}
