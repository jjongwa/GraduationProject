package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    //POST
    // 회원가입에서 아이디 중복 확인
    public int checkUserId(PostCheckIdReq postCheckIdReq) throws BaseException {
        try {
            int checkId = userDao.checkUserId(postCheckIdReq.getUserId());
            return checkId;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //POST
    // 회원가입 서비스
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // 우선 아이디 중복확인 거쳤다고 치자
        try{
            // createDao 이용해 게정 생성하고
            // idx, Id, Pw, userName 받아서 다시 반환
            int userIdx = userDao.createUser(postUserReq);

            return  new PostUserRes(userIdx, postUserReq.getUserId(), postUserReq.getUserPw_1(), postUserReq.getUserName());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
