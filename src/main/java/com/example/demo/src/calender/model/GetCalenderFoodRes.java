package com.example.demo.src.calender.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCalenderFoodRes {
    private int Idx;
    private String foodName;
    private String expirationDate;
}
