package com.zzh.riggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.riggie.dto.DishDto;
import com.zzh.riggie.entity.Dish;

public interface DishService extends IService<Dish> {
    /**
     * 新增菜品的同时插入菜品口味数据，操作dish,dishFalvor两张表
     */
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void removeWithFlavor(Long id);

    public void updateStatus(int status, Long id);
}
