package com.zzh.riggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.riggie.dto.DishDto;
import com.zzh.riggie.dto.SetmealDto;
import com.zzh.riggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface SetmealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);

    public void deleteWithDish(List<Long> ids);

    public void updateWithDish(SetmealDto setmealDto);

    public SetmealDto getByIdWithDish(Long id);

    public void  updateStatus(int status, List<Long> ids);

    public List<Setmeal> getList(Setmeal setmeal);

    public List<DishDto> getSetmealDish(Long id);
}
