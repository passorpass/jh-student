package com.jinghai.system.domain.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoginDto {

    private String username;

    private String password;

}
