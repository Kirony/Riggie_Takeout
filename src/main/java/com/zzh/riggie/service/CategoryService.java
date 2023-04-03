package com.zzh.riggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.riggie.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
