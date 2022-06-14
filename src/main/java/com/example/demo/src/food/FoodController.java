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
        try{
            PostFoodRes postFoodRes = foodService.postFood(postFoodReq, userIdx);
            return new BaseResponse<>(postFoodRes);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}

