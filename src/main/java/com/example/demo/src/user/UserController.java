package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }


/**
 * 아이디 중복확인 API -> 회원가입 하기 전 필요
 * [POST] /users/checkId
 * @return  -> int?
 */
    @ResponseBody
    @PostMapping("/checkId")
    public BaseResponse<String> checkId(@RequestBody PostCheckIdReq postCheckIdReq) {
        //System.out.println("체크 아이디 ");
        //System.out.println(postCheckIdReq.getUserId());
        if (postCheckIdReq.getUserId() == null || postCheckIdReq.getUserId().length() == 0) {
            return new BaseResponse<>(USERS_EMPTY_ID);
        }
        try {
            int checkId = userService.checkUserId(postCheckIdReq);
            if(checkId == 0){
                return new BaseResponse<>("생성 가능한 아이디");
            }
            else {
                return new BaseResponse<>("생성 불가능한 아이디");
            }
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

/**
 * 회원가입 API
 * [POST] /users
 * @return  -> BaseResponse<PostLoginRes>
 */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq){
        // 아이디 입력 안했을때
        if(postUserReq.getUserId().length() == 0){
            System.out.println("아이디가 입력되지 않았습니다.");
            return new BaseResponse<>(USERS_EMPTY_ID);
        }

        // 비밀번호 1과 2가 일치하지 않을 때
        if(!postUserReq.getUserPw_1().equals(postUserReq.getUserPw_2())) {
            System.out.println("비번 불일치");
            return new BaseResponse<>(WRONG_EACH_PW);
        }

        // 비밀번호 칸에 입력 안했을때
        if(postUserReq.getUserPw_1().length() == 0 || postUserReq.getUserPw_2().length() == 0){
            System.out.println("비밀번호가 입력되지 않았습니다.");
            return new BaseResponse<>(WRONG_EACH_PW);
        }
        // 이름 작성하지 않았을 때
        if (postUserReq.getUserName() == null || postUserReq.getUserName().length() == 0) {
            return new BaseResponse<>(USERS_EMPTY_NAME);
        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception){
            //System.out.println("컨트롤러 예외처리 구문");
            return new BaseResponse<>(exception.getStatus());
        }
    }



/**
 * 로그인 API
 * [POST] /users/logIn
 * @return BaseResponse<PostLoginRes>
 */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}

