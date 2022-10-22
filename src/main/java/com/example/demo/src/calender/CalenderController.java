package com.example.demo.src.calender;
import com.example.demo.src.food.model.GetFoodRes;
import com.example.demo.src.calender.CalenderProvider;
import com.example.demo.src.calender.CalenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.calender.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/calender")
public class CalenderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CalenderProvider calenderProvider;
    @Autowired
    private final CalenderService calenderService;
    @Autowired
    private final JwtService jwtService;


    public CalenderController(CalenderProvider calenderProvider, CalenderService calenderService, JwtService jwtService){
        this.calenderProvider = calenderProvider;
        this.calenderService = calenderService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("{userIdx}")
    public BaseResponse<List<GetCalenderFoodRes>> getFoods(@PathVariable("userIdx") int userIdx){
        try{
            //System.out.println("컨트롤러");
            List<GetCalenderFoodRes> getCalenderFoodResList = calenderProvider.getCalenderFoodResList(userIdx);
            return new BaseResponse<>(getCalenderFoodResList);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
