package com.example.demo.src.main;

import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class MainProvider {
    private final MainDao mainDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MainProvider(MainDao mainDao, JwtService jwtService){
        this.mainDao = mainDao;
        this.jwtService = jwtService;
    }

    public List<String> getMainpage(int userIdx) throws BaseException{

        try{
            System.out.println("메인 프로바이더");
            List MainFoodList = mainDao.getMainFoodResList(userIdx);
            List MainRecipeList = mainDao.getMainRecipeResList(userIdx);
            List MainpageData = new ArrayList<>(Arrays.asList(MainFoodList, MainRecipeList));

            return MainpageData;
        }
        catch (Exception exception){
            throw  new BaseException(DATABASE_ERROR);
        }
    }




}
