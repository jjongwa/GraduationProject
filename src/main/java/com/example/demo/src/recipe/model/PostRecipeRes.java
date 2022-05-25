package com.example.demo.src.recipe.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostRecipeRes {
    //private int Idx;
    private String recipeName;
    private int userIdx;
    private String detail;
    private String makeTime;
}
