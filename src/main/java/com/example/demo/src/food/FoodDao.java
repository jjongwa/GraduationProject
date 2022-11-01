package com.example.demo.src.food;

import com.example.demo.src.food.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
@Repository
public class FoodDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 식재료 리스트 쿼리
    public List<GetFoodRes> getFoods(int userIdx){
        //System.out.println("다오");
        //System.out.println(userIdx);
        String getFoodsQuery ="select F.Idx, F.foodName, F.foodPhoto, F.categoryIdx, F.amount, F.storageType, DATE(F.expirationDate) as expirationDate ,\n" +
                "     case when 0 > timestampdiff(DAY , current_timestamp, F.expirationDate)\n" +
                "            then timestampdiff(DAY , current_timestamp, F.expirationDate)\n" +
                "        when 0 = timestampdiff(DAY , current_timestamp, F.expirationDate) && 0 > timestampdiff(SECOND , current_timestamp, F.expirationDate)\n" +
                "            then -1\n" +
                "        else timestampdiff(DAY , current_timestamp, F.expirationDate)\n" +
                "          end ED_Left\n" +
                "from Food F\n" +
                "where F.userIdx = ? and F.status = 1\n" +
                "order by expirationDate";
        int GetUserIdx = userIdx;
        return this.jdbcTemplate.query(getFoodsQuery,
                (rs,rowNum) -> new GetFoodRes(
                        rs.getInt("Idx"),
                        rs.getString("foodName"),
                        rs.getString("foodPhoto"),
                        rs.getInt("categoryIdx"),
                        rs.getInt("amount"),
                        rs.getInt("storageType"),
                        rs.getString("expirationDate"),
                        rs.getInt("ED_Left")),
                GetUserIdx);
    }

    // 식재료 추가 쿼리
    public int postFoods(PostFoodReq postFoodReq, int userIdx){
        String postFoodQuery = "insert into Food(userIdx, foodName, foodPhoto, categoryIdx, amount, storageType, expirationDate)\n" +
                "VALUES (?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(postFoodQuery, userIdx, postFoodReq.getFoodName(), postFoodReq.getFoodPhoto(), postFoodReq.getCategoryIdx(), postFoodReq.getAmount(), postFoodReq.getStorageType(),postFoodReq.getExpirationDate());
        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    }

    //식재료 수정 쿼리
    public int updateFoods(PostFoodReq postFoodReq, int foodIdx){
        System.out.println("위치3");
        String updateFoodQuery = "update Food F\n" +
                "set F.foodName = ?,\n" +
                "    F.foodPhoto = ?,\n" +
                "    F.categoryIdx = ?,\n" +
                "    F.amount = ?,\n" +
                "    F.storageType = ?,\n" +
                "    F.expirationDate = ?\n" +
                "where F.Idx = ?";
        this.jdbcTemplate.update(updateFoodQuery, postFoodReq.getFoodName(), postFoodReq.getFoodPhoto(), postFoodReq.getCategoryIdx(), postFoodReq.getAmount(), postFoodReq.getStorageType(), postFoodReq.getExpirationDate(), foodIdx);

        System.out.println("위치4");
        return foodIdx;
    }

    //식재료 삭제(비활성화) 쿼리
    public void deleteFood(int foodIdx) {
        System.out.println("삭제 Dao호출");
        String deleteFoodQuery = "update Food F\n" +
                "set F.status = 2\n" +
                "where F.Idx = ?";
        this.jdbcTemplate.update(deleteFoodQuery, foodIdx);
        System.out.println("삭제 Dao리턴");
    }

    //식재료 본인 확인 쿼리
    public int checkMyFood(int userIdx, int foodIdx) {
        System.out.println("체크 Dao호출");
        String checkMyFoodQuery = "select exists (select *\n" +
                "from Food F\n" +
                "where F.userIdx = ? and F.Idx =?) as isMyFood";
        System.out.println("체크 Dao호출2");
        return this.jdbcTemplate.queryForObject(checkMyFoodQuery, int.class, userIdx, foodIdx);
    }
}

