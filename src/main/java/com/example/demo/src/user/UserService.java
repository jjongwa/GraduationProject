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
    // 회원가입 서비스
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // 우선 아이디 중복확인 거쳤다고 치자
        // 비밀번호1과 2가 일치하는지 확인
        try{
            if(!postUserReq.getUserPw_1().equals(postUserReq.getUserPw_2())) {
                System.out.println("비번 불일치");
                throw new BaseException(WRONG_EACH_PW);
            }
        }catch (Exception exception){
            throw new BaseException(WRONG_EACH_PW);
        }

        try{
            // createDao 이용해 게정 생성하고
            // idx, Id, Pw, userName 받아서 다시 반환
            int userIdx = userDao.createUser(postUserReq);

            return  new PostUserRes(userIdx, postUserReq.getUserId(), postUserReq.getUserPw_1(), postUserReq.getUserName());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



    /**
    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
     **/
}
