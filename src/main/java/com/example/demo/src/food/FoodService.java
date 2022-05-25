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

}
