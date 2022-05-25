package com.example.demo.src.calender;

import com.example.demo.src.calender.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class CalenderDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetCalenderFoodRes> getCalenderFoodResList(int userIdx){
        String getC_FoodQuery = "select F.Idx,\n" +
                "       F.foodName,\n" +
                "       F.expirationDate\n" +
                "from Food F\n" +
                "join User U on U.Idx = F.userIdx\n" +
                "where U.Idx = ?";
        return this.jdbcTemplate.query(getC_FoodQuery,
                (rs,rowNum) -> new GetCalenderFoodRes(
                        rs.getInt("Idx"),
                        rs.getString("foodName"),
                        rs.getString("expirationDate")),
                userIdx);
    }




}
