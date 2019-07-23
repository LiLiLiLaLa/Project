package xpu.edu.wind_trip.dao.impl;

import com.alibaba.druid.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import xpu.edu.wind_trip.dao.UserDao;
import xpu.edu.wind_trip.domain.User;
import xpu.edu.wind_trip.util.JDBCUtils;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username) {
        User user = null;
        try{
            //定义sql语句
            String sql = "select * from tab_user where username=?";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) {
        //定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email)" +
                " value(?,?,?,?,?,?,?)";
        //执行sql
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail());
    }
}
