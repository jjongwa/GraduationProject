package com.example.demo.src.recipe.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostCreateNewRecipe {
    private int recipeIdx;
    private PostRecipeRes recipeDetail;
}
