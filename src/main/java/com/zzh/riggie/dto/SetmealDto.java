package com.zzh.riggie.dto;


import com.zzh.riggie.entity.Setmeal;
import com.zzh.riggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
