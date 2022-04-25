package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int Idx;
    private String userId;
    private String userPw;
    private String userName;
    private String userImage;
    private int status;
}
