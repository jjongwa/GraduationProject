package com.example.demo.src.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/app/home")
public class MainController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MainProvider mainProvider;
    @Autowired
    private final MainService mainService;
    @Autowired
    private final JwtService jwtService;


    public MainController(MainProvider mainProvider, MainService mainService, JwtService jwtService){
        this.mainProvider = mainProvider;
        this.mainService = mainService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("{userIdx}")
    public BaseResponse<List<String>> getMainpageData(@PathVariable("userIdx") int userIdx){

        try{
            System.out.println("메인 컨트롤러");
            List<String> getMainpageData = mainProvider.getMainpage(userIdx);

            return new BaseResponse<>(getMainpageData);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
