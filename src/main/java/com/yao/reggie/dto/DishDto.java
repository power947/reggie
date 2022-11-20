package com.yao.reggie.dto;

import com.yao.reggie.bean.Dish;
import com.yao.reggie.bean.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>(); //口味信息

    private String categoryName;

    private Integer copies;
}
