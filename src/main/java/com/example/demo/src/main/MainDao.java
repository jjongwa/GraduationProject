package com.example.demo.src.main;

import com.example.demo.src.main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class MainDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetMainFoodRes> getMainFoodResList(int userIdx){
        System.out.println("foodDao");
        String getListQuery = "select F.Idx,\n" +
                "                      F.foodName,\n" +
                "                      F.foodPhoto,\n" +
                "                      F.amount,\n" +
                "                      F.storageType, \n" +
                "                       F.expirationDate,\n" +
                "                     case when 31 > timestampdiff(DAY , current_timestamp, F.expirationDate)\n" +
                "                            then concat(timestampdiff(Day , current_timestamp, F.expirationDate), '일 남음')\n" +
                "                          when 12 > timestampdiff(MONTH , current_timestamp, F.expirationDate)\n" +
                "                            then concat(timestampdiff(MONTH , current_timestamp, F.expirationDate), '달 남음')\n" +
                "                            else concat(timestampdiff(YEAR , current_timestamp, F.expirationDate), '년 남음')\n" +
                "                          end ED_Left\n" +
                "                from Food F\n" +
                "                where F.userIdx = ?\n" +
                "                order by expirationDate";
        int GetUserIdx = userIdx;
        return this.jdbcTemplate.query(getListQuery,
                (rs,rowNum) -> new GetMainFoodRes(
                        rs.getInt("Idx"),
                        rs.getString("foodName"),
                        rs.getString("foodPhoto"),
                        rs.getInt("amount"),
                        rs.getInt("storageType"),
                        rs.getString("expirationDate"),
                        rs.getString("ED_Left")),
                GetUserIdx);

    }

    public List<GetMainRecipeRes> getMainRecipeResList(int userIdx){
        System.out.println("RecipeDao");
        String getListQuery ="select R.Idx, R.recipeName, GROUP_CONCAT(FI.foodName) foodHave\n" +
                "from Recipe R\n" +
                "join (select F.userIdx, F.foodName, I.recipeIdx, I.main #, F.expirationDate\n" +
                "     from Food F, Ingredient I\n" +
                "     where F.Idx = I.foodIdx\n" +
                "    ) FI on FI.recipeIdx = R.Idx\n" +
                "join User U on R.userIdx = U.Idx\n" +
                "where U.Idx = ?\n" +
                "group by R.Idx;";
        return this.jdbcTemplate.query(getListQuery,
                (rs,rowNum) -> new GetMainRecipeRes(
                        rs.getInt("Idx"),
                        rs.getString("recipeName"),
                        rs.getString("foodHave")),
                userIdx);
    }



}
