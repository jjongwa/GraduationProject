package com.example.demo.src.food.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostFoodReq {
    private String foodName;
    private String foodPhoto;
    private int categoryIdx;
    private int amount;
    private int storageType;
    private Timestamp expirationDate;
}
