package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        String userId = postLoginReq.getUserId();
        String userPw = postLoginReq.getUserPw();
        if(userDao.checkUserId(userId) == 0){   // 아이디 확인
            throw new BaseException(USERS_EMPTY_USER_ID);   //USERS_EMPTY_USER_ID(false, 2010, "일치하는 아이디 없음.")
        }
        if(userDao.checkUserPw(userId, userPw) == 0){   // 비밀번호 확인
            throw new BaseException(USERS_EMPTY_USER_PW);   //USERS_EMPTY_USER_PW(false, 2011, "일치하는 비밀번호 없음.")
        }
        //System.out.println("아이디, 비밀번호 확인");

        try {
            PostLoginRes loginUserIdx = userDao.loginUser(userId, userPw);
            return loginUserIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
