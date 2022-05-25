package com.example.demo.src.recipe;
import com.example.demo.src.food.model.GetFoodRes;
import com.example.demo.src.recipe.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RecipeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetRecipeRes> getRecommendRecipe(int userIdx){
        String getRecipeQuery = "select R.Idx, R.recipeName, R.makeTime, GROUP_CONCAT(FI.foodName) foodHave\n" +
                "from Recipe R\n" +
                "join (select F.userIdx, F.foodName, I.recipeIdx, I.main #, F.expirationDate\n" +
                "     from Food F, Ingredient I\n" +
                "     where F.Idx = I.foodIdx\n" +
                "    ) FI on FI.recipeIdx = R.Idx\n" +
                "join User U on R.userIdx = U.Idx\n" +
                "where U.Idx = ?\n" +
                "group by R.Idx";

        return this.jdbcTemplate.query(getRecipeQuery,
                (rs,rowNum) -> new GetRecipeRes(
                        rs.getInt("Idx"),
                        rs.getString("recipeName"),
                        rs.getString("makeTime"),
                        rs.getString("foodHave")),
                userIdx);
    }

    public PostCreateNewRecipe createNewRecipe(PostRecipeReq postRecipeReq, int userIdx){
        String createRecipeQuery  = "insert into Recipe(recipeName, userIdx, detail, makeTime)\n" +
                "VALUES (?,?,?,?)";
        this.jdbcTemplate.update(createRecipeQuery, postRecipeReq.getRecipeName(), userIdx, postRecipeReq.getDetail(), postRecipeReq.getMakeTime());
        String lastInserIdQuery = "select last_insert_id()";

        PostRecipeRes postRecipeRes;
        postRecipeRes = new PostRecipeRes(postRecipeReq.getRecipeName(), userIdx, postRecipeReq.getDetail(), postRecipeReq.getMakeTime());

        PostCreateNewRecipe recipeIdxandRes;
        recipeIdxandRes = new PostCreateNewRecipe(this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class), postRecipeRes);

        return recipeIdxandRes;
    }
// 레시피 등록시 사진 첨부


// 레시피 등록시 링크 첨부

// 프론트랑 상의 후 진행 -> 사진, 링크 다 필수 아니라 예외처리 필요




}


