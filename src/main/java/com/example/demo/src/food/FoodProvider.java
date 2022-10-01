package com.example.demo.src.food;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.food.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FoodProvider {
    private final FoodDao foodDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FoodProvider(FoodDao foodDao, JwtService jwtService) {
        this.foodDao = foodDao;
        this.jwtService = jwtService;
    }

    public List<GetFoodRes> getFoods(int userIdx) throws BaseException{
        try{
            //System.out.println("프로바이더");
            List<GetFoodRes> getFoodRes = foodDao.getFoods(userIdx);
            //System.out.println("다오 통과");
            return getFoodRes;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }






}
