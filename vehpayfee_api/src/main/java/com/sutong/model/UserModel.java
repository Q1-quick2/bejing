package com.sutong.model;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserModel implements Serializable{

    private String name;

}