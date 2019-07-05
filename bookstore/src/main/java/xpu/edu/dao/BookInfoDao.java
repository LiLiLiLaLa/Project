package xpu.edu.dao;

import xpu.edu.entity.BookInfo;

import java.sql.SQLException;
import java.util.List;

public interface BookInfoDao {
    /**
     * 添加书籍
     */
    Boolean addBook(BookInfo bookInfo) ;

    /**
     * 删除书籍
     */
    void deleteBook(String bookId);

    /**
     * 根据Id查找书籍
     */
    BookInfo findBookById (String bookId);
    /**
     * 根据名称查找图书
     */
    List<BookInfo> findBookByName(String bookName);
    /**
     * 更新图书信息
     */
    Boolean updateBook(BookInfo bookInfo);
}