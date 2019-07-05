package xpu.edu.dao.impl;

import org.junit.Test;
import xpu.edu.dao.BookInfoDao;
import xpu.edu.entity.BookInfo;

import java.math.BigDecimal;
import java.util.List;

public class BookInfoDaoImplTest {
    private BookInfoDao bookInfoDao = new BookInfoDaoImpl();

    @Test
    public void addBook(){
        //封装一个对象，代表数据库里面的一条数据
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBook_author("施耐庵");
        bookInfo.setBook_id("005");
        bookInfo.setBook_name("水浒传");
        bookInfo.setBook_category(5);
        bookInfo.setBook_price(new BigDecimal(64.00));
        //使用数据库Dao层对象增加一条数据
        Boolean aBoolean = bookInfoDao.addBook(bookInfo);
        System.out.println(aBoolean);
    }

    @Test
    public void deleteBook(){
        bookInfoDao.deleteBook("003");
    }

    @Test
    public void findBookById(){
        BookInfo bookById = bookInfoDao.findBookById("001");
        System.out.println(bookById);
    }

    @Test
    public void findBookByName(){
        List<BookInfo> list = bookInfoDao.findBookByName("红楼梦");
        System.out.println(list);
    }

    @Test
    public void updateBook(){
        //封装一个对象，代表要更新的一条数据
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBook_author("施耐庵");
        bookInfo.setBook_id("004");
        bookInfo.setBook_name("三国演义");
        bookInfo.setBook_category(5);
        bookInfo.setBook_price(new BigDecimal(84.00));
        Boolean aBoolean = bookInfoDao.updateBook(bookInfo);
        System.out.println(aBoolean);
    }
}
