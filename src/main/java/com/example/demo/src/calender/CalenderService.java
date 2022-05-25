package com.example.demo.src.calender;

import com.example.demo.config.BaseException;
import com.example.demo.src.calender.model.*;
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
public class CalenderService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CalenderDao calenderDao;
    private final CalenderProvider calenderProvider;
    private final JwtService jwtService;


    @Autowired
    public CalenderService(CalenderDao calenderDao, CalenderProvider calenderProvider, JwtService jwtService) {
        this.calenderDao = calenderDao;
        this.calenderProvider = calenderProvider;
        this.jwtService = jwtService;
    }





}
