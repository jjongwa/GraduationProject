package com.example.demo.src.recipe.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRecomRes {
    private int Idx;
    private int userIdx;
    private int recipeIdx;
}
