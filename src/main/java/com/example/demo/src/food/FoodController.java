package com.example.demo.src.food;
import com.example.demo.src.food.FoodProvider;
import com.example.demo.src.food.FoodService;
import com.example.demo.src.user.model.PostLoginReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.food.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;



@RestController
@RequestMapping("/app/foods")
public class FoodController  {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FoodProvider foodProvider;
    @Autowired
    private final FoodService foodService;
    @Autowired
    private final JwtService jwtService;


    public FoodController(FoodProvider foodProvider, FoodService foodService, JwtService jwtService){
        this.foodProvider = foodProvider;
        this.foodService = foodService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("{userIdx}")
    public BaseResponse<List<GetFoodRes>> getFoods(@PathVariable("userIdx") int userIdx){
        try{
            //System.out.println("컨트롤러");
            List<GetFoodRes> getFoodRes = foodProvider.getFoods(userIdx);
            return new BaseResponse<>(getFoodRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("{userIdx}")
    public BaseResponse<PostFoodRes> createFood(@RequestBody PostFoodReq postFoodReq, @PathVariable("userIdx") int userIdx){
        //식재료의 이름을 넣지 않았을 때
        if (postFoodReq.getFoodName() == null || postFoodReq.getFoodName().length() == 0) {
            return new BaseResponse<>(FOOD_EMPTY_NAME);
        }
        //식재료의 카테고리를 넣지 않았을 때
        if (postFoodReq.getCategoryIdx() == 0) {
            return new BaseResponse<>(FOOD_EMPTY_CATEGORY_ID);
        }
        //식재료의 수량을 넣지 않았을 때
        if (postFoodReq.getAmount() == 0) {
            return new BaseResponse<>(FOOD_EMPTY_AMOUNT);
        }
        //식재료의 보관방법을 넣지 않았을 때
        if (postFoodReq.getStorageType() == 0) {
            return new BaseResponse<>(FOOD_EMPTY_STORAGE_TYPE);
        }
        //식재료의 유통기한을 넣지 않았을 때
        if (postFoodReq.getExpirationDate() == null) {
            return new BaseResponse<>(FOOD_EMPTY_EXPIRATION_DATE);
        }

        try{
            PostFoodRes postFoodRes = foodService.postFood(postFoodReq, userIdx);
            return new BaseResponse<>(postFoodRes);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}/{foodIdx}/update")
    public BaseResponse<PostFoodRes> updateFood(@RequestBody PostFoodReq postFoodReq, @PathVariable("userIdx") int userIdx, @PathVariable("foodIdx") int foodIdx){
        try{
            System.out.println("위치1");
            PostFoodRes postFoodRes = foodService.updateFood(postFoodReq, userIdx, foodIdx);
            System.out.println(postFoodRes.getFoodName());
            System.out.println(postFoodRes.getFoodPhoto());
            return new BaseResponse<>(postFoodRes);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}/{foodIdx}/delete")
    public BaseResponse<String> deleteFood(@PathVariable("userIdx") int userIdx, @PathVariable("foodIdx") int foodIdx){
        try{
            int deleteFood = foodService.deleteFood(userIdx, foodIdx);
            if(deleteFood == 0){
                throw new BaseException(INVALID_USER_JWT);
            }
            else {
                return new BaseResponse<>("상품 삭제 성공");
            }
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}

