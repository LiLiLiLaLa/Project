package xpu.edu.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import xpu.edu.dao.BookInfoDao;
import xpu.edu.entity.BookInfo;
import xpu.edu.util.JDBCUtil;

import java.sql.*;
import java.util.List;

public class BookInfoDaoImpl implements BookInfoDao {
    @Override
    public Boolean addBook(BookInfo bookInfo)  {
        QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "INSERT INTO book_info VALUES(?,?,?,?,?)";
        try {
            int insertResult = queryRunner.update(sql,
                    bookInfo.getBook_id(),
                    bookInfo.getBook_name(),
                    bookInfo.getBook_author(),
                    bookInfo.getBook_category(),
                    bookInfo.getBook_price());
            return insertResult > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteBook(String bookId) {
        QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "DELETE FROM book_info WHERE book_id = ?";
        try {
            queryRunner.update(sql,bookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BookInfo findBookById(String bookId) {
        QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "SELECT * FROM book_info WHERE book_id = '"+bookId+"'";
        BeanHandler<BookInfo> beanHandler = new BeanHandler<>(BookInfo.class);
        try {
            BookInfo query = queryRunner.query(sql, beanHandler);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BookInfo> findBookByName(String bookName) {
        QueryRunner queryRunner = new QueryRunner(JDBCUtil.getDataSource());
        String sql = "SELECT * FROM book_info WHERE book_name LIKE '%" + bookName +"%'";
        BeanListHandler<BookInfo> bookInfoBeanListHandler = new BeanListHandler<>(BookInfo.class);
        try {
            List<BookInfo> query = queryRunner.query(sql, bookInfoBeanListHandler);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean updateBook(BookInfo bookInfo) {
        BookInfo bookById = findBookById(bookInfo.getBook_id());
        if(bookById != null){
            deleteBook(bookInfo.getBook_id());
            return addBook(bookInfo);
        }else{
            return null;
        }
    }
}
