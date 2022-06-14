package com.example.demo.src.food;

import com.example.demo.config.BaseException;
import com.example.demo.src.food.model.*;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FoodService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FoodDao foodDao;
    private final FoodProvider foodProvider;
    private final JwtService jwtService;


    @Autowired
    public FoodService(FoodDao foodDao, FoodProvider foodProvider, JwtService jwtService) {
        this.foodDao = foodDao;
        this.foodProvider = foodProvider;
        this.jwtService = jwtService;
    }

    // 식재료 추가 서비스
    public PostFoodRes postFood(PostFoodReq postFoodReq,int userIdx) throws BaseException{
        try{
            int foodIdx = foodDao.postFoods(postFoodReq, userIdx);

            return  new PostFoodRes(foodIdx, postFoodReq.getFoodName(), postFoodReq.getFoodPhoto(), postFoodReq.getCategoryIdx(), postFoodReq.getAmount(), postFoodReq.getStorageType(), postFoodReq.getExpirationDate());
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }



}

