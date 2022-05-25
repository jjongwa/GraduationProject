package com.example.demo.src.calender;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.calender.model.*;
import com.example.demo.src.food.FoodDao;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CalenderProvider {
    private final CalenderDao calenderDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CalenderProvider(CalenderDao calenderDao, JwtService jwtService) {
        this.calenderDao = calenderDao;
        this.jwtService = jwtService;
    }

    public List<GetCalenderFoodRes> getCalenderFoodResList(int userIdx) throws BaseException{
        try{
            List<GetCalenderFoodRes> getCalenderFoodResList = calenderDao.getCalenderFoodResList(userIdx);
            return getCalenderFoodResList;
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
