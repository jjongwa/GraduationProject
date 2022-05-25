package com.example.demo.src.recipe;
import com.example.demo.config.BaseException;
import com.example.demo.src.recipe.model.*;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class RecipeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RecipeDao recipeDao;
    private final RecipeProvider recipeProvider;
    private final JwtService jwtService;

    @Autowired
    public RecipeService(RecipeDao recipeDao, RecipeProvider recipeProvider, JwtService jwtService) {
        this.recipeDao = recipeDao;
        this.recipeProvider = recipeProvider;
        this.jwtService = jwtService;
    }




}
