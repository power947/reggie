package com.yao.reggie.dto;

import com.yao.reggie.bean.Setmeal;
import com.yao.reggie.bean.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
