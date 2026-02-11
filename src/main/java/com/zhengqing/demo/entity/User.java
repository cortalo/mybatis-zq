package com.zhengqing.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long user_id;
    private Integer user_type;
    private String name;
    private String email;
    private String address;
    private String phone;

}
