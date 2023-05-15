package com.expect.spring.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Cat extends  Pet{

    private String color;
}
