package com.zzh.riggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzh.riggie.common.R;
import com.zzh.riggie.dto.DishDto;
import com.zzh.riggie.dto.SetmealDto;
import com.zzh.riggie.entity.Category;
import com.zzh.riggie.entity.Dish;
import com.zzh.riggie.entity.Setmeal;
import com.zzh.riggie.service.CategoryService;
import com.zzh.riggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CacheManager cacheManager;
    /**
     * 新增套餐同时添加菜品
     * @param setmealDto
     * @return
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }

    /**
     * 分页查询套餐
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name){

        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(name != null, Setmeal::getName,name);

        setmealService.page(pageInfo, queryWrapper);

        BeanUtils.copyProperties(pageInfo, setmealDtoPage ,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            String categoryName = byId.getName();
            setmealDto.setCategoryName(categoryName);

            return setmealDto;

        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);
    }

    /**
     * 删除套餐同时删除对应关系表菜品
     * @param ids
     * @return
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){

        setmealService.deleteWithDish(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 更新套餐
     * @param setmealDto
     * @return
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithDish(setmealDto);
        return R.success("更新套餐成功");
    }

    /**
     * 更新数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        SetmealDto byIdWithDish = setmealService.getByIdWithDish(id);
        return R.success(byIdWithDish);
    }

    /**
     * 批量售卖状态
     * @param status
     * @param ids
     * @return
     */
    @CacheEvict(value = "setMealCache",allEntries = true)
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable int status,@RequestParam List<Long> ids){
        setmealService.updateStatus(status, ids);
        return R.success("更改售卖状态成功");
    }

    /**
     * 获取菜品分类对应的套餐
     * @param setmeal
     * @return
     */
    @Cacheable(value = "setMealCache",key = "#setmeal.getCategoryId()+ '_' + #setmeal.getStatus()")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        List<Setmeal> list = setmealService.getList(setmeal);
        return R.success(list);
    }

    /**
     * 获取套餐的全部菜品
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    public R<List<DishDto>> getSetmealDish(@PathVariable Long id) {
        List<DishDto> setmealDish = setmealService.getSetmealDish(id);
        return R.success(setmealDish);
    }
}
